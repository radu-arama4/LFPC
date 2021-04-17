package com.company;

import javax.swing.*;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static com.company.GraphParse.*;

public class Main {
    static String[][] new_table;
    static List<String> newElements;

    public static void main(String[] args) {
        //Reading the input
        String filePath = new File("src/com/company/input.txt").getAbsolutePath();
        String content = readLineByLine(filePath);

        //The parsing of the input
        readStates(content);
        newElements = new ArrayList<>();

        //Initialization of new table
        new_table = new String[10][transVariables.length];

        String start = null;
        int flag = 0;
        int start_index = 0;

        for(int i=0;i< states.length;i++){
            for (int z=0;z< transVariables.length;z++){
                if (!Arrays.asList(states).contains(transTable[i][z])){
                    newElements.add(transTable[i][z]);
                    if(flag==0){
                        start = states[i];
                        start_index = i;
                        flag=1;
                    }
                }
            }
        }

        int size = newElements.size();

        // Iterating through the new states and determining the product for each one
        // If in the product[] is found a new state, it is added in the newElements
        for(int i=0;i<size;i++){
            String[] product = findStates(newElements.get(i), states, transVariables);
            for(String pr:product){
                if (isNew(pr)){
                    newElements.add(pr);
                    size++;
                }
            }
            // The results from product[] are added to the new transition table
            System.arraycopy(product, 0, new_table[i], 0, transVariables.length);
        }

        //Setting the final states
        for (String el:newElements){
            if(el.contains(finalState)){
                newElements.set(newElements.indexOf(el), "*".concat(el));
            }
        }
        states[0] = "->".concat(states[0]);

        //Creating the transition table
        new JTableEx(new_table, newElements, states, transVariables, transTable);
    }

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

    // Method for checking if a given state is new or not.
    public static boolean isNew(String element){
        return !Arrays.asList(states).contains(element) && !newElements.contains(element);
    }

    //Method for computing the products of a given state
    //It consists mostly of string processing
    public static String[] findStates(String state, String[] states, String[] trans){
        String[] units = state.split("\\s+");
        String[] eachTrans = new String[trans.length];
        Arrays.fill(eachTrans, "");

        if (units.length==1){
            String[] res = new String[transVariables.length];
            Arrays.fill(res, "");
            return res;
        }

        for (String unit : units) {
            int x = Arrays.asList(states).indexOf(unit);
            for (int i=0;i< eachTrans.length;i++){
                eachTrans[i] += transTable[x][i] + " ";
            }
        }

        for (int i=0;i< eachTrans.length;i++){
            String cuv = eachTrans[i];
            if(cuv.trim().length()==2){
                eachTrans[i] = eachTrans[i].trim();
            }
            String[] divided = eachTrans[i].split("\\s+");
            divided = Arrays.stream(divided)
                    .distinct()
                    .toArray(String[]::new);

            eachTrans[i] = String.join(" ", divided);
        }

        return eachTrans;
    }

}