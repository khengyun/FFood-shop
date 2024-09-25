from pydantic import BaseModel, validator
import pymssql
from typing import List
from config import db_config
from datetime import datetime
import pytz
class Order(BaseModel):
    order_id: int
    cart_id: int
    customer_id: int
    order_status_id: int
    payment_method_id: int
    contact_phone: str
    delivery_address: str
    order_time: datetime
    order_total: int
    order_note: str
    delivery_time: datetime
    order_cancel_time: datetime
    


class OrderOperations:
    def __init__(self):
        self.db_config = db_config

    def payment_from_cis(self, customerID: int):
        try:
            conn = pymssql.connect(**self.db_config)
            cursor = conn.cursor()
            cursor.execute(f"""
                SELECT TOP 1 
                    order_id,
                    cart_id,
                    customer_id,
                    order_status_id,
                    payment_method_id,
                    voucher_id,
                    contact_phone,
                    delivery_address,
                    order_time,
                    order_total,
                    order_note,
                    delivery_time,
                    order_cancel_time
                FROM [Order]
                WHERE customer_id = {customerID}
                ORDER BY order_id DESC;
            """)
            records = cursor.fetchall()
            order_data = []

            for record in records:
                order = {
                    "order_id": record[0],
                    "cart_id": record[1],
                    "customer_id": record[2],
                    "order_status_id": record[3],
                    "payment_method_id": record[4],
                    "voucher_id": record[5],
                    "contact_phone": record[6],
                    "delivery_address": record[7],
                    "order_time": record[8].strftime('%Y-%m-%d %H:%M:%S'),
                    "order_total": record[9],
                    "order_note": record[10],
                    "delivery_time": record[11].strftime('%Y-%m-%d %H:%M:%S') if record[11] else None,
                    "order_cancel_time": record[12].strftime('%Y-%m-%d %H:%M:%S') if record[12] else None
                }
                order_data.append(order)

            conn.close()
            return order_data

        except Exception as e:
            return [{"error": str(e)}]
        
    def insert_payment(self, order_id: int, vnp_Amount: int, vnp_TxnRef: str, vnp_BankCode: str):
        try:
            conn = pymssql.connect(**self.db_config)
            cursor = conn.cursor()
            
            sql = """
                INSERT INTO Payment (order_id, payment_method_id, payment_total, payment_content, payment_bank, payment_code, payment_status, payment_time)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            """
            
            values = (
                order_id,  
                1, 
                int(vnp_Amount) / 100, 
                "Thanh toán đơn hàng ffood",  
                vnp_BankCode, 
                vnp_TxnRef, 
                1,  
                datetime.now(pytz.timezone('Asia/Ho_Chi_Minh')).strftime('%Y-%m-%d %H:%M:%S')  
            )

            cursor.execute(sql, values)
            conn.commit()
            conn.close()
            return True

        except Exception as e:
            return str(e)
                
    def check_order_payment(self, order_id: int):
        try:
            conn = pymssql.connect(**self.db_config)
            cursor = conn.cursor()
            
            cursor.execute(f"""
                SELECT PaymentMethod.payment_method, Payment.payment_time, Payment.order_id, payment_status
                FROM Payment
                JOIN PaymentMethod ON Payment.payment_status = PaymentMethod.payment_method_id
                WHERE Payment.order_id = {order_id};
            """)
            
            result = cursor.fetchall()
            conn.close()
            
            payment_info = []
            for row in result:
                payment_method, payment_time, order_id, payment_method_id = row
                payment_info.append({
                    "payment_method": payment_method,
                    "payment_time": payment_time.strftime('%Y-%m-%d %H:%M:%S'),
                    "order_id": order_id,
                    "payment_method_id": payment_method_id
                })
            
            return payment_info

        except Exception as e:
            return {"error": str(e)}
