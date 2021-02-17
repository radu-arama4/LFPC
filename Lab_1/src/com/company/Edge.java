package com.company;

public class Edge {
    char weight;
    private Vertex dest;

    public Edge(char weight, Vertex dest) {
        this.weight = weight;
        this.dest = dest;
    }

    public char getWeight() {
        return weight;
    }

    public Vertex getDest() {
        return dest;
    }
}
