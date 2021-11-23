# agroal-connection-pool-tests



# Steps to reproduce

## preconditions

1. minikube installation
2. helm installation


## steps

    #start minikube
    minikube start
    #install postgres cluster
    git clone git@github.com:zalando/postgres-operator.git /tmp/postgres-operator
    helm install postgres-operator /tmp/postgres-operator/charts/postgres-operator
    kubectl create -f dbcluster.yml
    kubectl create -f pooltest.yml
