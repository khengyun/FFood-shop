from pydantic import BaseModel, Field
from datetime import datetime
import uuid 


class UserCreateModel(BaseModel):
    username: str = Field(max_length=8)
    email: str = Field(max_length=40)
    password: str = Field(max_length=12)
    fullname: str = Field(max_length=25)
    first_name: str = Field(max_length=25)
    last_name: str = Field(max_length=25)

# class UserCreateModel(BaseModel):
#     username: str = Field(max_length=8)
#     email: str = Field(max_length=40)
#     password: str = Field(max_length=12)

class UserModel(BaseModel):
    uid: uuid.UUID
    username: str
    fullname: str
    email: str
    first_name: str
    last_name: str
    password_hash: str = Field(exclude=True)
    is_verified: bool
    created_at: datetime 
    updated_at: datetime 
    
    
    
class UserLoginModel(BaseModel):
    email: str = Field(max_length=40)
    password: str = Field(max_length=12)