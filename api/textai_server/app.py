# app.py
from fastapi import FastAPI, Depends, Request, Query
from fastapi.middleware.cors import CORSMiddleware
from starlette.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
import uvicorn
import asyncio
import nest_asyncio
import requests
import os
from dotenv import load_dotenv

nest_asyncio.apply()

app = FastAPI()
# Load the environment variables from the .env file
load_dotenv()
# Retrieve the API key from environment variables
api_key = os.getenv('API_KEY')

# Print the API key to verify (remove this line in production)
print(f"API Key: {api_key}")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Thay đổi thành nguồn của ứng dụng JavaScript của bạn
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/generate_description/")
async def generate_description(food_name: str = Query(..., title="Food Name")):
    prompt = f'As a waiter of a restaurant, generate a description of {food_name} in a non-scientific way. The description should be easy to understand for normal people. The description must be written in Vietnamese with a language that is close to human language as possible. Avoid descriptive and robotic descriptions. The description length must be limited to 1 paragraph and must not exceed 30 words. Do not add recommendations in the last sentence.'
    
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
        return {"description": text_content}
    else:
        print(f"Error: {response.status_code}")
        print(response.text)
        
        return {"description": "response error"}
    
    

if __name__ == '__main__':
    uvicorn.run('app:app', port=8123, host='0.0.0.0', loop='asyncio')
