from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    DATABASE_URL: str
    JWT_SECRET: str 
    JWT_ALGORITHM: str 
    REDIS_HOST: str = "localhost"
    REDIS_PORT: int = 6379
        
    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8",
        extra='ignore'
    )
Config = Settings()