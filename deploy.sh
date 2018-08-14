#!/bin/sh
#kubectl apply -f adaptto-2018-example.yaml
helm install --name adapt-to-2018 ./helm --set rbac.create=true --set image.repository="${DOCKER_REGISTRY}/adapt-to-2018"