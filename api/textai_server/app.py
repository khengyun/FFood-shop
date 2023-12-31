# app.py
from fastapi import FastAPI, Depends, Request, Query
from fastapi.middleware.cors import CORSMiddleware
from starlette.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
import g4f
import uvicorn
app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Thay đổi thành nguồn của ứng dụng JavaScript của bạn
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

_providers = [
    g4f.Provider.Aichat,
    g4f.Provider.ChatBase,
    g4f.Provider.Bing,
    g4f.Provider.GptGo,
    g4f.Provider.You,
    g4f.Provider.Yqcloud,
]


@app.get("/generate_description/")
async def generate_description(food_name: str = Query(..., title="Food Name")):
    t = f'As a waiter of a restaurant, generate a description of {food_name} in a non-scientific way. The description should be easy to understand for normal people. The description must be written in Vietnamese with a language that is close to human language as possible. Avoid descriptive and robotic descriptions. The description length must be limited to 1 paragraph and must not exceed 30 words. Do not add recommendations in the last sentence.'
    
    response = g4f.ChatCompletion.create(
        model=g4f.models.default,
        messages=[{"role": "user", "content": t}],
    )
    
    return {"description": response}


if __name__ == '__main__': 
    uvicorn.run('app:app', port=8123, host='0.0.0.0')
