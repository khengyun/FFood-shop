from passlib.context import CryptContext
from datetime import timedelta, datetime
from src.config import Config
import jwt
import logging

import uuid

passwd_context = CryptContext(
    schemes=['bcrypt']
)


ACCESS_TOKEN_EXPIRY = 3600

def generate_passwd_hash(password: str)-> str:
    hash = passwd_context.hash (password)
    return hash

def verify_password(password: str, hash: str)-> bool:
    return passwd_context.verify(password, hash=hash)


def create_access_token(user_data: dict, expiry: timedelta = None, refresh: bool= False):
    payload = {}
    
    payload['user'] = user_data
    payload['exp'] = datetime.now() + (expiry if expiry is not None else timedelta(seconds=ACCESS_TOKEN_EXPIRY) )
    payload['jti'] = str(uuid.uuid4())
    
    payload['refresh'] = refresh
    token = jwt.encode(
        payload=payload,
        key=Config.JWT_SECRET,
        algorithm=Config.JWT_ALGORITHM
    )

    return token


def decode_token(token: str)-> dict:
    try:
        token_data = jwt.decode(
            jwt=token,
            key=Config.JWT_SECRET,
            algorithms=Config.JWT_ALGORITHM
        )
        return token_data
    except jwt.PyJWKError as e:
        logging.exception(e)
        return None