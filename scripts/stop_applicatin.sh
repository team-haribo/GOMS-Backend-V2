IMAGE_NAME=goms-server
CONTAINER_NAME=goms-server-container
DOCKERFILE_NAME=Dockerfile

echo "> 현재 실행 중인 Docker 컨테이너 ID 확인" >> /home/ubuntu/gsm-sv-deploy/deploy.log
RUNNING_CONTAINER_ID=$(docker ps -q -f name=$CONTAINER_NAME)
EXISTING_CONTAINER_ID=$(docker ps -a -q -f name=$CONTAINER_NAME)

if [ -z $RUNNING_CONTAINER_ID ]
then
  echo "> 현재 구동중인 Docker 컨테이너가 없으므로 종료하지 않습니다." >> /home/ubuntu/gsm-sv-deploy/deploy.log
else
  echo "> sudo docker stop $RUNNING_CONTAINER_ID"
  sudo docker stop $RUNNING_CONTAINER_ID
fi

if [ -z $EXISTING_CONTAINER_ID ]
then
  echo "> 현재 존재하는 Docker 컨테이너가 없으므로 삭제하지 않습니다." >> /home/ubuntu/gsm-sv-deploy/deploy.log
else
  echo "> sudo docker rm $EXISTING_CONTAINER_ID"
  sudo docker rm $EXISTING_CONTAINER_ID
fi

docker build -t $IMAGE_NAME -f $DOCKERFILE_NAME .
docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME