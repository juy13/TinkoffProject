FROM openjdk:11-slim

#env
ENV JDBC_URL jdbc:postgresql://localhost:5432/tinkoffdb
ENV UserPassword tinkoff
ENV User tinkoff
#copy jar from target to image
FROM gradle:7.4-jdk11-alpine as builder
ADD build/libs/mpdback-0.0.1.jar /usr/src/mpdback-0.0.1.jar

#set work dir to jar-file path
WORKDIR /usr/src
#port expose
EXPOSE 8080

RUN mkdir /var/tmp/mpd
RUN mkdir /var/tmp/mpdEnc

ENTRYPOINT java -Dspring.datasource.url=$JDBC_URL -Dspring.datasource.username=$UserPassword -Dspring.datasource.password=$User  -jar mpdback-0.0.1.jar