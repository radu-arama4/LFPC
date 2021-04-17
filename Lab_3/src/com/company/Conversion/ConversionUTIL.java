package com.company.Conversion;

import com.company.Data.Production;

import java.util.*;

public class ConversionUTIL {

    static private String[] nonTerm;
    static private String[] term;
    static private List<Production> productions;
    static private List<Production> lastProductions;
    static private List<Production> productionsBeta;
    static HashSet<String> Accessible = new HashSet<>();

    static private int[][] myBinary = new int[10][10];
    static private int j = 0;

    public static void convertToCNF(String[] nonT, String[] T, List<Production> Prod){
        nonTerm = nonT;
        term = T;
        productions = Prod;
        productionsBeta = productions;

        removeEmpty(); //done

        removeUnitProductions(); // done

        removeNonProductive(); //done

        removeInaccessible(); //done

        transformToCNF();

        for (Production pr:productions){
            System.out.println(pr.toString());
        }

    }

    static void generateAllBinaryStrings(int n, int arr[], int i) {
        if (i == n)
        {
            if (n >= 0) System.arraycopy(arr, 0, myBinary[j], 0, n);
            j++;
            return;
        }
        arr[i] = 0;
        generateAllBinaryStrings(n, arr, i + 1);

        arr[i] = 1;
        generateAllBinaryStrings(n, arr, i + 1);
    }

    private static void removeEmpty(){
        List<Character> emptyNonT = new ArrayList<>();

        for(Production prod : productions){
            if (prod.hasEmpty()){
                emptyNonT.add(prod.getLeft());
                productions.remove(prod);
            }
        }

        List<Production> emptyProds = new ArrayList<>();

        for(Character ch:emptyNonT){
            for(Production prod : productions){
                if (prod.contains(ch)){
                    List<Character> current = prod.getRight();
                    String curr = current.toString().replaceAll("[^a-zA-Z]", "");

                    int n = (int) current.stream().filter(a -> a == ch).count();
                    int[] myArr = new int[n];

                    j=0;
                    generateAllBinaryStrings(n, myArr, 0);

                    for (int i=0;i<Math.pow(2,n)-1;i++){
                        int j = 0;
                        StringBuilder newProd = new StringBuilder();

                        for(char c : curr.toCharArray()){
                            if(c==ch && myBinary[i][j]==1){
                                newProd.append(c);
                                j++;
                            }else if(c!=ch){
                                newProd.append(c);
                            }else {
                                j++;
                            }
                        }
                        emptyProds.add(new Production(String.valueOf(prod.getLeft()), newProd.toString()));
                    }
                }
            }
        }
        productions.addAll(emptyProds);
    }

    private static void removeUnitProductions(){
        List<Production> unitNonT = new ArrayList<>();
        List<Production> newProds = new ArrayList<>();

        for(Production production:productions){
            String current = String.valueOf(production
                    .getRight())
                    .replaceAll("[^a-zA-Z]", "");
            if(current.length()==1 &&
                    current.equals(current.toUpperCase())){
                unitNonT.add(production);
            }
        }

        for(Production pr : unitNonT){
            String currentR = String.valueOf(pr
                    .getRight())
                    .replaceAll("[^a-zA-Z]", "");
            String currentL = String.valueOf(pr.getLeft())
                    .replaceAll("[^a-zA-Z]", "");
            for (Production production:productions){
                if (String.valueOf(production.getLeft())
                        .replaceAll("[^a-zA-Z]", "")
                        .equals(currentR)){
                    newProds.add(new Production(currentL,
                            String.valueOf(production.getRight())
                                    .replaceAll("[^a-zA-Z]", "")));
                }
            }
        }

        productions.removeAll(unitNonT);
        productions.addAll(newProds);
    }

    private static boolean checkReachTerminals(String nonT){
        for (Production pr : productionsBeta){
            if (pr.getLeftS().equals(nonT)){
                if (pr.getRightS().equals(pr.getRightS().toLowerCase())){
                    return true;
                }else{
                    productionsBeta.remove(pr);
                    for(Character ch : pr.getRightS().toCharArray()){
                        if(Character.isUpperCase(ch)){
                            return checkReachTerminals(ch.toString());
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void removeNonProductive(){
        List<String> nonProdTerm = new ArrayList<>();
        Collections.copy(productionsBeta, productions);

        for(String nonT : nonTerm){
            if(!checkReachTerminals(nonT)){
                nonProdTerm.add(nonT);
            }
        }

        for(String nonT : nonProdTerm){
            productions.removeIf(pr -> pr.getRightS().contains(nonT) || pr.getLeftS().contains(nonT));
        }

    }

    private static void traverseProd(String nonT){
        Accessible.add(nonT);
        for (Production pr : productionsBeta){
            if (pr.getLeftS().equals(nonT)){
                if (pr.getRightS().equals(pr.getRightS().toLowerCase())){
                    continue;
                }else{
                    for(Character ch : pr.getRightS().toCharArray()){
                        if(Character.isUpperCase(ch) && !Accessible.contains(ch.toString())){
                            traverseProd(ch.toString());
                            return;
                        }
                    }
                }
            }
        }
    }

    private static void removeInaccessible(){
        List<String> Inaccessible = new ArrayList<>();
        Collections.copy(productionsBeta, productions);

        traverseProd("S");

        for(String nonT:nonTerm){
            if(!Accessible.contains(nonT)){
                Inaccessible.add(nonT);
            }
        }

        for (String pr : Inaccessible){
            productions.removeIf(prod -> prod.getLeftS().equals(pr));
        }
    }

    static int i = 0;

    private static void transformToCNF(){
        lastProductions = new ArrayList<>();


    }

}
