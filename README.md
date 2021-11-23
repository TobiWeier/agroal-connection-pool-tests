# agroal-connection-pool-tests

## preconditions

1. minikube installation
2. helm installation

## steps to start

1. minikube start
2. git clone git@github.com:zalando/postgres-operator.git /tmp/postgres-operator
3. helm install postgres-operator /tmp/postgres-operator/charts/postgres-operator
4. kubectl create -f dbcluster.yml
5. kubectl create -f pooltest.yml
