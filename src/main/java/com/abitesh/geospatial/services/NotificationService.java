package com.abitesh.geospatial.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastAgentLocation(UUID rideId, UUID agentId, Double lat, Double lng) {
        LocationUpdateMessage message = new LocationUpdateMessage();
        message.setRideId(rideId);
        message.setAgentId(agentId);
        message.setLatitude(lat);
        message.setLongitude(lng);

        messagingTemplate.convertAndSend("/topic/rides/" + rideId, message);
    }

    public static class LocationUpdateMessage {
        private UUID rideId;
        private UUID agentId;
        private Double latitude;
        private Double longitude;

        public UUID getRideId() {
            return rideId;
        }

        public void setRideId(UUID rideId) {
            this.rideId = rideId;
        }

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