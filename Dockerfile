FROM ubuntu:16.04

ADD target/words-and-synonyms-2.0.jar words-and-synonyms-2.0.jar

RUN apt-get update
RUN apt-get install -y default-jre
RUN apt-get clean
RUN rm -rf /var/lib/apt/lists/*

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "words-and-synonyms-2.0.jar"]