# OSGi Kubernetes example

This example shows how to create a small OSGi based application and deploy it to kubernetes.

## Prerequisites

Install
- jdk 1.8
- maven >= 3.5

## Source

Checkout the source

	git clone git@github.com:cschneider/osgi-example-kubernetes.git

Start eclipse and import from top level directory as existing maven projects.

## Build

	mvn clean install

## Run standalone

	java -jar starter/target/app.jar

## Run in Kubernetes

Maven build plus docker container creation and push

	sh build.sh

Deploy to your kubernetes Cluster

	kubectl apply -f example-kubernetes
