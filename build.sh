export TAG="${DOCKER_REGISTRY}/adapt-to-2018"
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
docker build -t $TAG starter
docker push $TAG
