package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    static private List<String> Vn;
    static public List<Vertex> vertices = new ArrayList<>();

    //method for reading the nonterminal symbols
    static public void setVn(String vn) {
        int a = vn.indexOf("Vn={");
        int b = vn.indexOf("},");

        Vn = Arrays
                .asList(vn.substring(a+4, b)
                        .replaceAll("\\s+","")
                        .trim().split(","));

        for (String c:Vn){
            vertices.add(new Vertex(c));
        }

        vertices.add(new Vertex("Eps"));
    }

    //method for generating the graph
    static public void generateGraph(String str){
        int a = str.indexOf("P= {")+4;
        int b = str.lastIndexOf("}");

        List<String> elements = new ArrayList<>();
        str = str.substring(a, b).trim();
        elements = Arrays.asList(str.split("\n"));

        for (String c:elements){
            String[] el = c.replaceAll("\\s+","").split("->");

            Vertex node = vertices
                    .stream()
                    .filter(f->el[0].equals(f.getName()))
                    .collect(Collectors.toList()).get(0);

            String destName;
            String w = null;

            if(!el[1].matches(".*[A-Z].*")){
                destName = "Eps";
                w = el[1];
            }else {
                destName = el[1].replaceAll("[^A-Z]", "");
                w = el[1].replaceAll(destName, "");
            }

            Vertex dest = vertices
                    .stream().filter(f->destName.equals(f.getName()))
                    .collect(Collectors.toList()).get(0);

            node.addEdge(w.toCharArray()[0], dest);
        }
    }
}
