package com.abitesh.geospatial.services;

import com.abitesh.geospatial.models.graph.GraphNode;
import com.abitesh.geospatial.services.routing.RoutingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutingService {

    private final RoutingStrategy routingStrategy;

    // Spring Boot automatically injects the Dijkstra strategy here
    public RoutingService(RoutingStrategy routingStrategy) {
        this.routingStrategy = routingStrategy;
    }

    public List<GraphNode> calculateRoute(GraphNode from, GraphNode to) {
        return routingStrategy.findPath(from, to);
    }
}