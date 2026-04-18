package com.abitesh.geospatial.services;

import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.models.RideOrderEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import com.abitesh.geospatial.repositories.RideOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DispatchServiceTest {

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private RideOrderRepository rideOrderRepository;

    @InjectMocks
    private DispatchService dispatchService;

    private RideOrderEntity ride;
    private AgentEntity farAgent;
    private AgentEntity closeAgent;

    @BeforeEach
    void setUp() {
        ride = new RideOrderEntity();
        ride.setId(UUID.randomUUID());
        ride.setPickupLat(0.0);
        ride.setPickupLng(0.0);
        ride.setStatus("PENDING");

        farAgent = new AgentEntity();
        farAgent.setId(UUID.randomUUID());
        farAgent.setStatus("AVAILABLE");
        farAgent.setCurrentLat(10.0);
        farAgent.setCurrentLng(10.0);

        closeAgent = new AgentEntity();
        closeAgent.setId(UUID.randomUUID());
        closeAgent.setStatus("AVAILABLE");
        closeAgent.setCurrentLat(1.0);
        closeAgent.setCurrentLng(1.0);
    }

    @Test
    void testDispatchNearestAgent_Success() {
        // Arrange
        when(rideOrderRepository.findById(ride.getId())).thenReturn(Optional.of(ride));
        // closeAgent is closer to 0,0 than farAgent
        when(agentRepository.findByStatus("AVAILABLE")).thenReturn(Arrays.asList(farAgent, closeAgent));

        // Act
        dispatchService.assignAgentToRide(ride.getId());

        // Assert
        assertEquals("ACTIVE", ride.getStatus());
        assertEquals(closeAgent.getId(), ride.getAgentId());
        assertEquals("DISPATCHED", closeAgent.getStatus());
        
        verify(agentRepository).save(closeAgent);
        verify(rideOrderRepository).save(ride);
    }

    @Test
    void testDispatch_NoAgentsAvailable() {
        // Arrange
        when(rideOrderRepository.findById(ride.getId())).thenReturn(Optional.of(ride));
        when(agentRepository.findByStatus("AVAILABLE")).thenReturn(Collections.emptyList());

        // Act & Assert (Expecting standard RuntimeException as thrown by your service)
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> dispatchService.assignAgentToRide(ride.getId()));
            
        assertEquals("No available agents right now.", exception.getMessage());
    }
}