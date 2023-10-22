from fastapi import FastAPI
from src.predict import predict_dish_onnx
from starlette.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
import onnxruntime
import uvicorn
import time

app = FastAPI()

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

if __name__ == '__main__':
    uvicorn.run('app:app', port=8000, host='0.0.0.0', reload=True)