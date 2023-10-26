# app.py
from fastapi import FastAPI
from model.Food import FoodModel, FoodOperations

app = FastAPI()
food_operations = FoodOperations()

@app.get('/get_all_food', response_model=list[FoodModel])
def get_all_food():
    return food_operations.get_food_data()

@app.get('/search_food_by_name/{food_name}', response_model=list[FoodModel])
def search_food_by_name_endpoint(food_name: str):
    return food_operations.search_food_by_name(food_name)

if __name__ == '__main__':
    import uvicorn
    uvicorn.run('app:app', port=8001, host='0.0.0.0')
