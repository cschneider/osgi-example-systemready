#!/bin/sh
kubectl apply -f example-kubernetes.yaml

#kubectl run example-kubernetes --image=cschneider/osgi.example.kubernetes --port=8080
#kubectl expose deployment example-kubernetes --type=LoadBalancer
