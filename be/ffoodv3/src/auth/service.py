from src.auth.models import User
from src.auth.schema import UserCreateModel
from sqlmodel.ext.asyncio.session import AsyncSession
from sqlmodel import select
from .utils import generate_passwd_hash

class UserService:
    async def get_user_by_email(self, email: str, session: AsyncSession)-> User:
        statement = select(User).where(User.email == email)
        result = await session.exec(statement=statement)
        user = result.first()
        return user

    async def user_exists(self, email: str, session: AsyncSession):
        user = await self.get_user_by_email(email=email, session=session)
        if user:
            return True
        else:
            return False
        
    async def create_user(self, user_data: UserCreateModel, session: AsyncSession):
        user_data_dict = user_data.model_dump()
        new_user = User(
            **user_data_dict
        )
        new_user.password_hash = generate_passwd_hash(user_data_dict['password'])
        new_user.role = 'user'
        session.add(new_user)
        await session.commit()
        return new_user
        
        