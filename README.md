Cloud Bowl Sample - Java Spring Boot
------------------------------------

To make changes, edit the `src/main/java/hello/Application.java` file.

Run Locally:
```
./mvnw spring-boot:run
```

[![Run on Google Cloud](https://deploy.cloud.run/button.svg)](https://deploy.cloud.run)

Containerize & Run Locally:
```
export PROJECT_ID=YOUR_GCP_PROJECT_ID
pack build --builder=gcr.io/buildpacks/builder gcr.io/$PROJECT_ID/cloudbowl-samples-java-springboot
docker run -it -ePORT=8080 -p8080:8080 gcr.io/$PROJECT_ID/cloudbowl-samples-java-springboot
```

curl -d '{
  "_links": {
    "self": {
      "href": "https://foo.com"
    }
  },
  "arena": {
    "dims": [4,3],
    "state": {
      "https://foo.com": {
        "x": 0,
        "y": 0,
        "direction": "N",
        "wasHit": false,
        "score": 0
      }
    }
  }
}' -H "Content-Type: application/json" -X POST -w "\n" \
  http://localhost:8080
