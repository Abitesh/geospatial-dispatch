package com.abitesh.geospatial.dto;

public class AgentResponse {
    private Long id;
    private String status;
    private double currentLat;
    private double currentLng;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getCurrentLat() { return currentLat; }
    public void setCurrentLat(double currentLat) { this.currentLat = currentLat; }
    public double getCurrentLng() { return currentLng; }
    public void setCurrentLng(double currentLng) { this.currentLng = currentLng; }
}