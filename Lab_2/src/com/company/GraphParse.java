package com.company;

import java.util.Arrays;

public class GraphParse {
    public static String[] states;
    public static String[] transVariables;
    public static String[][] transTable;
    public static String finalState;

    //Method for reading the string from the input.txt and assigning values to the main variables
    public static void readStates(String input){
        int a = input.indexOf("Q={");
        int b = input.indexOf("};\nA");

        states = input
                .substring(a+3,b)
                .split(",");

        a = input.indexOf("F={");
        b = input.indexOf("}");
        finalState = input.substring(a+3, b);

        a = input.indexOf("A={");
        b = input.indexOf("};\nsigma");
        transVariables = input
                .substring(a+3,b)
                .split(",");

        transTable = new String[10][transVariables.length];
        Arrays.stream(transTable).forEach(x->Arrays.fill(x, ""));

        a = input.indexOf(".");
        String[] elements = input.substring(b+3,a).split(";");

        for (String el:elements){
            a = el.indexOf("q");
            String state1 = el.substring(a, a+2);
            String transVar = el.substring(a+3, a+4);
            a = el.indexOf("=");
            String state2 = el.substring(a+1, a+3);

            int state = Arrays.asList(states).indexOf(state1);
            int trans = Arrays.asList(transVariables).indexOf(transVar);

            if(transTable[state][trans].equals("")){
                transTable[state][trans] = transTable[state][trans].concat(state2);
            }else{
                transTable[state][trans] = transTable[state][trans].concat(" " + state2);
            }
        }
    }
}
