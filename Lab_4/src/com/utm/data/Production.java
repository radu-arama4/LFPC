package com.utm.data;

import java.util.ArrayList;
import java.util.List;

public class Production {
    private String left;
    private String right;

    public Production(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + " -> " + right.toString();
    }

}
