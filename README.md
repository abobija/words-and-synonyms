# words-and-synonyms
Application written in Java using Spring Boot framework. Application offers a way of adding new words and adding synonyms to existing words. Besides of creating new words and synonyms, user is able to search words as well.

## Demo

_Click on image..._

[![Words And Synonyms - Java Spring Boot](https://img.youtube.com/vi/Hd75inwVoBs/mqdefault.jpg)](https://www.youtube.com/watch?v=Hd75inwVoBs)

## Preview

Either you choose Maven method or method with Docker Container, at the end of the day open browser and navigate to `http://localhost:8080`.

### Using Maven

> For Maven run smoothly make sure that `JAVA_HOME` environment variable points to `jdk` (ie.: `C:\Program Files\Java\jdk1.8.0_221`)

> Generated `.jar` file will be located in `/target` folder.

```
mvn package
java -jar target/words-and-synonyms-3.0.jar
````

### Using Docker Container

```
docker run --rm -it -p 8080:8080 abobija/words-and-synonyms
```

## Run Tests

```
mvn test
```

## Build Docker Image

```
mvn package
docker build -t name_of_image .
```

## Author
 
[Alija Bobija](http://abobija.com)
