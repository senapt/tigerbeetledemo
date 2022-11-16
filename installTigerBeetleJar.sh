# update as required
# will also need to update pom.xml to match
export TIGERBEETLE_JAR_VER=0.0.20221114c

mvn --no-transfer-progress install:install-file \
   -Dfile=src/main/resources/bin/tigerbeetle-java-$TIGERBEETLE_JAR_VER.jar  \
   -DgroupId=com.tigerbeetle \
   -DartifactId=tigerbeetle-java\
   -Dversion=$TIGERBEETLE_JAR_VER \
   -Dpackaging=jar \
   -DgeneratePom=true