package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        //File for parsing the grammar
        String filePath = new File("src/com/company/input.txt").getAbsolutePath();

        Graph.setVn(readLineByLine( filePath ));
        Graph.generateGraph(readLineByLine( filePath ));

        System.out.println("Introduce the word you want to check: ");

        //Scanning the word we want to check
        Scanner sc = new Scanner(System.in);
        String word;
        word = sc.nextLine();

        //Initial state
        Vertex currentState = Graph.vertices.get(0);

        for (int i=0;i<word.length();i++){
            char ch = word.charAt(i);

            if(currentState.hasEdge(ch)!=null){
                currentState = currentState.hasEdge(ch);
            }else {
                break;
            }
            //Eps
            if(currentState==Graph.vertices.get(Graph.vertices.size()-1)){
                if (i!=word.length()-1){
                    break;
                }
                System.out.println("Accepted!");
                return;
            }
        }
        System.out.println("Rejected!");
    }

    // Static method for reading the content of the file
    private static String readLineByLine(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
