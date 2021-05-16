package com.utm.parse;

import com.utm.data.Production;

import java.util.ArrayList;
import java.util.List;

public class ParseUtil {
    static private String[] nonTerm = new String[]{};
    static private String[] term = new String[]{};
    static private String[] elements = new String[]{};
    static private List<Production> productions = new ArrayList<>();

    public static void readGrammar(String content){
        int a,b;

        a = content.indexOf("VN={");
        b = content.indexOf("}");
        nonTerm = content
                .substring(a+4,b)
                .split(",");

        a = content.indexOf("VT={");
        b = content.indexOf("}\nP={");
        term = content
                .substring(a+4,b)
                .split(",");

        a = content.indexOf("P={\n");
        b = content.lastIndexOf("}");

        elements = content
                .substring(a+4,b)
                .split("\n");

        for(String element:elements){
            String[] terms = element.split("->");
            productions.add(new Production(terms[0], terms[1]));
        }
    }

    public static String[] getNonTerm() {
        return nonTerm;
    }

    public static String[] getTerm() {
        return term;
    }

    public static List<Production> getProductions() {
        return productions;
    }
}
