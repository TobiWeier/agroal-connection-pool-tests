# agroal-connection-pool-tests

## preconditions

1. minikube installation
2. helm installation

## steps to start

    $ minikube start --cpus 6 --memory 16384
    $ rm -rf /tmp/postgres-operator
    $ git clone git@github.com:zalando/postgres-operator.git /tmp/postgres-operator
    $ helm install postgres-operator /tmp/postgres-operator/charts/postgres-operator
    $ rm -rf /tmp/agroal-connection-pool-tests
    $ git clone git@github.com:TobiWeier/agroal-connection-pool-tests.git /tmp/agroal-connection-pool-tests
    $ cd /tmp/agroal-connection-pool-tests
    $ kubectl create -f dbcluster.yml
    $ kubectl create -f pooltest.yml
