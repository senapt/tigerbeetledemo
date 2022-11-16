# update as required

DOCKER_VER=0.0.1
docker tag tigerbeetledemo:latest ianmorgan/tigerbeetledemo:$DOCKER_VER
docker push ianmorgan/tigerbeetledemo:$DOCKER_VER