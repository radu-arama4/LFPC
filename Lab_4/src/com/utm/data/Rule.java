package com.utm.data;

public class Rule {

    private String left;
    private String sign;
    private String right;

    public Rule(String left, String sign, String right) {
        this.left = left;
        this.sign = sign;
        this.right = right;
    }

    public boolean check(String left, String right){
        return this.left.equals(left) && this.right.equals(right);
    }

    public String getLeft() {
        return left;
    }

    public String getSign() {
        return sign;
    }

    public String getRight() {
        return right;
    }

    @Override
    public String toString() {
        return getLeft() + getSign() + getRight();
    }
}
