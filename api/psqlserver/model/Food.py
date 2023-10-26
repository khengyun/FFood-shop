from pydantic import BaseModel
import pymssql
from typing import List
from config import db_config


class FoodModel(BaseModel):
    food_id: int
    food_name: str
    food_description: str = None
    food_price: float
    food_rate: int
    food_status: bool
    food_type_id: int
    food_url: str = None

class FoodOperations:
    def __init__(self):
        self.db_config = db_config

    def get_food_data(self):
        try:
            conn = pymssql.connect(**self.db_config)
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM Food")
            records = cursor.fetchall()
            food_data = []

            for record in records:
                food = FoodModel(
                    food_id=record[0],
                    food_name=record[1],
                    food_description=record[2],
                    food_price=float(record[3]),
                    food_rate=int(record[4]),
                    food_status=bool(record[5]),
                    food_type_id=int(record[6]),
                    food_url=record[7]
                )
                food_data.append(food.dict())

            conn.close()
            return food_data

        except Exception as e:
            return str(e)

    def search_food_by_name(self, food_name: str):
        try:
            conn = pymssql.connect(**self.db_config)
            cursor = conn.cursor()

            # Use the LIKE operator in the SQL query
            # The '%' characters are used as wildcards for pattern matching
            cursor.execute("SELECT * FROM Food WHERE food_name LIKE %s", ('%' + food_name + '%',))
            records = cursor.fetchall()
            food_data = []

            for record in records:                
                food = FoodModel(
                    food_id=record[0],
                    food_name=record[1],
                    food_description=record[2],
                    food_price=float(record[3]),
                    food_rate=int(record[4]),
                    food_status=bool(record[5]),
                    food_type_id=int(record[6]),
                    food_url=record[7]
                    
                )
                food_data.append(food.dict())

            conn.close()
            return food_data

        except Exception as e:
            return str(e)
