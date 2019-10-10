# FROM ubuntu:latest
FROM python:3.6-slim

RUN apt-get update -y
RUN apt-get install -y python-pip python-dev build-essential

WORKDIR /app
ADD . /app

EXPOSE 80

RUN pip install -r requirements.txt

CMD ["python", "app.py"]
