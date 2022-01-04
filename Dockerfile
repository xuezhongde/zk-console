FROM java:8

MAINTAINER xuezhongde "xuezhongde@foxmail.com"

#VOLUMN /tmp

WORKDIR /data/apps/zk-console

ADD ./target/zk-console.jar app.jar

ENV JAVA_OPTS="-Xms128m -Xmx256m"
ENV ZK_SERVERS="localhost:2181"

EXPOSE 9010

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar