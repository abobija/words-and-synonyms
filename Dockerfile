FROM openjdk:8u181-jdk
ADD target/words-and-synonyms-2.0.jar words-and-synonyms-2.0.jar
RUN apt-get update && apt-get install -y --no-install-recommends openjfx && rm -rf /var/lib/apt/lists/*
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "words-and-synonyms-2.0.jar"]