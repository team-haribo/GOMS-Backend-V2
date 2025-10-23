docker build -t $IMAGE_NAME -f $DOCKERFILE_NAME .
docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME