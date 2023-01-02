package com.company;
import java.util.ArrayList;

public class Flow {

    AdjacencyGraph adjDirectedG = createDirectedAdj(); //Kalder metoden createDirectedAdj, som returnere newG fra createDirectedAdj(). Denne metode har typen AdjacencyGraph (Klassen)
    //Almindelig arrays (4 nedenstående)
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
        AdjacencyGraph newG = new AdjacencyGraph(); //Kalder AdjacencyGraph klassens konstruktør som laver ny arrayliste (Vertices) som tager Vertex
        //Laver en ny Vertex med navn (Havn) - Havne tilføjes som objekter.
        Vertex Jaw = new Vertex("Jaw"); //Nye objekter af Vertex klassen
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
        //2 foreach løkker + foreach løkke i indefOf
        for (Vertex v : adjDirectedG.Vertices) { //For hver vertex løbes edges igennem (Her finder den havn)
            for (Edge e : v.OutEdge) { //Her løbes edges igennem (Her finder den edges tilknyttet havn) Det ligges i variable nedenstående:
                Vertex f = e.from; //Fra havenen
                Vertex t = e.to; //Til havnen
                int w = e.weight; //Antal containere for den specifikke edge

                //Indeks af fra havn (f), tildeles variablen idxf
                int idxf = adjDirectedG.Vertices.indexOf(f); //default foreach løkke, arrayliste metode
                //Indeks af til havn (t) tildeles variablen idxt
                int idxt = adjDirectedG.Vertices.indexOf(t);

                //I saldo arrayet, minusses/fjernes den pågældende weight (containers) fra vertex f (from)
                //Hvorefter samme weight ligges til fra vertex t.
                //LÆngden af saldo er det samme som Vertices -> Dette betyder at indeks for Vertices er identisk med saldos indeks. På den måde holder vi styr på havnen
                saldo[idxf] -= w; //Havnen/vertex hvor weight skal minusses/fjernes
                saldo[idxt] += w; //Havnen/vertex hvor weigth skal plusses
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
                imin++; //Counter der flytter det indeks, som nedenstående tilgår (sMinus)
            }
            if (sPlus[iplus] == 0) {
                iplus++; //Counter der flytter det indeks som nedenstående tilgår (sPlus)
            }
            if (imin >= saldo.length) {
                run = false;
            }
            if (iplus >= saldo.length) {
                run = false;

                continue;
            }

            //Flytter containerne
            if (-sMinus[imin] <= sPlus[iplus]) { //Tjekker hvor mange der skal flyttes. Finder det laveste tal (ved at begge værdier i sPlus og sMinus er positivt tal) Derfor der er minus fortegn ved sMinus
                flyt = -sMinus[imin]; //Antal contianers der flyttes, ligge si variablen flyt
                flytCost += flyt; //Antal containere der flyttes ligges i flytCost variabel
                System.out.println("Flyt: " + flyt + " fra " + names[iplus] + " til " + names[imin]);
                sPlus[iplus] -= flyt; //de flyttede containere minusses fra sPlus array
                sMinus[imin] += flyt; //de flyttede containere plusses på sMinus
            } else if (sPlus[iplus] < -sMinus[imin]) { //Hvis værdien på indeks iPlus i sPlus array er mindre end -sMinus[imin]
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
