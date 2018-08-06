# OSGi Kubernetes example

[![Build Status](https://travis-ci.com/cschneider/osgi-example-kubernetes.svg?branch=master)](https://travis-ci.com/cschneider/osgi-example-kubernetes)

This example shows how to create a small OSGi based application and deploy it to kubernetes.

## Prerequisites

Install
- jdk 1.8
- maven >= 3.5
- docker

## Source

Checkout the source

	git clone git@github.com:cschneider/osgi-example-kubernetes.git

Start eclipse and import from top level directory as existing maven projects.

## Build

	mvn clean install

## Run standalone

	java -jar starter/target/app.jar

### Access the deployment

	http://localhost:8080/hello

This should return Hello World and is served by a JAX-RS service.

	http://localhost:8080/systemready

Returns the ready state of the system. At the start this might return YELLOW but
after a very short while it should return GREEN.

	http://localhost:8080/system/console

Allows access to the felix webconsole to look into all details of the system.

## Run in Kubernetes

You need access to a kubernetes cluster. To achieve this either install minikube
or get a kubernetes cluster from a cloud provider.

For my tests I installed the kubernetes service on Azure. This only needs a few
clicks. The default settings work fine.

This does a maven build plus docker container creation and docker push.

	sh build.sh

Deploy to your kubernetes Cluster

	kubectl apply -f example-kubernetes

This installs the docker container as a deployment with one instance.
Additionally it creates a service with a load balancer.

### Check the deployment worked

   kubectl get pods

	 It should display output like below. We should have one pod installed that
	 contains our OSGi app.

	 NAME                                  READY     STATUS    RESTARTS   AGE
	 example-kubernetes-6b7985c88f-tcfmh   1/1       Running   0          10h

	 kubectl get services

	 Here we should see a service that makes our example available on an external
	 ip with a load balancer.

	 NAME                 TYPE           CLUSTER-IP    EXTERNAL-IP      PORT(S)          AGE
	 example-kubernetes   LoadBalancer   10.0.207.16   104.211.55.231   8080:30215/TCP   22h

Now test the steps from "access the deployment" abovce with the external IP
instead of localhost.

### Update kubernetes deployment

Change some code.

Then do

	sh build.sh
	kubectl delete pod -l run=example-kubernetes

This builds and deploys the new docker image and then deletes all existing pods.
This will cause kubernetes to spawn a new pod which loads the new image.
