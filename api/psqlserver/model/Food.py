# Food.py
from pydantic import BaseModel
import pymssql
from typing import List
from config import db_config
from datetime import date,timedelta
from unidecode import unidecode
from itertools import permutations
import wikipedia
from bardapi import Bard
import json

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
    
    
class DailyReport:
    def __init__(self,total_account=None, daily_revenue=None, daily_orders=None, customer_with_most_orders=None):
        self.total_account = total_account
        self.daily_revenue = daily_revenue
        self.daily_orders = daily_orders
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
            
            query = "SELECT * FROM Food WHERE "
            query += " OR ".join(["food_name COLLATE Vietnamese_CI_AS LIKE %s" for _ in variants])
            
            cursor.execute(query, tuple('%' + variant + '%' for variant in variants))
            records = cursor.fetchall()
            food_data = []
            print(records)

            
            
            for record in records:
                # add if record[2] is null call wikipedia_summary
                print("record[2]: ",record[2])
                print("record[2] type: ",type(record[2]))
                # content = record[2]
                # if content == None:
                # content = self.wikipedia_summary(record[1])
                
                # print("content: ",content)
                
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

                result = DailyReport(
                    total_account=total_account,
                    daily_revenue=daily_revenue,
                    daily_orders=daily_orders,
                    customer_with_most_orders=customer_with_most_orders
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
                    target_date = (date.today()+timedelta(days=1)) - timedelta(days=i)
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
        
        
    def wikipedia_summary(self, food_name: str):
        token = 'cwhc0nEvP6vBJCnYULFK5k-1qPGVgnt6KmCtEXv7pIZEMrnROoiRXYqwYUopCJMc1ubDEw.'
        bard = Bard(token=token)
        content = bard.get_answer(f'{food_name} là gì trong ẩm thực. chỉ cần đưa ra thông tin, dưới 180 từ ?')['content']
        
        return content