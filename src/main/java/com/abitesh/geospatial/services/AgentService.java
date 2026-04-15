package com.abitesh.geospatial.services;

import com.abitesh.geospatial.dto.AgentCreateRequest;
import com.abitesh.geospatial.dto.AgentResponse;
import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public AgentResponse registerAgent(AgentCreateRequest request) {
        AgentEntity agent = new AgentEntity();
        agent.setStatus(request.getStatus() != null ? request.getStatus() : "OFFLINE");
        agent.setLastHeartbeatAt(LocalDateTime.now());
        
        AgentEntity savedAgent = agentRepository.save(agent);
        return mapToResponse(savedAgent);
    }

    public List<AgentResponse> getAllAgents() {
        return agentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AgentResponse mapToResponse(AgentEntity entity) {
        AgentResponse response = new AgentResponse();
        response.setId(entity.getId());
        response.setStatus(entity.getStatus());
        response.setCurrentLat(entity.getCurrentLat());
        response.setCurrentLng(entity.getCurrentLng());
        return response;
    }
}