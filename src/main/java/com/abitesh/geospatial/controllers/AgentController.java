package com.abitesh.geospatial.controllers;

import com.abitesh.geospatial.dto.AgentCreateRequest;
import com.abitesh.geospatial.dto.AgentResponse;
import com.abitesh.geospatial.services.AgentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    // Injecting the service via constructor
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    public AgentResponse registerAgent(@RequestBody AgentCreateRequest request) {
        return agentService.registerAgent(request);
    }

    @GetMapping
    public List<AgentResponse> listAgents() {
        return agentService.getAllAgents();
    }
}