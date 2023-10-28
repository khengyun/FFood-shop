# google_auth.py

from google.oauth2 import id_token
import datetime
from google.auth.transport import requests
# from fastapi import HTTPException, Query
from config import CLIENT_ID,db_config

import pymssql
import hashlib

class GoogleAuth:
    def __init__(self):
        self.client_id = CLIENT_ID
        self.db_config  = db_config
        self.account_type = "user"
        
        self.account_username = None
        self.account_email = None
        self.account_password = None
        

    def login(self, idinfo):
        email = idinfo.get("email", None)
        
        if email:
            # Check if the email exists in the database
            if self.email_exists_in_database(email):
                print("email checked: ", email)
                return True
        # If idinfo is not provided or the email doesn't exist, go to the register method
        return self.register(idinfo)

    def email_exists_in_database(self, email):
        try:
            with pymssql.connect(**self.db_config) as conn:
                cursor = conn.cursor()
                cursor.execute("SELECT account_email FROM Account WHERE account_email = %s", (email,))
                result = cursor.fetchone()
                return result is not None
        except Exception as e:
            return False

    def register(self, idinfo):
        if idinfo:
            self.account_username = idinfo["email"].split("@")[0]
            self.account_email = idinfo["email"]
            self.account_password = idinfo["sub"]
            
            # Encode the password to bytes using UTF-8
            password_bytes = self.account_password.encode('utf-8')
            # Hash the password
            hash_account_password = hashlib.md5(password_bytes).hexdigest()

            print("account_username: ", self.account_username)
            print("account_email: ", self.account_email)
            
            
            try:
                with pymssql.connect(**self.db_config) as conn:
                    cursor = conn.cursor()
                    cursor.execute("""
                        INSERT INTO Account (account_username, account_email, account_password, account_type)
                        VALUES (%s, %s, %s, %s)
                    """, (self.account_username, self.account_email, hash_account_password, self.account_type))
                    conn.commit()

                return "Registration successful"
            except Exception as e:
                return "Registration failed: " + str(e)
        else:
            return "Registration failed: idinfo is missing"

    def verify_google_id_token(self, token):
        try:
            idinfo = id_token.verify_oauth2_token(token, requests.Request(), self.client_id)
            
            self.account_email = idinfo["email"]
            self.account_password = idinfo["sub"]

            # Check if the aud claim matches the client ID
            if idinfo["aud"] != self.client_id:
                raise ValueError("Invalid aud claim")

            # Convert the 'exp' claim timestamp to a datetime object
            exp_timestamp = idinfo["exp"]
            exp_datetime = datetime.datetime.utcfromtimestamp(exp_timestamp)

            # Check if the exp claim is greater than the current time
            if exp_datetime <= datetime.datetime.utcnow():
                raise ValueError("Expired token")

            # If all checks pass, return email and password
            if self.login(idinfo):

                
                response_data = {
                    "email": self.account_email,
                    "password": self.account_password
                }
                
                print("response_data: ", response_data)
                return response_data

        except ValueError:
            # Invalid token
            return False
    
        
        
def main():
    auth = GoogleAuth()

    # Replace 'YOUR_GOOGLE_ID_TOKEN' with an actual Google ID token for testing
    google_id_token = ""
    login_result = auth.verify_google_id_token(google_id_token)
    if login_result:
        print("Login successful")
    else:
        print("Login failed")

if __name__ == '__main__':
    main()
