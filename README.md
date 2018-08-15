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

	export DOCKER_USERNAME=<your user>
	export DOCKER_PASSWORD=<your password>
	sh build.sh

Deploy to your kubernetes Cluster

	sh deploy.sh

This installs the docker container as a deployment with one instance and
a service with a load balancer for access.

### Check the deployment worked

    kubectl get pods

It should display output like below. We should have one pod installed that contains our OSGi app.

	 NAME                                  READY     STATUS    RESTARTS   AGE
	 example-systemready-6b7985c88f-tcfmh   1/1       Running   0          10h

	 kubectl get services

Here we should see a service that makes our example available on an external
ip with a load balancer.

	 NAME                 TYPE           CLUSTER-IP    EXTERNAL-IP      PORT(S)          AGE
	 example-systemready  LoadBalancer   10.0.207.16   104.211.55.231   80:30215/TCP     22h

Now test the steps from "access the deployment" above with your external IP
instead of localhost and with port 80.

You can use this command to get the external IP:

    kubectl get svc example-systemready -o json | jq -r .status.loadBalancer.ingress[0].ip

### Update kubernetes deployment

Change some code.

Then do

	sh build.sh
	kubectl delete pod -l run=example-systemready

This builds and deploys the new docker image and then deletes all existing pods.
This will cause kubernetes to spawn a new pod which loads the new image.

### Test liveness

Our example is deployed in kubernetes with a liveness check. If this check fails then kubernetes will restart the pod.

To see current pods run this in a shell. It will wait and display changes.

    kubectl get pods -o wide -w
    
Now let's report not alive:

    curl -X POST http://ext-ip/control/notalive
    
After a few seconds we should see the number of restarts increase in the shell where we check for the pods.
Alternatively you can use the kubernetes dashboard and look into the running pod. It will show failed readiness and liveness checks and report that the 
instance will be killed and restarted.

### Test readiness

Our example is deployed in kubernetes with a liveness check. If this check fails then kubernetes will restart the pod.

To see current pods run this in a shell. It will wait and display changes.

    kubectl get pods -o wide -w
    
Focus on the ready column. It should report "1/1".
    
Now let's report not ready for 60 seconds:

    curl -X POST http://ext-ip/control/notready
    
Then quickly check the ready probe:

    http://ext-ip/systemready
    
It will now report RED. Keep an eye on the pod list above. It should update with "0/1" in the ready column. 
Once kubernetes consideres the pod as not ready it will take it out of the load balancer. So when we check the ready state above again it will
not respond.

After the 60 seconds it should go back to "1/1". The ready state in our probe will return to GREEN.

### Further experimentation

Scale the deployment to 2 intances and check how the cluster now behaves.

    kubectl scale deployments/example-systemready --replicas=2
