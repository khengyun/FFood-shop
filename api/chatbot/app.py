import minsearch
import json
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
import asyncio
import nest_asyncio
from chatbot_rag import rag, read_json

nest_asyncio.apply()

app = FastAPI()
file = 'food_description.json'
index = read_json(file)
print()

# Add CORS Middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/rag/")
async def rag_endpoint(query: str):
    try:
        answer = rag(query, index)
        return {"answer": answer}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == '__main__':
    uvicorn.run('app:app', port=8100, host='0.0.0.0', loop='asyncio')
