# Dockerfile
FROM python:3.11-slim

# Set working directory
WORKDIR /workspace

# Install Poetry
RUN pip install poetry

# Add Poetry to PATH
ENV PATH="/root/.local/bin:${PATH}"

# Copy the dependency descriptor and install dependencies
COPY pyproject.toml poetry.lock* ./
RUN poetry lock --no-update
RUN poetry install

# Copy the rest of the application code
COPY . .

# Expose the port Uvicorn will run on
EXPOSE 8000

# Default command
CMD ["poetry", "dev", "uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
