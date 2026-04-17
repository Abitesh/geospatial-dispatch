package com.abitesh.geospatial.services.routing;

import com.abitesh.geospatial.models.graph.GraphNode;
import java.util.List;

public interface RoutingStrategy {
    List<GraphNode> findPath(GraphNode from, GraphNode to);
}