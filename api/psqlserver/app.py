# app.py
from fastapi import FastAPI, Depends
from model.Food import FoodModel, FoodOperations
from model.Google_Auth import GoogleAuth
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Thay đổi thành nguồn của ứng dụng JavaScript của bạn
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

food_operations = FoodOperations()


@app.get('/get_all_food', response_model=list[FoodModel])
def get_all_food():
    return food_operations.get_food_data()

@app.get('/search_food_by_name/{food_name}',response_model=list[FoodModel])
def search_food_by_name_endpoint(food_name: str):
    return food_operations.search_food_by_name(food_name)

@app.get('/get_daily_revenue')
def get_daily_revenue():
    return food_operations.get_daily_revenue()

@app.get('/get_top_selling_foods')
def get_top_selling_foods():
    return food_operations.get_top_selling_foods()

@app.get('/get_daily_sales')
def get_daily_sales():
    return food_operations.get_daily_sales()


@app.get('/get_credential/{token_info}')
async def get_credential(token_info: str):
    google_auth = GoogleAuth()
    return google_auth.verify_google_id_token(token_info)


if __name__ == '__main__': 
    uvicorn.run('app:app', port=8001, host='0.0.0.0')
