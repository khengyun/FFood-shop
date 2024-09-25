# app.py
from fastapi import FastAPI, Depends, Request, Query
from model.Food import FoodModel, FoodOperations
from model.Payment import Payment, PaymentRequest, Settings
from model.Google_Auth import GoogleAuth
from model.Order import OrderOperations
from model.vn_payment import Vnpay
from fastapi.middleware.cors import CORSMiddleware
from starlette.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
import uvicorn
app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Thay đổi thành nguồn của ứng dụng JavaScript của bạn
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.mount("/model", StaticFiles(directory="model"), name="model")


food_operations = FoodOperations()
order_operations = OrderOperations()


@app.get('/get_all_food', response_model=list[FoodModel])
def get_all_food():
    return food_operations.get_food_data()

@app.get('/search_food_by_name/{food_name}')
async def search_food_by_name_endpoint(food_name: str):
    return food_operations.search_foods(food_name)

@app.get('/get_daily_revenue')
def get_daily_revenue():
    return food_operations.get_daily_revenue()

@app.get('/get_top_selling_foods')
def get_top_selling_foods():
    return food_operations.get_top_selling_foods()

# today income
@app.get('/get_today_income')
def get_today_income():
    return food_operations.get_today_income()

@app.get('/get_daily_sales')
def get_daily_sales():
    return food_operations.get_daily_sales()


@app.get('/get_credential/{token_info}')
async def get_credential(token_info: str):
    google_auth = GoogleAuth()
    return google_auth.verify_google_id_token(token_info)



# payment method on pserver
@app.get('/payment', response_model=dict)
async def payment_endpoint(
    request: Request,
    vnp_OrderType: str = Query(..., alias='vnp_OrderType'),
    vnp_OrderInfo: str = Query(..., alias='vnp_OrderInfo'),
    vnp_Amount: int = Query(..., alias='vnp_Amount')
):
    payment_instance = Payment(Settings)
    return payment_instance(PaymentRequest(vnp_OrderType=vnp_OrderType, vnp_Amount=vnp_Amount, vnp_OrderInfo=vnp_OrderInfo), request)

@app.get('/payment_from_cis')
def payment_from_cis(request: Request, cis: str = Query(..., alias='cis')):
    try:
        order_data = order_operations.payment_from_cis(int(cis))
        if order_data:
            first_order = order_data[0]
            payment_instance = Payment(Settings)
            return payment_instance(PaymentRequest(
                vnp_OrderType="online_order",
                vnp_Amount=first_order["order_total"]*100,
                vnp_OrderInfo=str({
                    "order_id": first_order["order_id"],
                    "customer_id": first_order["customer_id"],
                    "order_note": first_order["order_note"],
                    "order_time": first_order["order_time"]
                }),
            ), request)
        else:
            return "something went wrong"

    except Exception as e:
        error_message = {"error": str(e)}
        return [error_message]

@app.get('/payment_return',response_class=HTMLResponse)
async def payment_return(request: Request):
    # Access and show the query parameters
    
    vnp_Amount = request.query_params.get('vnp_Amount')
    vnp_BankCode = request.query_params.get('vnp_BankCode')
    vnp_CardType = request.query_params.get('vnp_CardType')
    vnp_OrderInfo = request.query_params.get('vnp_OrderInfo')
    vnp_PayDate = request.query_params.get('vnp_PayDate')
    vnp_ResponseCode = request.query_params.get('vnp_ResponseCode')
    vnp_TmnCode = request.query_params.get('vnp_TmnCode')
    vnp_TransactionNo = request.query_params.get('vnp_TransactionNo')
    vnp_TransactionStatus = request.query_params.get('vnp_TransactionStatus')
    vnp_TxnRef = request.query_params.get('vnp_TxnRef')
    vnp_SecureHash = request.query_params.get('vnp_SecureHash')
    tag = "#failure"
    
    
    vnpay = Vnpay(Settings)
    vnpay.responseData = {
    "vnp_Amount": vnp_Amount,
    "vnp_BankCode": vnp_BankCode,
    "vnp_CardType": vnp_CardType,
    "vnp_OrderInfo": vnp_OrderInfo,
    "vnp_PayDate": vnp_PayDate,
    "vnp_ResponseCode": vnp_ResponseCode,
    "vnp_TmnCode": vnp_TmnCode,
    "vnp_TransactionNo": vnp_TransactionNo,
    "vnp_TransactionStatus": vnp_TransactionStatus,
    "vnp_TxnRef": vnp_TxnRef,
    "vnp_SecureHash": vnp_SecureHash
    }
    print("==============Validate response===============")
    
    if (vnp_TransactionStatus == '00'):
         # Gọi hàm insert_payment ở đây
        try:
            # Trích xuất order_id từ vnp_OrderInfo
            vnp_OrderInfo_dict = eval(vnp_OrderInfo)
            order_id = vnp_OrderInfo_dict.get('order_id')
            
            # Gọi hàm insert_payment
            result = order_operations.insert_payment(order_id, vnp_Amount, vnp_TxnRef, vnp_BankCode)
            # Thực hiện xử lý kết quả (nếu cần) sau khi insert_payment
            if result:
                tag = "#success"

            
        except Exception as e:
            result = str(e)
    else:
        # Do something when the payment is failed
        pass

    # Generate or load the HTML content
    html_content = f"""
    <!DOCTYPE html>
    <html>
    <head>
        <title>Payment Return Page</title>
    </head>
    <body>
        <h1>Payment Return Page</h1>
        <p>Your payment has been processed {tag}.</p>
        <script>
            // Execute the script after the page has fully loaded
            window.onload = function() {{
                window.location.assign("http://localhost:8080/home{tag}");
            }};
        </script>
        <!-- Add your HTML content here -->
    </body>
    </html>
    """
    
    return html_content

# /check_order_payment/${orderID}
@app.get('/check_order_payment/{orderID}')
def check_order_payment(orderID: int):
    return order_operations.check_order_payment(orderID)


if __name__ == '__main__': 
    uvicorn.run('app:app', port=8001, host='0.0.0.0')
