from fastapi import FastAPI
from src.predict import predict_dish_onnx, predict_dish_onnx_from_base64
from starlette.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
from fastapi.middleware.cors import CORSMiddleware
import logging
import onnxruntime
import uvicorn
import time
from pydantic import BaseModel

app = FastAPI()
class ImageBase64Request(BaseModel):
    image_base64: str
# Thêm CORS Middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Thay đổi thành nguồn của ứng dụng JavaScript của bạn
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# mount data
app.mount("/images", StaticFiles(directory="images"), name="images")
app.mount("/model", StaticFiles(directory="model"), name="model")
app.mount("/src", StaticFiles(directory="src"), name="src")

# mount && load model
session_onnx = onnxruntime.InferenceSession("model/food_model.onnx", providers=['CPUExecutionProvider'])

@app.get("/", response_class=HTMLResponse)
def read_root():
    return """
    <html>
        <head>
            <title>Welcome to the FFood-shop API Server!</title>
        </head>
        <body>
            <h1>Welcome to the FFood-shop API Server!</h1>
            <p>Repository: <a href="https://github.com/khengyun/FFood-shop">https://github.com/khengyun/FFood-shop</a></p>
        </body>
    </html>
    """

@app.get("/predict_dish_test")
async def predict_dish(url: str):
    class_list_file = "model/food_model_labels.txt"
    
    start_time = time.time()
    predicted_dish, img_url = predict_dish_onnx(url, session_onnx, class_list_file)
    end_time = time.time()
    
    response_time = end_time - start_time
    
    html_content = f'<html><body><h2>Pred: {predicted_dish}</h2><p>Mean-AUC: 0.8897322416305542</p><p>Inference time: {response_time} seconds</p><img style="width:500px;" src="{img_url}" alt="Image"></body></html>'
    
    return HTMLResponse(content=html_content)



@app.post("/predict_dish")
async def predict_dish(request_data: ImageBase64Request):
    class_list_file = "model/food_model_labels.txt"
    
    logging.info(f"Received image_base64: {request_data.image_base64}")
    
    start_time = time.time()

    predicted_dish = predict_dish_onnx_from_base64(request_data.image_base64, session_onnx, class_list_file)
    end_time = time.time()
    
    response_time = end_time - start_time
    
    return {
        "predicted_dish": predicted_dish,
        "inference_time": response_time,
    }


if __name__ == '__main__':
    uvicorn.run('app:app', port=8000, host='0.0.0.0', reload=True)