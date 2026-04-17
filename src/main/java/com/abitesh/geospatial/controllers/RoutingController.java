package com.abitesh.geospatial.controllers;

import com.abitesh.geospatial.models.graph.GraphNode;
import com.abitesh.geospatial.services.RoutingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping
    public List<GraphNode> getRoute(
            @RequestParam double fromLat, @RequestParam double fromLng,
            @RequestParam double toLat, @RequestParam double toLng) {
        
        GraphNode startNode = new GraphNode("start-node", fromLat, fromLng);
        GraphNode endNode = new GraphNode("end-node", toLat, toLng);

        return routingService.calculateRoute(startNode, endNode);
    }
}