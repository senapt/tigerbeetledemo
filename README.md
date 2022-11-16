Tiger Beetle Demo
=================

A minimal project to investigate problems using the tigerbeetle jar under Docker. All it does 
is try to create a new account.

## Running locally 

```bash
mvn clean package 
# local server
java -jar target/tigerbeetledemo.jar 

# remote cluster 
TIGERBEETLE=123.123.123.1:3000,123.123.123.2:3000,123.123.123.3:3000 java -jar target/tigerbeetledemo.jar 
```

## Docker 
```bash
./buildDocker.sh

# local cluster 
docker run tigerbeetledemo 

# remote cluster
docker run -e TIGERBEETLE=123.123.123.1:3000,123.123.123.2:3000,123.123.123.3:3000 java -tigerbeetledemo 
```

To create a public image in a repe update `pushImage.sh` as required.