# Replace cschneider with your docker login
export TAG=cschneider/osgi.example.kubernetes

docker build -t $TAG starter
docker push $TAG
