package com.abitesh.geospatial.services.routing;

import com.abitesh.geospatial.models.graph.GraphEdge;
import com.abitesh.geospatial.models.graph.GraphNode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DijkstraRoutingStrategy implements RoutingStrategy {

    @Override
    public List<GraphNode> findPath(GraphNode from, GraphNode to) {
        Map<GraphNode, Double> distances = new HashMap<>();
        Map<GraphNode, GraphNode> previousNodes = new HashMap<>();
        PriorityQueue<GraphNode> queue = new PriorityQueue<>(Comparator.comparing(distances::get));

        distances.put(from, 0.0);
        queue.add(from);

        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();

            if (current.equals(to)) {
                break; 
            }

            for (GraphEdge edge : current.getEdges()) {
                GraphNode neighbor = edge.getTarget();
                double newDist = distances.get(current) + edge.getWeight();

                if (newDist < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, current);
                    
                    queue.remove(neighbor); 
                    queue.add(neighbor);
                }
            }
        }
        List<GraphNode> path = new ArrayList<>();
        for (GraphNode node = to; node != null; node = previousNodes.get(node)) {
            path.add(node);
        }
        Collections.reverse(path);

        if (path.isEmpty() || !path.get(0).equals(from)) {
            return Collections.emptyList(); // No path exists
        }

        return path;
    }
}