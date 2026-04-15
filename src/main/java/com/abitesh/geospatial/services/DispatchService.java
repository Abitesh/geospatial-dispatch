package com.abitesh.geospatial.services;

import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.models.RideOrderEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import com.abitesh.geospatial.repositories.RideOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DispatchService {

    private final RideOrderRepository rideOrderRepository;
    private final AgentRepository agentRepository;

    public DispatchService(RideOrderRepository rideOrderRepository, AgentRepository agentRepository) {
        this.rideOrderRepository = rideOrderRepository;
        this.agentRepository = agentRepository;
    }

    public void assignAgentToRide(UUID rideId) {
        Optional<RideOrderEntity> rideOpt = rideOrderRepository.findById(rideId);
        if (rideOpt.isEmpty()) {
            throw new RuntimeException("Ride order not found with id: " + rideId);
        }
        
        RideOrderEntity ride = rideOpt.get();
        
        if (!"PENDING".equals(ride.getStatus())) {
            throw new RuntimeException("Ride is not pending. Current status: " + ride.getStatus());
        }

        List<AgentEntity> availableAgents = agentRepository.findByStatus("AVAILABLE");
        if (availableAgents.isEmpty()) {
            throw new RuntimeException("No available agents right now.");
        }

        AgentEntity nearestAgent = null;
        double minDistance = Double.MAX_VALUE;

        for (AgentEntity agent : availableAgents) {
            if (agent.getCurrentLat() == null || agent.getCurrentLng() == null) continue;
            
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
        ride.setUpdatedAt(LocalDateTime.now());
        rideOrderRepository.save(ride);

        nearestAgent.setStatus("DISPATCHED");
        agentRepository.save(nearestAgent);
        
        System.out.println("Assigned Agent " + nearestAgent.getId() + " to Ride " + ride.getId());
    }
}