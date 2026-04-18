package com.abitesh.geospatial.controllers;

import com.abitesh.geospatial.dto.AgentCreateRequest;
import com.abitesh.geospatial.dto.AgentResponse;
import com.abitesh.geospatial.models.AgentEntity; // <-- FIXED: Using your actual model
import com.abitesh.geospatial.repositories.AgentRepository;
import com.abitesh.geospatial.services.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;
    private final AgentRepository agentRepository;

    @Autowired
    public AgentController(AgentService agentService, AgentRepository agentRepository) {
        this.agentService = agentService;
        this.agentRepository = agentRepository;
    }

    @PostMapping
    public AgentResponse registerAgent(@RequestBody AgentCreateRequest request) {
        return agentService.registerAgent(request);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<AgentEntity>> getNearbyAgents(@RequestParam double lat, @RequestParam double lng, @RequestParam double radius) {
        List<AgentEntity> nearbyAgents = agentRepository.findNearbyAvailableAgents(lat, lng, radius);
        return ResponseEntity.ok(nearbyAgents);
    }
}