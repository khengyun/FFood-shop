from fastapi import APIRouter, HTTPException, status, Body, Depends
from fastapi.responses import JSONResponse
from src.auth.schema import UserCreateModel, UserModel, UserLoginModel
from src.auth.service import UserService
from sqlmodel.ext.asyncio.session import AsyncSession
from src.db.main import get_session
from .utils import create_access_token, decode_token, verify_password
from datetime import timedelta
from datetime import datetime
from .dependencies import (
    RefreshTokenBearer,
    AccessTokenBearer,
    RoleChecker,
    get_current_user,
)
from src.db.redis import add_jti_to_blocklist

auth_router = APIRouter()
user_service = UserService()
role_check = RoleChecker(["admin", "user"])
REFRESH_TOKEN_EXPIRY = 2


@auth_router.post(
    "/signup", response_model=UserModel, status_code=status.HTTP_201_CREATED
)
async def create_user_Account(
    user_data: UserCreateModel, session: AsyncSession = Depends(get_session)
):
    email = user_data.email
    user_exist = await user_service.user_exists(email=email, session=session)
    if user_exist:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="User Email Exist, Cant Create User",
        )
    else:
        new_user = await user_service.create_user(user_data=user_data, session=session)
        return new_user


@auth_router.post("/login")
async def login_users(
    login_data: UserLoginModel, session: AsyncSession = Depends(get_session)
):
    email = login_data.email
    password = login_data.password

    user = await user_service.get_user_by_email(email=email, session=session)

    if user is not None:
        password_valid = verify_password(password=password, hash=user.password_hash)

        if password_valid:
            access_token = create_access_token(
                user_data={
                    "email": user.email,
                    "user_uid": str(user.uid),
                    "role": user.role,
                }
            )

            refresh_token = create_access_token(
                user_data={"email": user.email, "user_uid": str(user.uid)},
                refresh=True,
                expiry=timedelta(days=REFRESH_TOKEN_EXPIRY),
            )
            return JSONResponse(
                content={
                    "message": "success",
                    "access_token": access_token,
                    "refresh_token": refresh_token,
                    "user": {"email": user.email, "uid": str(user.uid)},
                }
            )
    raise HTTPException(
        status_code=status.HTTP_403_FORBIDDEN, detail="Invalid Email Or Password"
    )


@auth_router.get("/refresh_token")
async def get_new_access_token(token_details: dict = Depends(RefreshTokenBearer())):
    expiry_timestamp = token_details["exp"]

    if datetime.fromtimestamp(expiry_timestamp) > datetime.now():
        new_access_token = create_access_token(user_data=token_details["user"])

        return JSONResponse(content={"access_token": new_access_token})

    raise HTTPException(
        status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid Or Expired token"
    )


@auth_router.get("/me")
async def get_current_user(
    user: dict = Depends(get_current_user), _: bool = Depends(role_check)
):
    return user


@auth_router.get("/logout")
async def revooke_token(token_detailes: dict = Depends(AccessTokenBearer())):

    jti = token_detailes["jti"]
    await add_jti_to_blocklist(jti=jti)

    return JSONResponse(
        content={
            "message": "Logged Our Success",
        },
        status_code=status.HTTP_200_OK,
    )
