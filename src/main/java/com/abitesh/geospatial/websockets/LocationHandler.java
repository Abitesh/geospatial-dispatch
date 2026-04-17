package com.abitesh.geospatial.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class LocationHandler {

    /**
     * Placeholder for agent location updates.
     * An agent sends a message to "/app/agent/location".
     * The broker broadcasts the return value to all subscribers listening on "/topic/locations".
     */
    @MessageMapping("/agent/location")
    @SendTo("/topic/locations")
    public LocationUpdatePayload broadcastLocation(@Payload LocationUpdatePayload payload) {
        System.out.println("Received location update from Agent " + payload.getAgentId() + 
                           ": [" + payload.getLat() + ", " + payload.getLng() + "]");
        
        return payload; 
    }

    public static class LocationUpdatePayload {
        private UUID agentId;
        private Double lat;
        private Double lng;

        public UUID getAgentId() { return agentId; }
        public void setAgentId(UUID agentId) { this.agentId = agentId; }
        public Double getLat() { return lat; }
        public void setLat(Double lat) { this.lat = lat; }
        public Double getLng() { return lng; }
        public void setLng(Double lng) { this.lng = lng; }
    }
}