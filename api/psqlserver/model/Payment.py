# Payment.py
from fastapi import FastAPI, Depends, Query, Request
from pydantic import BaseModel
from datetime import datetime
from model.vn_payment import Vnpay, Settings
from config import db_config
from typing import List


class PaymentRequest(BaseModel):
    vnp_OrderType: str
    vnp_OrderInfo: str
    vnp_Amount: int


class Payment:
    def __init__(self, settings):
        self.settings = settings
        self.db_config = db_config
        print(self.settings)
        print(self.settings.VNPAY_TMN_CODE)
        print(self.settings.VNPAY_RETURN_URL)
        print(self.settings.VNPAY_PAYMENT_URL)
        print(self.settings.VNPAY_HASH_SECRET_KEY)

    def __call__(self, payment_data: PaymentRequest, request):
        vnp = Vnpay(self.settings)
        vnp.requestData = {
            'vnp_Version': '2.1.0',
            'vnp_Command': 'pay',
            'vnp_TmnCode': self.settings.VNPAY_TMN_CODE,
            'vnp_Amount': payment_data.vnp_Amount,
            'vnp_CurrCode': 'VND',
            'vnp_TxnRef': str(int(datetime.timestamp(datetime.now()))),
            'vnp_OrderInfo': payment_data.vnp_OrderInfo,
            'vnp_OrderType': payment_data.vnp_OrderType,
            "vnp_Locale": "vn",
            'vnp_CreateDate': datetime.now().strftime('%Y%m%d%H%M%S'),
            'vnp_IpAddr': self.get_client_ip(request),
            'vnp_ReturnUrl': self.settings.VNPAY_RETURN_URL,
        }
        
        vnpay_payment_url = vnp.get_payment_url()
        return {"vnpay_payment_url": vnpay_payment_url}
        
    def get_client_ip(self, request):
        return request.client.host
    
    