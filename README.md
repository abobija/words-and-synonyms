# words-and-synonyms
Spring boot Words and Synonyms application

## Preview

Either you choose Maven method or method with Docker Container, at the end of the day open browser and navigate to `http://localhost:8080`.

### Using Maven

> For Maven run smoothly make sure that `JAVA_HOME` environment variable points to `jdk` (ie.: `C:\Program Files\Java\jdk1.8.0_221`)

> Generated `.jar` file will be located in `/target` folder.

```
mvn package
java -jar target/words-and-synonyms-2.0.jar
````

### Using Docker Container

```
docker run --rm -it -p 8080:8080 abobija/words-and-synonyms
```

## Build Docker Image

```
docker build -t name_of_image .
```

## Demo

_Click on image..._

[![Words And Synonyms - Java Spring Boot](https://img.youtube.com/vi/Hd75inwVoBs/mqdefault.jpg)](https://www.youtube.com/watch?v=Hd75inwVoBs)

## Author

[Alija Bobija](http://abobija.com)
