package com.company;

import java.util.ArrayList;

public class Flow {

    AdjacencyGraph adjDirectedG = createDirectedAdj();
    int[] saldo = new int[adjDirectedG.Vertices.size()];
    String[] names = new String[adjDirectedG.Vertices.size()];
    int[] sPlus = new int[adjDirectedG.Vertices.size()];
    int[] sMinus = new int[adjDirectedG.Vertices.size()];
    int flytCost = 0;

    public void PrintGraph() {
        for (int i = 0; i < adjDirectedG.Vertices.size(); i++) {
            System.out.println(" Destination " + adjDirectedG.Vertices.get(i).name + " is shipping container amount to: ");
            Vertex current = adjDirectedG.Vertices.get(i);
            for (Edge e : current.OutEdge) {
                System.out.println(e.to.name + " with container amount: " + e.weight);
            }
        }
    }

    public AdjacencyGraph createDirectedAdj() {
        AdjacencyGraph newG = new AdjacencyGraph();

        //Laver en ny Vertex med navn
        Vertex Jaw = new Vertex("Jaw");
        Vertex Tan = new Vertex("Tan");
        Vertex Dar = new Vertex("Dar");
        Vertex Mom = new Vertex("Mom");
        Vertex Jeb = new Vertex("Jeb");
        Vertex Zan = new Vertex("Zan");
        Vertex Sal = new Vertex("Sal");

        //tilføjer Vertex til arrayliste
        newG.addVertex(Jaw);
        newG.addVertex(Tan);
        newG.addVertex(Dar);
        newG.addVertex(Mom);
        newG.addVertex(Jeb);
        newG.addVertex(Zan);
        newG.addVertex(Sal);

        //Fra en destination til en anden -> hvor mange containers der bliver flyttet
        //Fra havn Jaw
        newG.addEdge(Jaw, Dar, 2000);
        newG.addEdge(Jaw, Mom, 2000);
        // newG.addEdge(Jaw, Jaw , 0);

        //Fra havn Tan
        newG.addEdge(Tan, Mom, 5000);
        newG.addEdge(Tan, Jeb, 7000);
        newG.addEdge(Tan, Dar, 3000);
        newG.addEdge(Tan, Zan, 2000);
        newG.addEdge(Tan, Sal, 7000);
        //  newG.addEdge(Tan,Tan,0);

        //Fra havn Dar
        newG.addEdge(Dar, Jaw, 3000);
        newG.addEdge(Dar, Jeb, 2000);
        newG.addEdge(Dar, Tan, 5000);
        // newG.addEdge(Dar,Dar,0);

        //Fra havn Mom
        newG.addEdge(Mom, Jeb, 500);
        newG.addEdge(Mom, Sal, 2000);

        newG.addEdge(Jeb, Jeb, 0);
        newG.addEdge(Zan, Zan, 0);
        newG.addEdge(Sal, Sal, 0);

        return newG;

    }

    public void runVertexAndEdges() {
        for (Vertex v : adjDirectedG.Vertices) {
            for (Edge e : v.OutEdge) {
                Vertex f = e.from;
                Vertex t = e.to;
                int w = e.weight;

                int idxf = adjDirectedG.Vertices.indexOf(f);
                int idxt = adjDirectedG.Vertices.indexOf(t);
                saldo[idxf] -= w;
                saldo[idxt] += w;
                names[idxf] = v.toString();

            }
        }
        System.out.println();

        for (int i = 0; i < saldo.length; i++) {
            System.out.println("port: " + names[i] + " surplus: " + saldo[i]);
        }
        System.out.println();
    }

    public void plusMinusArray() {
        for (int i = 0; i < saldo.length; i++) {
            if (saldo[i] > 0) {
                sPlus[i] = saldo[i];
            }
            if (saldo[i] < 0) {
                sMinus[i] = saldo[i];
            }
        }
    }

    public void flytContainers() {
        int flyt;

        int imin = 0, iplus = 0; //counter
        boolean run = true;
        while (run) {
            if (sMinus[imin] == 0) {
                imin++;
            }
            if (sPlus[iplus] == 0) {
                iplus++;
            }
            if (imin >= saldo.length) {
                run = false;
            }
            if (iplus >= saldo.length) {
                run = false;
                continue;
            }


            //Flytter containerne
            if (-sMinus[imin] <= sPlus[iplus]) { //Tjekker hvor mange der skal flyttes. Finder det laveste tal (ved at begge værdier i sPlus og sMinus
                flyt = -sMinus[imin];
                flytCost += flyt;
                System.out.println("Flyt: " + flyt + " fra " + names[iplus] + " til " + names[imin]);
                sPlus[iplus] -= flyt;
                sMinus[imin] += flyt;
            } else if (sPlus[iplus] < -sMinus[imin]) {
                flyt = sPlus[iplus];
                flytCost += flyt;
                System.out.println("Flyt: " + flyt + " fra " + names[iplus] + " til " + names[imin]);
                sPlus[iplus] -= flyt;
                sMinus[imin] += flyt;
            } else {
                run = false;
            }
        }
        printFlytCostPris();
    }

    public void printFlytCostPris() {
        System.out.println("TOTAL: " + flytCost * 100 + " Dollars");
    }


}
