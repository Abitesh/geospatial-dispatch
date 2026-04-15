package com.abitesh.geospatial.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideResponse {
    private UUID orderId;
    private String userId;
    private String agentId;
    private String status;
    private LocalDateTime createdAt;

    // Getters and Setters
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}