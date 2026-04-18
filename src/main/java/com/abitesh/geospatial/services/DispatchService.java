package com.abitesh.geospatial.services;

import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.models.RideOrderEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import com.abitesh.geospatial.repositories.RideOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class DispatchService {

    @Autowired
    private RideOrderRepository rideOrderRepository;

    @Autowired
    private AgentRepository agentRepository;

    public void assignAgentToRide(UUID rideId) {
        RideOrderEntity ride = rideOrderRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        List<AgentEntity> availableAgents = agentRepository.findByStatus("AVAILABLE");

        AgentEntity nearestAgent = null;
        double minDistance = Double.MAX_VALUE;

        for (AgentEntity agent : availableAgents) {
            double dx = agent.getCurrentLat() - ride.getPickupLat();
            double dy = agent.getCurrentLng() - ride.getPickupLng();
            double distance = Math.sqrt((dx * dx) + (dy * dy));

            if (distance < minDistance) {
                minDistance = distance;
                nearestAgent = agent;
            }
        }

        if (nearestAgent == null) {
            throw new RuntimeException("Could not calculate distance for available agents.");
        }

        ride.setAgentId(nearestAgent.getId());
        ride.setStatus("ACTIVE");
        
        // FIXED: Using java.time.Instant.now() to match the Entity
        ride.setUpdatedAt(Instant.now()); 
        
        rideOrderRepository.save(ride);

        nearestAgent.setStatus("DISPATCHED");
        agentRepository.save(nearestAgent);

        System.out.println("Assigned Agent " + nearestAgent.getId() + " to Ride " + ride.getId());
    }
}