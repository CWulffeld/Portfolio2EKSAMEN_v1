package com.company;
import java.util.ArrayList;
import java.util.Arrays;

public class Flow {
//
    AdjacencyGraph adjDirectedG = createDirectedAdj(); //Kalder metoden createDirectedAdj, som returnere newG fra createDirectedAdj(). Denne metode har typen AdjacencyGraph (Klassen)
    //Almindelig arrays (4 nedenstående)
    int[] saldo = new int[adjDirectedG.Vertices.size()];
    String[] names = new String[adjDirectedG.Vertices.size()];
    int[] sPlus = new int[adjDirectedG.Vertices.size()]; //det kunne have været en fordel at lave ArrayLists
    int[] sMinus = new int[adjDirectedG.Vertices.size()];
    int flytCost = 0;

    public void PrintGraph() { //Nested for-loop
        //Første for-loop lineær tid - da der hele tiden vil blive lagt til, men grundet vores foreach bliver det quadratic time O(N^2) da det er en løkke inden i en løkke.
        for (int i = 0; i < adjDirectedG.Vertices.size(); i++) { //Fra AdjancencyGraph klassen, hvor vi har alle navnene. Arraylisten skal køres så længe der ekistserer havne i vertices
            System.out.println(" Destination " + adjDirectedG.Vertices.get(i).name + " is shipping container amount to: "); //Antal containers der bliver sendt fra en given haven
            Vertex current = adjDirectedG.Vertices.get(i); //Vertex: Er current
            for (Edge e : current.OutEdge) { //For hver current havn, løbes hvert outedge (udgående edges) igennem
                System.out.println(e.to.name + " with container amount: " + e.weight); //"til havnen" printes ud, med det givende antal containere.
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

        //tilføjer Vertex (ovenstående objekter) til arrayliste
        newG.addVertex(Jaw); //Tilføjer ovenstående objekter (Havne) til newG arraylisten
        newG.addVertex(Tan);
        newG.addVertex(Dar);
        newG.addVertex(Mom);
        newG.addVertex(Jeb);
        newG.addVertex(Zan);
        newG.addVertex(Sal);

        //Fra en destination til en anden -> hvor mange containers der bliver flyttet. I Vertex klasse er der en OutEdgeArrayListe
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
        System.out.println("Saldo inden for-løkke: " + Arrays.toString(saldo));

        for (Vertex v : adjDirectedG.Vertices) { //For hver vertex løbes edges igennem (Her finder den havn)
            for (Edge e : v.OutEdge) { //Her løbes edges igennem for den specifikke vertex i ovensåtende foeach loop (Her finder den edges tilknyttet havn) Det ligges i variable nedenstående:
                Vertex f = e.from; //Fra havenen
                Vertex t = e.to; //Til havnen
                int w = e.weight; //Antal containere for den specifikke edge

                //Indeks af fra havn (f), tildeles variablen idxf
                int idxf = adjDirectedG.Vertices.indexOf(f); //default foreach løkke, arrayliste metode
                //System.out.println("IDXF: " + idxf);
                //Indeks af til havn (t) tildeles variablen idxt
                int idxt = adjDirectedG.Vertices.indexOf(t);
                //System.out.println("IDXT: " + idxt);

                //I saldo arrayet, minusses/fjernes den pågældende weight (containers) fra vertex f (from)
                //Hvorefter samme weight ligges til fra vertex t.
                //LÆngden af saldo er det samme som Vertices -> Dette betyder at indeks for Vertices er identisk med saldos indeks. På den måde holder vi styr på havnen
                saldo[idxf] -= w; //Havnen/vertex hvor weight skal minusses/fjernes
                //System.out.println("Kald lige efter Fra: " + Arrays.toString(saldo));
                saldo[idxt] += w; //Havnen/vertex hvor weigth skal plusses
                //System.out.println("Kald lige efter Til: " + Arrays.toString(saldo));
                names[idxf] = v.toString();

            }
        }
        System.out.println();

        for (int i = 0; i < saldo.length; i++) { //Denne for-løkke printer navnet på havnene og deres saldo (antal containere)
            System.out.println("port: " + names[i] + " surplus: " + saldo[i]);
        }
        System.out.println();
    }

    public void plusMinusArray() { //to Arrays - måske struktureret anderledes med en liste og lavet f.eks. bubble sort.
        for (int i = 0; i < saldo.length; i++) { //Lineær søgning
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
        int imin = 0, iplus = 0; //imin + iplus er counters
        boolean run = true; //default true
        while (run) { //så længe run er true så skal den køre
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
            //-sMinus mindre end eller lig sPlus
            if (-sMinus[imin] <= sPlus[iplus]) { //Tjekker hvor mange der skal flyttes. Finder det laveste tal (ved at begge værdier i sPlus og sMinus er positivt tal) Derfor der er minus fortegn ved sMinus
                flyt = -sMinus[imin]; //Antal contianers der flyttes, det laveste tal. Dette ved vi fra ovenstående statement. Ligges i variablen flyt
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
