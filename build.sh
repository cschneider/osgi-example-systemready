export TAG=$DOCKER_USERNAME/example.systemready
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
docker build -t $TAG starter
docker push $TAG
