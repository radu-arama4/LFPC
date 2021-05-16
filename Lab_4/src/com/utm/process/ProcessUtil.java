package com.utm.process;

import com.sun.deploy.util.ArrayUtil;
import com.utm.data.Production;
import com.utm.data.Rule;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessUtil {

    static Map<String, String> first = new HashMap<>();
    static Map<String, String> last = new HashMap<>();
    static List<Production> productions2 = null;
    static private List<Rule> rules = new ArrayList<>();

    static public void processInput(String input, List<Production> productions, String[] nonTerm, String[] term){

        System.out.println(input);

        for (Production pr:productions){
            System.out.println(pr.toString());
        }

        productions2 = productions;

        //first
        for (String nT:nonTerm){
            findFirst(nT);
            first.put(nT, myFirst.toString());
            myFirst = new StringBuilder();
        }

        //last
        for (String nT:nonTerm){
            findLast(nT);
            last.put(nT, myLast.toString());
            myLast = new StringBuilder();
        }

        findRules();

        System.out.println("Rules:");
        for (Rule rule : rules){
            System.out.println(rule.toString());
        }

        System.out.println("\nMatrix:");
        createMatrix(nonTerm, term);

        System.out.println("\nParsing:");
        if(parseInput(input)){
            System.out.println("Word accepted!");
        }else {
            System.out.println("Word not accepted!");
        }

    }

    private static boolean parseInput(String input){

        StringBuilder sb = new StringBuilder(input);

        sb.deleteCharAt(sb.length()-1);
        sb.insert(0, "<");
        sb.append(">");

        for(int i=1;i<sb.length()-2;i=i+2){
            sb.insert(i+1, findRule(String.valueOf(sb.charAt(i)),
                    String.valueOf(sb.charAt(i+1))));
        }

        String expression = sb.toString();

        int c=0;
        while (true){
            Pattern p = Pattern.compile("<[a-zA-Z=]+>");
            int i = indexOf(p, expression);
            Matcher m = p.matcher(expression);

            if(c>100){
                return false;
            }
            if(m.find()){
                String right = findProd(m.group(0));

                if(right.equals("S")){
                    System.out.println("<S>");
                    return true;
                }

                char first = expression.charAt(i-1);
                String abc = m.group(0);

                int sum = abc.length() + i;
                StringBuilder sb2 = new StringBuilder();

                if(sum>=expression.length()-1){
                    sb2.append(findRule(String.valueOf(first), right));
                    sb2.append(right);
                    sb2.append(">");
                }else{
                    char second = expression.charAt(sum);
                    sb2.append(findRule(String.valueOf(first), right));
                    sb2.append(right);
                    sb2.append(findRule(right, String.valueOf(second)));
                }

                expression = expression.replaceFirst(abc, sb2.toString());

                System.out.println(expression);
            }
            c++;
        }
    }

    public static int indexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.start() : -1;
    }

    private static String findProd(String right){
        right = right.replaceAll("[^a-zA-Z]", "");
        for(Production rule:productions2){
            if(rule.getRight().contains(right)){
                return rule.getLeft();
            }
        }
        return null;
    }

    private static String findRule(String left, String right){
        for(Rule rule:rules){
            if(rule.getLeft().contains(left) && rule.getRight().contains(right)){
                return rule.getSign();
            }
        }
        return null;
    }

    private static void createMatrix(String[] nonTerm, String[] term) {
        int size = nonTerm.length+term.length+1;
        String[][] matrix = new String[size][size];
        String[] all = new String[size];
        System.arraycopy(nonTerm, 0, all, 0,  nonTerm.length);
        System.arraycopy(term, 0, all, nonTerm.length, term.length);

        for (Rule rule:rules){
            int i;
            int j;
            if(rule.getRight().length()==1 && rule.getLeft().length()==1){
                i = Arrays.binarySearch(all, rule.getLeft());
                j = Arrays.binarySearch(all, rule.getRight());
                matrix[i][j] = rule.getSign();
            }
            else if(rule.getRight().length()==1 && rule.getLeft().length()!=1){
                j = Arrays.binarySearch(all, rule.getRight());
                for(char ch:rule.getLeft().toCharArray()){
                    i = Arrays.binarySearch(all, String.valueOf(ch));
                    matrix[i][j] = rule.getSign();
                }
            }
            else{
                i = Arrays.binarySearch(all, rule.getLeft());
                for(char ch:rule.getRight().toCharArray()){
                    j = Arrays.binarySearch(all, String.valueOf(ch));
                    matrix[i][j] = rule.getSign();
                }
            }
        }

        for(int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                if(matrix[i][j]==null){
                    System.out.print("* ");
                }else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    static private StringBuilder myFirst = new StringBuilder();

    static private void findFirst(String element){
        for (Production pr:productions2){
            if(pr.getLeft().equals(element)){
                String potentialF = pr.getRight().substring(0,1);
                if(!myFirst.toString().contains(potentialF)){
                    myFirst.append(potentialF);
                }
                if(potentialF.equals(potentialF.toUpperCase())){
                    findFirst(potentialF);
                }
            }
        }
    }

    static private StringBuilder myLast = new StringBuilder();

    static private void findLast(String element){
        for (Production pr:productions2){
            if(pr.getLeft().equals(element)){
                String potentialF = pr.getRight().substring(pr.getRight().length()-1);
                if(!myLast.toString().contains(potentialF)){
                    myLast.append(potentialF);
                }else {
                    return;
                }
                if(potentialF.equals(potentialF.toUpperCase())){
                    findLast(potentialF);
                }
            }
        }
    }

    static private void findRules(){
        //first rule
        for(Production production:productions2){
            String right = production.getRight();
            for (int i=0;i<right.length()-1;i++){
                String el1 = Character.toString(right.charAt(i));
                String el2 = Character.toString(right.charAt(i+1));
                rules.add(new Rule(el1, "=", el2));
                if(el1.equals(el1.toLowerCase()) && el2.equals(el2.toUpperCase())){
                    rules.add(new Rule(el1, "<", first.get(el2)));
                }
                if(el1.equals(el1.toUpperCase()) && el2.equals(el2.toLowerCase())){
                    rules.add(new Rule(last.get(el1), ">", el2));
                }
            }
        }
    }

}
