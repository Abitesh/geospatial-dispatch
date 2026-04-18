package com.abitesh.geospatial.services;

import com.abitesh.geospatial.dto.RideCreateRequest;
import com.abitesh.geospatial.dto.RideResponse;
import com.abitesh.geospatial.models.RideOrderEntity;
import com.abitesh.geospatial.repositories.RideOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RequestService {

    @Autowired
    private RideOrderRepository rideOrderRepository;

    @Autowired
    private DispatchService dispatchService;

    public RideResponse createRequest(RideCreateRequest request) {
        // 1. Create and Save the Ride
        RideOrderEntity ride = new RideOrderEntity();
        ride.setUserId(request.getUserId());
        ride.setPickupLat(request.getPickupLat());
        ride.setPickupLng(request.getPickupLng());
        ride.setDropLat(request.getDropLat());
        ride.setDropLng(request.getDropLng());
        ride.setStatus("PENDING");

        RideOrderEntity savedRide = rideOrderRepository.save(ride);

        // 2. Assign Agent
        dispatchService.assignAgentToRide(savedRide.getId());

        // 3. Refresh to get assigned agent info
        savedRide = rideOrderRepository.findById(savedRide.getId()).orElse(savedRide);

        // 4. Map to Response
        RideResponse response = mapToResponse(savedRide);

        // 5. BRANCH-SAFE ROUTING (Straight Line)
        // Since the Graph branch is not merged, we create a 2-point path
        List<double[]> pathPoints = new ArrayList<>();
        pathPoints.add(new double[]{request.getPickupLat(), request.getPickupLng()});
        pathPoints.add(new double[]{request.getDropLat(), request.getDropLng()});
        
        response.setPath(pathPoints);

        return response;
    }

    public RideResponse getRequest(UUID id) {
        RideOrderEntity ride = rideOrderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ride not found"));
        return mapToResponse(ride);
    }

    private RideResponse mapToResponse(RideOrderEntity ride) {
        RideResponse response = new RideResponse();
        response.setId(ride.getId());
        response.setUserId(ride.getUserId());
        response.setAgentId(ride.getAgentId());
        response.setStatus(ride.getStatus());
        response.setPickupLat(ride.getPickupLat());
        response.setPickupLng(ride.getPickupLng());
        response.setDropLat(ride.getDropLat());
        response.setDropLng(ride.getDropLng());
        return response;
    }
}