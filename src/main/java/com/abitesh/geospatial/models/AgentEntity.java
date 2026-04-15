package com.abitesh.geospatial.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

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

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getCurrentLat() { return currentLat; }
    public void setCurrentLat(Double currentLat) { this.currentLat = currentLat; }

    public Double getCurrentLng() { return currentLng; }
    public void setCurrentLng(Double currentLng) { this.currentLng = currentLng; }
}