package com.abitesh.geospatial.services;

import com.abitesh.geospatial.dto.AgentCreateRequest;
import com.abitesh.geospatial.dto.AgentResponse;
import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import com.abitesh.geospatial.repositories.RideOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentServiceTest {

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private RideOrderRepository rideOrderRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AgentService agentService;

    private AgentCreateRequest request;
    private AgentEntity savedEntity;

    @BeforeEach
    void setUp() {
        request = new AgentCreateRequest();
        request.setName("Test Agent");
        request.setStatus("AVAILABLE");
        request.setLatitude(10.0);
        request.setLongitude(20.0);

        savedEntity = new AgentEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setName("Test Agent");
        savedEntity.setStatus("AVAILABLE");
        savedEntity.setCurrentLat(10.0);
        savedEntity.setCurrentLng(20.0);
        savedEntity.setLastHeartbeatAt(LocalDateTime.now());
    }

    @Test
    void testRegisterAgent_Success() {
        // Arrange
        when(agentRepository.save(any(AgentEntity.class))).thenReturn(savedEntity);

        // Act
        AgentResponse response = agentService.registerAgent(request);

        // Assert
        assertNotNull(response);
        assertEquals(savedEntity.getId(), response.getId());
        assertEquals("Test Agent", response.getName());
        assertEquals("AVAILABLE", response.getStatus());

        ArgumentCaptor<AgentEntity> captor = ArgumentCaptor.forClass(AgentEntity.class);
        verify(agentRepository).save(captor.capture());
        
        AgentEntity captured = captor.getValue();
        assertEquals("Test Agent", captured.getName());
        assertEquals("AVAILABLE", captured.getStatus());
        assertEquals(10.0, captured.getCurrentLat());
    }
}