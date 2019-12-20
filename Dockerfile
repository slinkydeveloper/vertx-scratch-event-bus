FROM openjdk:8-alpine

ENV VERTICLE_NAME io.slinkydeveloper.vertx.scratch.ScratchEventBusVerticle
ENV VERTICLE_FILE target/vertx-scratch-event-bus-1.0-SNAPSHOT.jar

ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

COPY $VERTICLE_FILE $VERTICLE_HOME/vertx-scratch-event-bus.jar

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $VERTICLE_HOME/vertx-scratch-event-bus.jar"]
