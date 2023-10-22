import io
import requests
from PIL import Image
import numpy as np
import tensorflow as tf
import onnxruntime



def predict_dish_onnx(url, session, class_list_file):

    response = requests.get(url)
    image = Image.open(io.BytesIO(response.content))

    image = image.resize((224, 224))
    image = np.array(image).astype(np.float32)  
    image = image / 255.0  

    input_name = session.get_inputs()[0].name
    output_name = session.get_outputs()[0].name


    input_data = np.expand_dims(image, axis=0)
    predictions = session.run([output_name], {input_name: input_data})[0][0]

    with open(class_list_file, 'r') as file:
        class_list = file.read().split('\n')


    predicted_class_index = np.argmax(predictions)
    predicted_class = class_list[predicted_class_index]

    return predicted_class, url


# convert code to __main__


if __name__ == "__main__":

    onnx_model_path = "../model/food_model.onnx" 
    session = onnxruntime.InferenceSession(onnx_model_path, providers=['AzureExecutionProvider', 'CPUExecutionProvider'])
    class_list_file = "../model/food_model_labels.txt"
    url = "https://i.redd.it/69imzozl0i231.jpg" 

    predicted_dish = predict_dish_onnx(url, onnx_model_path, class_list_file)

    print("Predicted Dish:", predicted_dish)