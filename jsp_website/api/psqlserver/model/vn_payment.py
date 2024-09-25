# vn_payment.py
import hashlib
import hmac
import urllib.parse

class Settings:
    VNPAY_TMN_CODE = '2FB67LT2'
    VNPAY_RETURN_URL = 'http://localhost:8001/payment_return'
    VNPAY_PAYMENT_URL = 'https://sandbox.vnpayment.vn/paymentv2/vpcpay.html'
    VNPAY_HASH_SECRET_KEY = 'PXLKKGIQSXIUUZHUUPXUTNXDNFFWSBAD'

class Vnpay:
    requestData = {}
    responseData = {}

    def __init__(self, settings):
        self.settings = settings

    def get_payment_url(self):
        inputData = sorted(self.requestData.items())
        queryString = ''
        hasData = ''
        seq = 0
        for key, val in inputData:
            if seq == 1:
                queryString = queryString + "&" + key + '=' + urllib.parse.quote_plus(str(val))
            else:
                seq = 1
                queryString = key + '=' + urllib.parse.quote_plus(str(val))

        hashValue = self.__hmacsha512(self.settings.VNPAY_HASH_SECRET_KEY, queryString)
        return self.settings.VNPAY_PAYMENT_URL + "?" + queryString + '&vnp_SecureHash=' + hashValue

    def validate_response(self):
        print("==============Validate response 2===============")
        print(self.responseData)
        print("==============================================")
        
        if 'vnp_SecureHash' in self.responseData:
            vnp_SecureHash = self.responseData['vnp_SecureHash']
            # Remove hash params
            if 'vnp_SecureHash' in self.responseData:
                self.responseData.pop('vnp_SecureHash')

            if 'vnp_SecureHashType' in self.responseData:
                self.responseData.pop('vnp_SecureHashType')
                
            inputData = sorted(self.responseData.items())
            hasData = ''
            seq = 0
            for key, val in inputData:
                if str(key).startswith('vnp_'):
                    if seq == 1:
                        hasData = hasData + "&" + str(key) + '=' + urllib.parse.quote_plus(str(val))
                    else:
                        seq = 1
                        hasData = str(key) + '=' + urllib.parse.quote_plus(str(val))
            hashValue = self.__hmacsha512(self.settings.VNPAY_HASH_SECRET_KEY, hasData)

            print(
                'Validate debug, HashData:' + hasData + "\n HashValue:" + hashValue + "\nInputHash:" + vnp_SecureHash)

            return vnp_SecureHash == hashValue
        else:
            return False

    @staticmethod
    def __hmacsha512(key, data):
        byteKey = key.encode('utf-8')
        byteData = data.encode('utf-8')
        return hmac.new(byteKey, byteData, hashlib.sha512).hexdigest()
