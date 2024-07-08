import requests
import os
import json
import minsearch
from dotenv import load_dotenv

# Import necessary libraries

# Load the environment variables from the .env file
load_dotenv()

# Retrieve the API key from environment variables
api_key = os.getenv('API_KEY')

def llm(prompt):

    # Define the endpoint URL
    url = f'https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key={api_key}'

    # Define headers
    headers = {
        'Content-Type': 'application/json'
    }

    # Define the data to send
    data = {
        "contents": [
            {
                "parts": [
                    {"text": prompt}
                ]
            }
        ]
    }

    # Make the POST request
    response = requests.post(url, headers=headers, json=data)

    # Print the response
    if response.status_code == 200:
        response_data = response.json()
        text_content = response_data['candidates'][0]['content']['parts'][0]['text']
        print(text_content)
        return {"response": text_content}
    else:
        print(f"Error: {response.status_code}")
        print(response.text)
        
        return {"response": "response error"}      

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