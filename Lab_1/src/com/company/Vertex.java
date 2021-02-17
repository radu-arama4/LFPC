package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private String name;
    private List<Edge> edges;

    public Vertex(String name) {
        this.name = name;
        edges = new ArrayList<>();
    }

    public Vertex hasEdge(char weight){
        for (Edge ed:edges){
            if (ed.getWeight()==weight) return ed.getDest();
        }
        return null;
    }

    public void addEdge(char weight, Vertex vert){
        edges.add(new Edge(weight, vert));
    }

    public String getName() {
        return name;
    }
}
