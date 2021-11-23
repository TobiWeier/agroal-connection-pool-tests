#!/bin/bash

minikube delete                                                                 #delete minikube 
minikube start --cpus 6 --memory 16384                                          #create minikube
rm -rf /tmp/postgres-operator                                                   #delete temporary postgres-operator directory 
git clone git@github.com:zalando/postgres-operator.git /tmp/postgres-operator   #clone zalando postgres-operator project
helm install postgres-operator /tmp/postgres-operator/charts/postgres-operator  #install postgres operator with helm

kubectl create -f dbcluster.yml                                                 #create postgres cluster 
echo -n "wait until cluster is ready"
until [ $(kubectl get pods -l cluster-name=connection-pool-test --ignore-not-found --no-headers | wc -l) -eq 2 ]
do
  sleep 1
  echo -n "."
done 
echo 
kubectl wait --for=condition=Ready pod connection-pool-test-0
kubectl wait --for=condition=Ready pod connection-pool-test-1

kubectl create -f pooltest.yml                                                  #run quarkus test pod
kubectl wait --for=condition=Ready pod pooltest