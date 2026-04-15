package com.abitesh.geospatial.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "agents")
public class AgentEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String status; // e.g., OFFLINE, AVAILABLE, DISPATCHED
    private Double currentLat;
    private Double currentLng;

    public AgentEntity() {}

    public AgentEntity(String name, String status, Double currentLat, Double currentLng) {
        this.name = name;
        this.status = status;
        this.currentLat = currentLat;
        this.currentLng = currentLng;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Status: OFFLINE, AVAILABLE, DISPATCHED
    private String status; 
    
    // Location Coordinates
    private double currentLat;
    private double currentLng;
    
    // Time tracking
    private LocalDateTime lastHeartbeatAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getCurrentLat() { return currentLat; }
    public void setCurrentLat(Double currentLat) { this.currentLat = currentLat; }

    public Double getCurrentLng() { return currentLng; }
    public void setCurrentLng(Double currentLng) { this.currentLng = currentLng; }
    public double getCurrentLat() { return currentLat; }
    public void setCurrentLat(double currentLat) { this.currentLat = currentLat; }

    public double getCurrentLng() { return currentLng; }
    public void setCurrentLng(double currentLng) { this.currentLng = currentLng; }

    public LocalDateTime getLastHeartbeatAt() { return lastHeartbeatAt; }
    public void setLastHeartbeatAt(LocalDateTime lastHeartbeatAt) { this.lastHeartbeatAt = lastHeartbeatAt; }
}