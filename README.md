# GeoSpatial Dispatch Engine 🚀

This full-stack, real-time location-based service application was built for the CS2016 – Object Oriented Design Programming course. The engine registers field agents with GPS coordinates, accepts user service requests, and auto-assigns the nearest available agent utilizing native PostGIS spatial queries. It features real-time agent movement tracking streamed directly to a Leaflet.js map interface over WebSockets using the STOMP protocol, alongside dynamic road-network routing powered by the OSRM API.

## Architecture Overview

```text
       [ Frontend UI - Leaflet.js + Nominatim + OSRM ]
                            │
               HTTP / WebSocket (STOMP)
                            │
┌───────────────────────────▼───────────────────────────┐
│                     CONTROLLERS                       │
│    AgentController, RideController, RoutingController │
└───────────────────────────┬───────────────────────────┘
                            │
┌───────────────────────────▼───────────────────────────┐
│                      SERVICES                         │
│  AgentService, RideService, NotificationService       │
└───────────────────────────┬───────────────────────────┘
                            │
┌───────────────────────────▼───────────────────────────┐
│                    REPOSITORIES                       │
│  AgentRepository, RideOrderRepository, UserRepository │
└───────────────────────────┬───────────────────────────┘
                            │
┌───────────────────────────▼───────────────────────────┐
│             POSTGRESQL + POSTGIS (SRID 4326)          │
│                 georouting database                   │
└───────────────────────────────────────────────────────┘
```

## Technology Stack

- Backend: Java 21, Spring Boot, Spring Data JPA
- Database: PostgreSQL with PostGIS Spatial Extension
- Frontend: HTML5, CSS3, JavaScript, Leaflet.js
- Real-Time: WebSockets (STOMP over SockJS)
- Routing: Open Source Routing Machine (OSRM) API
- Geocoding: Nominatim (OpenStreetMap)

## Real-Time WebSocket Flow

1. Agent simulator connects to `/ws` via SockJS
2. Location updates are sent to `/app/agent/location`
3. AgentService updates the PostGIS geometry in the database
4. NotificationService broadcasts the agent's new location
5. The payload is pushed to `/topic/rides/{rideId}`, which the passenger UI subscribes to

## Local Setup Instructions

Ensure you have Java 21+, Maven 3.8+, and a local PostgreSQL instance with the PostGIS extension installed (e.g., `brew install postgis` on macOS).

1. Create the database and enable spatial features.
```sql
psql postgres
CREATE DATABASE georouting;
\c georouting
CREATE EXTENSION IF NOT EXISTS postgis;
\q
```

2. Configure your credentials by opening `src/main/resources/application.yml` and setting your database details.
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/georouting
    username: your_postgres_username
    password: your_password
```

3. Run the backend by navigating to the backend directory and starting the server.
```bash
cd BACKEND/geospatial
mvn clean spring-boot:run
```

4. Run the frontend by simply opening `FRONTEND/index.html` in your web browser (VS Code Live Server is recommended).

## Key API Endpoints

### Register an Agent
**POST /api/agents**
```json
{
  "name": "Agent Lokesh",
  "status": "AVAILABLE",
  "latitude": 17.3361,
  "longitude": 78.5741
}
```

### Get Nearby Available Agents
**GET /api/agents/nearby?lat=17.3616&lng=78.4747&radius=5000**
Retrieves nearby available agents using a spatial query.

### Create a Ride Request
**POST /api/rides**
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "pickupLat": 17.3275,
  "pickupLng": 78.6068,
  "dropLat": 17.3616,
  "dropLng": 78.4747
}
```

## Team Contributions

This project was built over a 16-Day Sprint for CS2016 Object Oriented Design Programming. 

- Abitesh Pabbulate (124cs0104): Frontend development, REST API controllers and services, WebSocket integration, UI design implementation
- S.S.D.B Gangadgar (124cs0100): Backend architecture setup, Leaflet.js map integration, Nominatim reverse geocoding, GitHub repository management
- Korra Lokesh (124cs0129): Database schema design, PostgreSQL and PostGIS configuration, native spatial SQL queries, backend DTOs and models