FROM gradle:8.1-jdk17 as builder
USER root

ADD . .

RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz


RUN ["gradle", "build", "-x", "test", "--stacktrace"]


#FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
FROM openjdk:17-alpine
USER root

RUN mkdir /opt/app-root
WORKDIR /opt/app-root
RUN chmod g+w /opt/app-root
COPY --from=builder /home/gradle/build/container .
EXPOSE 8088

ENTRYPOINT ["java", "-jar", "atm-kotlin-basic.jar"]