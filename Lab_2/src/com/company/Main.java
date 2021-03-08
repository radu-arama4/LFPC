package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static String[][] table;
    static String[][] new_table;
    static String[] states;
    static List<String> newElements;

    public static void main(String[] args) {
        //Here the states are stored
        states = new String[]{"q0", "q1", "q2"};
        //Transition variables
        String[] trans = {"a", "b", "c"};

        newElements = new ArrayList<>();

        //The initial transition table
        table = new String[][]{
                {"q0 q1", "", ""},
                {"q2", "q1", "q0"},
                {"q2", "", ""}
        };

        //The resulting transition table
        new_table = new String[10][trans.length];

        //These are variables for determining the starting point of the new graph
        String start = null;
        int flag = 0;
        int start_index = 0;

        //Finding all the new states in the transition table and determining the starting point for DFA
        for(int i=0;i< states.length;i++){
            for (int z=0;z< trans.length;z++){
                if (!Arrays.asList(states).contains(table[i][z])){
                    newElements.add(table[i][z]);
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
            String[] product = findStates(newElements.get(i), states, trans);
            for(String pr:product){
                if (isNew(pr)){
                    newElements.add(pr);
                    size++;
                }
            }
            // The results from product[] are added to the new transition table
            System.arraycopy(product, 0, new_table[i], 0, trans.length);
        }


        //Printing the transition table
        System.out.println("State : a | b | c");
        System.out.print("-> " + start + " :");
        System.out.println(Arrays.toString(table[start_index]));

        for (int i = 0;i < newElements.size(); i++){
            if(newElements.get(i).length()<2){
                i++;
            }
            System.out.print(newElements.get(i) + " : ");

            for (int z = 0; z<trans.length; z++){
                System.out.print(new_table[i][z] + " | ");
            }
            System.out.println();
        }

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
            return new String[] {"","",""};
        }

        for (String unit : units) {
            int x = Arrays.asList(states).indexOf(unit);
            for (int i=0;i< eachTrans.length;i++){
                eachTrans[i] += table[x][i] + " ";
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
