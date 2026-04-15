# GeoSpatial Dispatch Engine

## MVP Description
A real-time backend that registers field agents with GPS coordinates, accepts
user service requests, matches the nearest available agent using Dijkstra's
shortest-path algorithm, and streams live location updates to the user
over WebSockets (STOMP protocol). Data is persisted in PostgreSQL with PostGIS.

## Tech Stack
- Java 21, Spring Boot 4.0.5
- Spring Web, WebSocket, Spring Data JPA, PostgreSQL

## How to Run Locally
Prerequisites: Java 21+, Maven 3.8+

```bash
git clone https://github.com/Abitesh/geospatial-dispatch.git
cd geospatial-dispatch
mvn spring-boot:run
```

Health check: GET http://localhost:8080/health
Hii I'm lokesh glad to meet you!