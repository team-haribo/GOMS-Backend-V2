#!/bin/bash

IMAGE_NAME="goms-server"
CONTAINER_NAME="goms-container"

echo "> Starting application deployment..."
echo "> Building Docker image: $IMAGE_NAME"
docker build -t $IMAGE_NAME .
if [ $? -eq 0 ]; then
    echo "> Docker image built successfully"
else
    echo "> Failed to build Docker image"
    exit 1
fi
echo "> Starting Docker container: $CONTAINER_NAME"
docker run -d --name $CONTAINER_NAME --add-host=host.docker.internal:host-gateway -p 8080:8080 $IMAGE_NAME
if [ $? -eq 0 ]; then
    echo "> Container started successfully"
    echo "> Application is running on port 8080"
else
    echo "> Failed to start container"
    exit 1
fi