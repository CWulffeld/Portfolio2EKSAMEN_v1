package com.company;

import java.util.ArrayList;

public class AdjacencyGraph {
    ArrayList<Vertex> Vertices;

    public AdjacencyGraph(){
        Vertices=new ArrayList<Vertex>();

    }

    public void addVertex(Vertex v){

        Vertices.add(v);
    }

    public void addEdge(Vertex from, Vertex to, Integer container){

        if(!(Vertices.contains(from) && Vertices.contains(to)))
        {
            System.out.println("Vertices missing from graph");
            return;
        }
        Edge newE = new Edge(from, to, container);

        /*
        for (int i = 0; i <Vertices.size(); i++) {
             if (from == Vertices.get(i)){
                 System.out.println(Vertices.get(i).name + ": " + container );
             }
        }
*/
    }


 /*   public void addUnDirectedEdge(Vertex from, Vertex to, Integer weight){
        if(!(Vertices.contains(from) && Vertices.contains(to)))
        {
            System.out.println("Vertices missing from graph");
            return;
        }
        Edge newE=new Edge(from, to, weight);
        Edge newE2=new Edge( to, from, weight);
    }*/

    public void PrintGraph(){

        for(int i=0;i<Vertices.size();i++){
            System.out.println(" Destination "+Vertices.get(i).name+" is shipping container amount to: ");
            Vertex current=Vertices.get(i);
            for (Edge e: current.OutEdge) {
                System.out.println(e.to.name +" with container amount: "+e.weight);
            }
        }
    }


}

class Vertex {
    String name;
    ArrayList<Edge> OutEdge;
    public Vertex(String name ){
        this.name=name;
        OutEdge=new ArrayList<Edge>();
    }

    public String toString() {
        return name;
    }


}

class Edge{
    Vertex from;
    Vertex to;
    Integer weight;
    public Edge(Vertex from,Vertex to, Integer weight){
        this.from=from;
        this.to=to;
        this.weight=weight;
        from.OutEdge.add(this);
    }
}
