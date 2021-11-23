#!/bin/bash

curl -L https://github.com/TobiWeier/agroal-connection-pool-tests/tarball/main | tar xz
mv TobiWeier* agroal-connection-pool-tests
cd agroal-connection-pool-tests
mvn clean quarkus:dev
