# Food.py
from pydantic import BaseModel
import pymssql
from typing import List
from config import db_config
from datetime import date,timedelta
from unidecode import unidecode
from itertools import permutations


class FoodModel(BaseModel):
    food_id: int
    food_name: str
    food_description: str = None
    food_price: float
    food_status: bool
    food_rate: int = None  # Corrected data type to int
    discount_percent: int  # Corrected data type to int
    food_url: str = None
    food_type_id: int = None

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
                    food_status=bool(record[4]),
                    food_rate=int(record[5]),
                    discount_percent=int(record[6]),
                    food_url=record[7],
                    food_type_id=record[8]
                    
                )
                food_data.append(food.dict())
            conn.close()
            return food_data

        except Exception as e:
            return str(e)
        

    def generate_variants(self, food_name):
        words = food_name.split()
        variants = set()
        
        variants.add(food_name)
        
        for word in words:
            variants.add(word)
        
        for r in range(2, len(words) + 1):
            for combo in permutations(words, r):
                variants.add(' '.join(combo))
        
        return variants

    def similarity_score(self, food_name, food):
        print(food_name, food.food_name)
        food_name_lower = food_name.lower()
        food_name_tokens = food_name_lower.split()
        food_tokens = food.food_name.lower().split()
        score = sum(1 for token in food_tokens if token in food_name_tokens)
        return score

    def search_food_by_name(self, food_name: str):
        try:
            conn = pymssql.connect(**self.db_config)
            cursor = conn.cursor()
            variants = self.generate_variants(food_name)
            
            query = "SELECT TOP 10 * FROM Food WHERE "
            query += " OR ".join(["food_name COLLATE Vietnamese_CI_AI LIKE %s" for _ in variants])
            
            cursor.execute(query, tuple('%' + variant + '%' for variant in variants))
            records = cursor.fetchall()
            food_data = []
            print(records)

            for record in records:
                food = FoodModel(
                    food_id=record[0],
                    food_name=record[1],
                    food_description=record[2],
                    food_price=float(record[3]),
                    food_status=bool(record[4]),
                    food_rate=int(record[5]),
                    discount_percent=int(record[6]),
                    food_url=record[7],
                    food_type_id=record[8]
                )
                food_data.append(food)
            print(food_data)

            # Sort food_data based on similarity_score
            food_data = sorted(food_data, key=lambda x: self.similarity_score(food_name, x), reverse=True)

            conn.close()

            return [food.dict() for food in food_data]

        except Exception as e:
            return str(e)


        
    def get_daily_revenue(self):
        try:
            with pymssql.connect(**self.db_config) as conn:
                cursor = conn.cursor()
                cursor.execute("""
                WITH DailySales AS (
                SELECT
                    A.account_username,
                    CI.food_id,
                    SUM(CI.food_quantity) AS total_items_sold
                FROM [Order] AS O
                INNER JOIN CartItem AS CI ON O.cart_id = CI.cart_id
                INNER JOIN Account AS A ON O.customer_id = A.customer_id
                WHERE CAST(CONVERT(DATE, O.order_time) AS DATE) = CAST(GETDATE() AS DATE)
                GROUP BY A.account_username, CI.food_id
                )



                SELECT
                SUM(CI.food_quantity * F.food_price) AS daily_revenue,
                COUNT(O.order_id) AS daily_orders,
                A.account_username AS customer_with_most_orders
                FROM [Order] AS O
                INNER JOIN CartItem AS CI ON O.cart_id = CI.cart_id
                INNER JOIN Food AS F ON CI.food_id = F.food_id
                INNER JOIN Account AS A ON O.customer_id = A.customer_id
                WHERE CAST(CONVERT(DATE, O.order_time) AS DATE) = CAST(GETDATE() AS DATE)
                GROUP BY A.account_username;

                """)
                row = cursor.fetchone()

                result = {
                    'daily_revenue': float(row[0]) if row else 0.0,
                    'daily_orders': int(row[1]) if row else 0,
                    'customer_with_most_orders': row[2] if row else 0
                }

                return result

        except Exception as e:
            return {'error': str(e)}

    def get_top_selling_foods(self):
        try:
            with pymssql.connect(**self.db_config) as conn:
                cursor = conn.cursor()
                cursor.execute("""
                SELECT TOP 5
                    F.food_id,
                    F.food_name,
                    F.food_img_url,
                    SUM(CI.food_quantity) AS total_sold_quantity,
                    SUM(CI.food_quantity * F.food_price) AS total_revenue
                FROM Food AS F
                INNER JOIN CartItem AS CI ON F.food_id = CI.food_id
                GROUP BY F.food_id, F.food_name, F.food_img_url
                ORDER BY total_sold_quantity DESC, total_revenue DESC
                """)
                top_selling_foods = cursor.fetchall()

                formatted_top_selling_foods = []
                for food in top_selling_foods:
                    formatted_food = {
                        "id": food[0],
                        "name": food[1],
                        "img_url": food[2],
                        "total_sold": food[3],
                        "total_revenue": float(food[4])
                    }
                    formatted_top_selling_foods.append(formatted_food)

                return formatted_top_selling_foods

        except Exception as e:
            return str(e)

    def get_daily_sales(self):
        try:
            with pymssql.connect(**self.db_config) as conn:
                cursor = conn.cursor()

                daily_sales = []
                for i in range(7):
                    target_date = date.today() - timedelta(days=i)

                    cursor.execute("""
                        SELECT ISNULL(SUM(CI.food_quantity * F.food_price), 0) AS daily_revenue
                        FROM [Order] AS O
                        LEFT JOIN CartItem AS CI ON O.cart_id = CI.cart_id
                        LEFT JOIN Food AS F ON CI.food_id = F.food_id
                        WHERE CAST(O.order_time AS DATE) = %s
                    """, (target_date,))
                    row = cursor.fetchone()
                    daily_revenue = float(row[0]) if row else 0.0

                    daily_sales.append({"date": target_date, "daily_revenue": daily_revenue})

                return daily_sales

        except Exception as e:
            return str(e)
