# agroal-connection-pool-tests

## preconditions

- git
- minikube installation
- helm installation

## steps to reproduce

1. Start minikube with database cluster and pool test application

        ./run-test.sh

2. Check metrics with 

        watch -n 2 kubectl exec -it pooltest -- "curl http://localhost:8080/q/metrics | grep -E '^application'"

3. Check database cluster status with

        kubectl exec -it connection-pool-test-0 -- patronictl list

4. Kill database leader pod with

        kubectl delete pod connection-pool-test-<N>
