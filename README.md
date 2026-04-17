# GeoSpatial Dispatch Engine

A real-time backend that registers field agents with GPS coordinates, accepts user
service requests, matches the nearest available agent using Euclidean distance, and
streams live location updates to the user over WebSockets (STOMP protocol).
Data is persisted in PostgreSQL.

---

## Architecture Overview
HTTP Clients / WebSocket Clients
│
┌─────▼──────┐
│ Controllers │ ← AgentController, RequestController, RoutingController
└─────┬───────┘
│
┌─────▼──────┐
│ Services │ ← AgentService, DispatchService, RoutingService, NotificationService
└─────┬───────┘
│
┌─────▼──────┐
│Repositories │ ← AgentRepository, RideOrderRepository, UserRepository
└─────┬───────┘
│
┌─────▼──────┐
│ PostgreSQL │ ← georouting database
└────────────┘

WebSocket Flow:
Agent (browser/app) → /ws (SockJS) → /app/agent/location
→ AgentService.updateLocation()
→ NotificationService.broadcastAgentLocation()
→ /topic/rides/{rideId} ← subscribed by user

text

---

## Tech Stack

| Layer       | Technology                        |
|-------------|-----------------------------------|
| Language    | Java 21                           |
| Framework   | Spring Boot 4.0.5                 |
| DB          | PostgreSQL + Spring Data JPA      |
| WebSocket   | STOMP over SockJS                 |
| Build Tool  | Maven 3.8+                        |
| Routing     | Dijkstra (in-memory graph)        |

---

## How to Run Locally

### Prerequisites
- Java 21+
- Maven 3.8+
- PostgreSQL running locally

### Step 1 — Create the database
```bash
psql postgres
CREATE DATABASE georouting;
\q
```

### Step 2 — Configure credentials
Open `src/main/resources/application.yml` and set your PostgreSQL username:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/georouting
    username: your_postgres_username
    password: your_password
```

### Step 3 — Run the backend
```bash
cd BACKEND/geospatial
mvn clean spring-boot:run
```

Server starts on: `http://localhost:8080`

---

## Key API Endpoints

### Health Check
```http
GET /health
Response: "System is operational"
```

### Register an Agent
```http
POST /api/agents
Content-Type: application/json

{
  "name": "Agent Lokesh",
  "status": "AVAILABLE",
  "latitude": 12.9716,
  "longitude": 77.5946
}
```

### Get All Agents
```http
GET /api/agents
```

### Create a Ride Request
```http
POST /api/rides
Content-Type: application/json

{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "pickupLat": 12.9720,
  "pickupLng": 77.5950
}
```

### Get Routing Path (Dijkstra Debug)
```http
GET /api/routes?fromLat=12.97&fromLng=77.59&toLat=12.98&toLng=77.60
```

---

## WebSocket Usage (STOMP)

### Connection
```javascript
const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);
stompClient.connect({}, () => {
    stompClient.subscribe('/topic/rides/{rideId}', (message) => {
        console.log(JSON.parse(message.body));
    });
});
```

### Send Agent Location Update
```javascript
stompClient.send('/app/agent/location', {}, JSON.stringify({
    agentId: 'your-agent-uuid',
    latitude: 12.9725,
    longitude: 77.5955
}));
```

---

## Running Tests
```bash
mvn clean test
```

---

*Built by Abitesh and Lokesh — 16-Day Sprint*