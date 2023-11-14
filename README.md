# Measure Service
This Service is responsible to receive sensor measures such as thermometers  and publish into a 
topic

## Requirements

### Software
* Java JDK >=17
* Docker and Docker Compose

### IDE
* Change properties encoding to UTF-8

### HTTP Clients 
The next HTTP Clients could be used to call the API's of this service

#### HTTP Yac
````bash
npm install -g httpyac
````
#### HTTP Pie
````bash
pip install httpie
````

## Usage
- Compile
```bash
mvn clean install
```
- 
- Build Docker Image (spring)
```bash
mvn clean package
mvn spring-boot:build-image
```

- Run Application
```bash
docker-compose up -d
mvn spring-boot:run
```

- Local Test
```bash
http  POST localhost:8080/measures deviceId=123 value=27 unit=C measureAt=2023-11-14T17:17:47.525Z
```