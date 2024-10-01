from sqlmodel import SQLModel
from sqlalchemy.ext.asyncio import  create_async_engine
from sqlmodel.ext.asyncio.session import AsyncSession
from src.config import Config
from sqlalchemy.orm import sessionmaker

print("=====================================")
print(Config.DATABASE_URL)
print("=====================================")


# Create an asynchronous engine using asyncpg
async_engine = create_async_engine(
    url=Config.DATABASE_URL,
    echo=True
)

async def init_db():
    try:
        # Start a new transaction and initialize the database
        async with async_engine.begin() as conn:
            from src.auth.models import User
            
            
            
            await conn.run_sync(SQLModel.metadata.create_all)
        print("Database initialized successfully")
    except Exception as e:
        print(f"Failed to initialize the database: {e}")
        
        
async def get_session() -> AsyncSession: # type: ignore
    Session = sessionmaker(
        bind=async_engine,
        class_=AsyncSession,
        expire_on_commit=False
    )
    async with Session() as session:
        yield session


