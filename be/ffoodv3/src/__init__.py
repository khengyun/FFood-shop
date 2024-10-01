# main.py
from fastapi import FastAPI
from src.books.routers import books_router
from src.auth.routers import auth_router
from contextlib import asynccontextmanager
from src.db.main import init_db

@asynccontextmanager
async def life_span(app:FastAPI):
    print(f"server is running ....")
    await init_db()
    yield
    print(f"server has been stop ....")
    


VERSION = "v1"


app = FastAPI(
    title="BookManager",
    description="A book rest api for book review service !!!",
    version=VERSION,
    lifespan=life_span  
)

app.include_router(books_router, prefix=f"/api/{VERSION}/books", tags=['books'])
app.include_router(auth_router, prefix=f"/api/{VERSION}/auths", tags=['auths'])


