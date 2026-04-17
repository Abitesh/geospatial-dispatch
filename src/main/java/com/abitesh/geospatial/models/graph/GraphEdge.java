package com.abitesh.geospatial.models.graph;

public class GraphEdge {
    private GraphNode target;
    private double weight; // Represents distance or time cost

    public GraphEdge(GraphNode target, double weight) {
        this.target = target;
        this.weight = weight;
    }

    public GraphNode getTarget() { return target; }
    public double getWeight() { return weight; }
}