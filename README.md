# zk-console

### step 1: build springboot project
```
mvn clean package -DskipTests
```

### step 2: build docker image
```
docker build -t zk-console:v0.1 .
```

### step 3: docker run
```
docker run --name docker-zk-console -d -p 9010:9010 \
    -e JAVA_OPTS="-Xms128m -Xmx256m" \
    -e ZK_SERVERS="localhost:2181" \
    zk-console:v0.1
```
