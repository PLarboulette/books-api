FROM openjdk:latest

LABEL MAINTAINER="Pierre LARBOULETTE <larboulette.pierre@gmail.com>"

COPY target/universal/stage /opt/docker

WORKDIR /opt/docker

RUN ["chown", "-R", "daemon:daemon", "."]

USER daemon

EXPOSE 9000

CMD ["/opt/docker/bin/books-api"]
