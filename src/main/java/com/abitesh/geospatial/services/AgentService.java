package com.abitesh.geospatial.services;

import com.abitesh.geospatial.dto.AgentCreateRequest;
import com.abitesh.geospatial.dto.AgentResponse;
import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public AgentResponse registerAgent(AgentCreateRequest request) {
        AgentEntity entity = new AgentEntity();
        entity.setName(request.getName());
        entity.setStatus(request.getStatus() != null ? request.getStatus() : "OFFLINE");
        entity.setCurrentLat(request.getLatitude());
        entity.setCurrentLng(request.getLongitude());
        entity.setLastHeartbeatAt(LocalDateTime.now());

        AgentEntity savedEntity = agentRepository.save(entity);
        return mapToResponse(savedEntity);
    }

    public List<AgentResponse> getAllAgents() {
        return agentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AgentResponse updateLocation(UUID agentId, Double lat, Double lng) {
        Optional<AgentEntity> agentOpt = agentRepository.findById(agentId);
        
        if (agentOpt.isPresent()) {
            AgentEntity agent = agentOpt.get();
            agent.setCurrentLat(lat);
            agent.setCurrentLng(lng);
            agent.setLastHeartbeatAt(LocalDateTime.now());
            AgentEntity updated = agentRepository.save(agent);
            return mapToResponse(updated);
        }
        throw new RuntimeException("Agent not found with id: " + agentId);
    }

    private AgentResponse mapToResponse(AgentEntity entity) {
        AgentResponse response = new AgentResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setStatus(entity.getStatus());
        response.setLatitude(entity.getCurrentLat());
        response.setLongitude(entity.getCurrentLng());
        return response;
    }
}