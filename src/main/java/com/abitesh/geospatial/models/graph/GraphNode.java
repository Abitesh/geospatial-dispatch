package com.abitesh.geospatial.models.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphNode {
    private String id;
    private double lat;
    private double lng;
    private List<GraphEdge> edges;

    public GraphNode(String id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.edges = new ArrayList<>();
    }

    public void addEdge(GraphEdge edge) { this.edges.add(edge); }
    public List<GraphEdge> getEdges() { return edges; }
    public String getId() { return id; }
    
    public double getLat() { return lat; }
    public double getLng() { return lng; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return Objects.equals(id, graphNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}