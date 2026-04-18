package com.abitesh.geospatial.services;

import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.models.RideOrderEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import com.abitesh.geospatial.repositories.RideOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {

    @Autowired
    private RideOrderRepository rideOrderRepository;
    
    @Autowired
    private AgentRepository agentRepository;

    // Make sure your method parameters match your actual DTO for creating rides
    @Transactional
    public RideOrderEntity createRide(UUID userId, double pickupLat, double pickupLng, double dropLat, double dropLng) {
        
        RideOrderEntity ride = new RideOrderEntity();
        ride.setUserId(userId);
        ride.setPickupLat(pickupLat);
        ride.setPickupLng(pickupLng);
        ride.setDropLat(dropLat);
        ride.setDropLng(dropLng);

        // Find the closest agent using the fixed repository method
        Optional<AgentEntity> closestAgentOpt = agentRepository.findClosestAvailableAgent(pickupLat, pickupLng);
        
        if (closestAgentOpt.isPresent()) {
            AgentEntity agent = closestAgentOpt.get();
            ride.setAgentId(agent.getId());
            ride.setStatus("ACTIVE");
            
            // Mark agent as busy/dispatched so they don't get assigned to another ride
            agent.setStatus("DISPATCHED");
            agentRepository.save(agent);
        } else {
            ride.setStatus("PENDING"); // No agents available nearby
        }

        return rideOrderRepository.save(ride);
    }
}