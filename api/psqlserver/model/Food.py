# Food.py
from pydantic import BaseModel
import pymssql
from typing import List
from config import db_config
from datetime import date,timedelta
from unidecode import unidecode
from itertools import permutations
import wikipedia
import g4f
import json

g4f.debug.logging = True # enable logging
g4f.check_version = False # Disable automatic version checkinga

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
    food_stock_quantity: int = None
    
    
class DailyReport:
    def __init__(self,total_account=None, daily_revenue=None, daily_orders=None, customer_with_most_orders=None,best_selling_food=None):
        self.total_account = total_account
        self.daily_revenue = daily_revenue
        self.daily_orders = daily_orders
        self.best_selling_food=best_selling_food
        self.customer_with_most_orders = customer_with_most_orders

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
                    food_stock_quantity=int(record[4]),
                    food_status=bool(record[5]),
                    food_rate=int(record[6]),
                    discount_percent=int(record[7]),
                    food_url=record[8],
                    food_type_id=record[9]
                    
                )
                food_data.append(food.dict())
            conn.close()
            return food_data

        except Exception as e:
            return str(e)
        


    def search_foods(self, food_name):
        def generate_variants(name):
            words = name.split()
            variants = set()
            
            variants.add(name)
            
            for word in words:
                variants.add(word)
            
            for r in range(2, len(words) + 1):
                for combo in permutations(words, r):
                    variants.add(' '.join(combo))
            
            return variants

        def similarity_score(name, food):
            name_lower = name.lower()
            name_tokens = name_lower.split()
            food_tokens = food.lower().split()
            score = sum(1 for token in food_tokens if token in name_tokens)
            return score

        try:
            conn = pymssql.connect(**self.db_config)
            cursor = conn.cursor()
            variants = generate_variants(food_name)
            
            query = "SELECT * FROM Food WHERE " + " OR ".join(["food_name COLLATE Vietnamese_CI_AI LIKE %s" for _ in variants])
            cursor.execute(query, tuple('%' + variant + '%' for variant in variants))
            records = cursor.fetchall()
            food_data = []

            for record in records:
                food = {
                    "food_id": record[0],
                    "food_name": record[1],
                    "food_description": record[2],
                    "food_price": float(record[3]),
                    "food_stock_quantity": int(record[4]),
                    "food_status": bool(record[5]),
                    "food_rate": int(record[6]),
                    "discount_percent": int(record[7]),
                    "food_url": record[8],
                    "food_type_id": record[9]
                }
                food_data.append(food)

            # Sort food_data based on similarity_score
            food_data = sorted(food_data, key=lambda x: similarity_score(food_name, x["food_name"]), reverse=True)

            conn.close()

            return food_data

        except Exception as e:
            return str(e)


        
    def get_daily_revenue(self):
        try:
            with pymssql.connect(**self.db_config) as conn:
                cursor = conn.cursor()
                
                # total_account
                cursor.execute("""SELECT COUNT(account_id) FROM Account""")
                rows0 = cursor.fetchall()
                total_account = int(rows0[0][0]) if rows0 and rows0[0][0] is not None else 0

                # Truy vấn 1: Lấy thông tin về khách hàng có nhiều đơn hàng nhất
                cursor.execute("""
                SELECT TOP 5 A.account_id, A.account_username, COUNT(O.order_id) AS total_orders
                FROM Account AS A
                JOIN [Order] AS O ON A.customer_id = O.customer_id
                JOIN Payment AS P ON O.order_id = P.order_id
                WHERE P.payment_time IS NOT NULL
                GROUP BY A.account_id, A.account_username
                ORDER BY total_orders DESC;
                """)
                rows1 = cursor.fetchall()
                customer_with_most_orders = [{"account_id": row[0], "account_username": row[1], "total_orders": row[2]} for row in rows1] if rows1 else []

                # Truy vấn 2: Tính tổng số tiền thanh toán trong ngày hôm nay
                cursor.execute("""
                SELECT SUM(payment_total) AS total_payment_today
                FROM Payment
                WHERE CONVERT(date, SWITCHOFFSET(CONVERT(datetimeoffset, payment_time), '+07:00')) = CONVERT(date, SWITCHOFFSET(CONVERT(datetimeoffset, SYSDATETIME()), '+07:00'));
                """)
                rows2 = cursor.fetchall()
                daily_revenue = float(rows2[0][0]) if rows2 and rows2[0][0] is not None else 0.0

                # Truy vấn 3: Đếm tổng số đơn hàng trong ngày hôm nay
                cursor.execute("""
                SELECT COUNT(order_id) AS total_orders_today
                FROM [Order]
                WHERE CONVERT(date, SWITCHOFFSET(CONVERT(datetimeoffset, order_time), '+07:00')) = CONVERT(date, SWITCHOFFSET(CONVERT(datetimeoffset, SYSDATETIME()), '+07:00'));
                """)
                rows3 = cursor.fetchall()
                daily_orders = int(rows3[0][0]) if rows3 and rows3[0][0] is not None else 0
                
                
                # Truy vấn 4: take the name of the best-selling food
                cursor.execute("""
                SELECT TOP 1 F.food_name
                FROM Food AS F
                INNER JOIN CartItem AS CI ON F.food_id = CI.food_id
                GROUP BY F.food_name
                ORDER BY SUM(CI.food_quantity) DESC
                """)
                rows4 = cursor.fetchall()
                best_selling_food_name = rows4[0][0] if rows4 and rows4[0][0] is not None else None

                result = DailyReport(
                    total_account=total_account,
                    daily_revenue=daily_revenue,
                    daily_orders=daily_orders,
                    best_selling_food=best_selling_food_name,
                    customer_with_most_orders=customer_with_most_orders,
                )

                return result.__dict__

        except Exception as e:
            return json.dumps({'error': str(e)})


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

                daily_payments = []

                for i in range(7):  # Lấy dữ liệu trong 7 ngày gần đây
                    target_date = date.today() - timedelta(days=i)
                    print(target_date)
                    print(date.today())
                    print("======================")
                    cursor.execute("""
                        SELECT CAST(payment_time AS DATE) AS payment_date, SUM(payment_total) AS total_payment
                        FROM Payment
                        WHERE CAST(payment_time AS DATE) = %s
                        GROUP BY CAST(payment_time AS DATE)
                    """, (target_date,))
                    
                    row = cursor.fetchone()
                    daily_revenue = float(row[1]) if row else 0.0

                    daily_payments.append({"date": target_date, "total_payment": daily_revenue})

                return daily_payments

        except Exception as e:
            return str(e)
        
        

    
    def get_today_income(self):
        try:
            with pymssql.connect(**self.db_config) as conn:
                cursor = conn.cursor()

                daily_payments = []

                cursor.execute("""
                    SELECT SUM(payment_total) AS total_payment_today
                    FROM Payment
                    WHERE
                        DAY(payment_time) = DAY(SYSDATETIME())
                        AND MONTH(payment_time) = MONTH(SYSDATETIME())
                        AND YEAR(payment_time) = YEAR(SYSDATETIME());
                """)

                row = cursor.fetchone()
                today_income = float(row[0]) if row and row[0] is not None else 0.0

                daily_payments.append({"today_income": today_income})

                return daily_payments

        except Exception as e:
            return str(e)
