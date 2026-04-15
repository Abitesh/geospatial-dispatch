package com.abitesh.geospatial.services;

import com.abitesh.geospatial.dto.RideCreateRequest;
import com.abitesh.geospatial.dto.RideResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestService {

    // Temporary in-memory map to store rides until a Database Entity is created
    private final Map<UUID, RideResponse> rideDatabase = new ConcurrentHashMap<>();

    public RideResponse createRequest(RideCreateRequest request) {
        RideResponse response = new RideResponse();
        response.setOrderId(UUID.randomUUID());
        response.setUserId(request.getUserId());
        response.setStatus("PENDING"); // Every new ride starts as pending
        response.setCreatedAt(LocalDateTime.now());

        // Save to our temporary database
        rideDatabase.put(response.getOrderId(), response);
        
        return response;
    }

    public RideResponse getRequest(UUID id) {
        return rideDatabase.get(id); // Retrieves the ride by its ID
    }
}