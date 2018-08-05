mvn clean install
export TAG=cschneider/osgi.example.kubernetes
docker build -t $TAG starter
docker push $TAG
