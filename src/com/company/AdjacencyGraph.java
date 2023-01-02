//Indholder af klasserne; AdjanacyGraph, Vertex og Edge  er udleveret af Mads Rosendahl i undervisning.
// Koden findes på Software Developments Moodle side

package com.company;

import java.util.ArrayList;

public class AdjacencyGraph {

    ArrayList<Vertex> Vertices;

    //Konstruktør
    public AdjacencyGraph() {
        Vertices = new ArrayList<Vertex>(); //ArrayListe med Vertices (Havne)
    }

    //Metode til at tilføje vertex (Vi kan tilføje nye havne)
    public void addVertex(Vertex v) {
        Vertices.add(v); //v fra metodens parameter
    }

    public void addEdge(Vertex from, Vertex to, Integer container) {
        if (!(Vertices.contains(from) && Vertices.contains(to))) {
            System.out.println("Vertices missing from graph");
            return;
        }
        Edge newE = new Edge(from, to, container);
    }
}

class Vertex {
    String name;
    ArrayList<Edge> OutEdge;

    public Vertex(String name) {
        this.name = name;
        OutEdge = new ArrayList<Edge>(); //Udgående edges fra den pågældende Vertex (Havn)
    }

    //Anvendes til at printe navnet til konsollen
    public String toString() {
        return name;
    }
}

class Edge {
    Vertex from;
    Vertex to;
    Integer weight;

    public Edge(Vertex from, Vertex to, Integer weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        from.OutEdge.add(this); // tilføjer til OutEdge i Vertex Klassen. This referer til klassen og derved følgende variabler; this.from, this.to og this.weight
    }
}
