#!/bin/sh
cat example-systemready.yaml | sed s/DOCKER_USERNAME/$DOCKER_USERNAME/ | kubectl apply -f -
