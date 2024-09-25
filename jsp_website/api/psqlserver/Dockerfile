FROM tiangolo/uvicorn-gunicorn-fastapi:latest

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt
RUN export PROTOCOL_BUFFERS_PYTHON_IMPLEMENTATION=python

COPY . .
WORKDIR /app

EXPOSE 8001

CMD ["python3", "app.py"]