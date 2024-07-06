# Use an official Python runtime as a parent image
FROM tiangolo/uvicorn-gunicorn-fastapi:python3.9

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt
RUN export PROTOCOL_BUFFERS_PYTHON_IMPLEMENTATION=python

COPY . .

EXPOSE 8100

# Run app.py when the container launches
CMD ["python3", "app.py"]
