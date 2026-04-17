package com.abitesh.geospatial.websockets;

import com.abitesh.geospatial.services.AgentService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class LocationHandler {

    private final AgentService agentService;

    public LocationHandler(AgentService agentService) {
        this.agentService = agentService;
    }

    @MessageMapping("/agent/location")
    public void receiveLocationUpdate(@Payload LocationUpdatePayload payload) {
        agentService.updateLocation(
                payload.getAgentId(),
                payload.getLatitude(),
                payload.getLongitude()
        );
    }

    public static class LocationUpdatePayload {
        private UUID agentId;
        private Double latitude;
        private Double longitude;

        public UUID getAgentId() {
            return agentId;
        }

        public void setAgentId(UUID agentId) {
            this.agentId = agentId;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }
}