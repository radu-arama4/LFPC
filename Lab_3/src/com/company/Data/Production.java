package com.company.Data;

import java.util.*;

public class Production {

    private Character left;
    private List<Character> right = new ArrayList<>();

    public Production(String left, String right) {
        this.left = (left.toCharArray()[0]);
        for(char ch:right.toCharArray()){
            this.right.add(ch);
        }
    }

    public boolean hasEmpty(){
        for (char ch:right){
            if(ch=='@'){
                return true;
            }
        }
        return false;
    }

    public boolean contains(char el){
        for(char ch:right){
            if(ch==el){
                return true;
            }
        }
        return false;
    }

    public Character getLeft() {
        return left;
    }

    public List<Character> getRight() {
        return right;
    }

    public String getLeftS(){
        return String.valueOf(left).replaceAll("[^a-zA-Z]", "");
    }

    public String getRightS(){
        return String.valueOf(right).replaceAll("[^a-zA-Z]", "");
    }

    @Override
    public String toString() {
        return left + " -> " + right.toString();
    }

}
