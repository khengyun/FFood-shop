import uvicorn
import numpy as np
import logging
import PIL
import PIL.Image
import pathlib
import requests
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow.keras.preprocessing import image
from sklearn.metrics import *
from fastapi import FastAPI
from io import BytesIO
from fastapi.responses import HTMLResponse
from fastapi import FastAPI
from starlette.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
from pydantic import BaseModel

app = FastAPI()


class PredictionRequest(BaseModel):
    path: str
    url: str


# Load model
loaded_model = tf.keras.models.load_model('my_model.h5')


def predict_img(request: PredictionRequest):
    """
    This Function predicts image from file path or url

    Args:
        request: A PredictionRequest object containing the file path or url of the image to predict.

    Returns:
        A string describing the image.
    """

    try:
        if request.path:
            img_path = pathlib.Path(request.path)
            img = image.load_img(img_path, target_size=(160, 160))
        elif request.url:
            response = requests.get(request.url)
            img = PIL.Image.open(BytesIO(response.content))
            img = image.resize((160, 160), resample=PIL.Image.Resampling.BICUBIC)
        else:
            raise Exception('Invalid request: Either path or url must be provided.')

        img_array = image.img_to_array(img)
        img_batch = np.expand_dims(img_array, axis=0)
        predictions = loaded_model.predict_on_batch(img_batch).flatten()
        predictions = tf.nn.sigmoid(predictions)
        predictions = tf.where(predictions < 0.5, 0, 1)
        labels = ['Defective Box', 'Non Defective Box']
        return {'prediction': 'This is a {}'.format(labels[predictions.numpy()[0]])}
    except Exception as ex:
        logging.info("Error:", ex)
        exit('Error Occured: Check File Path or URL')


# Đăng ký thư mục chứa tấm ảnh
app.mount("/images", StaticFiles(directory="images"), name="images")


@app.get('/')
def index():
    message = 'This FFood Image Classification API!'
    image_filename = 'ok.jpg'  # Tên tập tin ảnh trong thư mục

    # Tạo một chuỗi HTML chứa thẻ <img> với đường dẫn tới tập tin ảnh trong thư mục
    html_content = f'<html><body><p>{message}</p><img style="width:500px;" src="/images/{image_filename}" alt="Image"></body></html>'

    return HTMLResponse(content=html_content)


@app.get('/predict', response_model=dict)
def predict(path: str = None, url: str = None):
    """
    This Function predicts an image from a file path or URL.

    Args:
        path: The file path of the image to predict.
        url: The URL of the image to predict.

    Returns:
        A dictionary containing the prediction result.
    """
    try:
        if path:
            img_path = pathlib.Path(path)
            img = image.load_img(img_path, target_size=(160, 160))
        elif url:
            response = requests.get(url)
            img = PIL.Image.open(BytesIO(response.content))
            img = img.resize((160, 160), resample=PIL.Image.BICUBIC)
        else:
            return {'error': 'Invalid request: Either path or url must be provided.'}

        img_array = image.img_to_array(img)
        img_batch = np.expand_dims(img_array, axis=0)
        predictions = loaded_model.predict_on_batch(img_batch).flatten()
        predictions = tf.nn.sigmoid(predictions)
        predictions = tf.where(predictions < 0.2, 0, 1)
        labels = ['Defective Box', 'Non Defective Box']
        return {'prediction': 'This is a {}'.format(labels[predictions.numpy()[0]])}
    except Exception as ex:
        logging.error("Error:", ex)
        return {'error': 'An error occurred. Check the file path or URL.'}

if __name__ == '__main__':
    uvicorn.run('app:app', port=8000,host= '0.0.0.0', reload=True)