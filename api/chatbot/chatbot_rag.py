import minsearch
import json
from g4f.client import Client
import g4f

_providers = [
    g4f.Provider.Aichat,
    g4f.Provider.ChatBase,
    g4f.Provider.Bing,
    g4f.Provider.GptGo,
    g4f.Provider.You,
    g4f.Provider.Yqcloud,
]

def llm(prompt):
    client = Client()
    print("Creating chat completion...")
    chat_completion = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[{"role": "user", "content": prompt}], 
        ignored=["Ylokh", "GptGo", "AItianhu", "Aibn", "Myshell", "FreeGpt"],
        stream=True
    )
    
    response = ""
    print("Waiting for completion...")
    for completion in chat_completion:
        response += completion.choices[0].delta.content or ""
        
    return {"response": response}

def read_json(file):
    with open(file, 'rt', encoding='utf-8') as f_in:  # Specify UTF-8 encoding
        docs_raw = json.load(f_in)
    print(f"Read {len(docs_raw)} documents from {file}.")
    
    documents = []
    for course_dict in docs_raw:
        for doc in course_dict['items']:
            doc['category'] = course_dict['category']
            documents.append(doc)
            
    index = minsearch.Index(
        text_fields=["question", "description", "name"],
        keyword_fields=["category"]
    )
    index.fit(documents)
    print("Index has been initialized.")
    return index

def search(query, index):
    
    if index is None:
        raise RuntimeError("Index has not been initialized. Call read_json() first.")
    
    boost = {'question': 3.0, 'section': 0.5}

    results = index.search(
        query=query,
        filter_dict={'category': 'FFood'},
        boost_dict=boost,
        num_results=5
    )

    return results

def build_prompt(query, search_results):
    prompt_template = """
You're a vietnamese food ordering assistant. Answer the QUESTION based on the CONTEXT from the food description database.
Use only the facts from the CONTEXT when answering the QUESTION.

QUESTION: {question}

CONTEXT: 
{context}
""".strip()

    context = ""
    
    for doc in search_results:
        context = context + f"name: {doc['name']}\nquestion: {doc['question']}\nanswer: {doc['description']}\n\n"
    
    prompt = prompt_template.format(question=query, context=context).strip()
    return prompt

def rag(query, index):
    print(f"Searching for: {query}")
    search_results = search(query, index)
    print(f"Found {len(search_results)} search results.")
    prompt = build_prompt(query, search_results)
    answer = llm(prompt)
    return answer