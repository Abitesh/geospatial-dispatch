# GeoSpatial Dispatch Engine

## MVP Description
A real-time backend that registers field agents with GPS coordinates, accepts user service requests, matches the nearest available agent using Euclidean distance (shortest-path algorithm coming soon), and will stream live location updates to the user over WebSockets (STOMP protocol). Data is persisted in PostgreSQL.

## Tech Stack
- **Java 21, Spring Boot 4.0.5**
- **Spring Web, Spring Data JPA, PostgreSQL**
- **Maven** for dependency management

## Current Features (Up to Day 9)
- **Health Check:** Verified application startup (`/api/health`).
- **Database Connection:** Automated PostgreSQL schema generation (`ddl-auto=update`).
- **Agent Management:** 
  - `AgentEntity` mapped to `agents` table (UUID, name, status, lat/lng, lastHeartbeatAt).
  - API to register agents and update their locations.
- **User & Ride Order Management:** 
  - `UserEntity` and `RideOrderEntity` mapped.
  - API to create ride requests.
- **Dispatch Engine:** 
  - `DispatchService` calculates the nearest `AVAILABLE` agent using Euclidean distance.
  - Automatically assigns the agent, updates ride status to `ACTIVE`, and sets agent to `DISPATCHED`.

## How to Run Locally

### Prerequisites
1. Java 21+
2. Maven 3.8+
3. PostgreSQL installed and running locally (`brew services start postgresql@18`)

### Setup Database
Before running the app, create the database in PostgreSQL:
```bash
psql postgres
CREATE DATABASE georouting;
\q
```

### Run the Application
Clone the repo and start the Spring Boot server:
```bash
git clone https://github.com/Abitesh/geospatial-dispatch.git
cd geospatial-dispatch/BACKEND/geospatial
mvn clean spring-boot:run
```

## Testing the APIs (Postman/cURL)

**1. System Health**
```http
GET http://localhost:8080/health
```

**2. Register an Agent**
```http
POST http://localhost:8080/api/agents
Content-Type: application/json

{
  "name": "Agent Lokesh",
  "status": "AVAILABLE",
  "latitude": 37.7749,
  "longitude": -122.4194
}
```

**3. Create a Ride Request**
```http
POST http://localhost:8080/api/rides
Content-Type: application/json

{
  "userId": "replace-with-user-uuid",
  "pickupLat": 37.7750,
  "pickupLng": -122.4190
}
```

**4. Trigger Dispatch (Assign Nearest Agent)**
*(Currently triggered internally, endpoint coming soon!)*

---
*Hii I'm lokesh glad to meet you!*