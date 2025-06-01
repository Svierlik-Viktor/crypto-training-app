package com.cryptoapp.cryptotrainingapp.columnar;

import java.util.List;

public class ColumnarResponse {
    private String result;
    private List<Character> key;
    private List<Integer> order;
    private List<List<Character>> matrix;

    public ColumnarResponse(String result, List<Character> key, List<Integer> order, List<List<Character>> matrix) {
        this.result = result;
        this.key = key;
        this.order = order;
        this.matrix = matrix;
    }

    public String getResult() {
        return result;
    }

    public List<Character> getKey() {
        return key;
    }

    public List<Integer> getOrder() {
        return order;
    }

    public List<List<Character>> getMatrix() {
        return matrix;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setKey(List<Character> key) {
        this.key = key;
    }

    public void setOrder(List<Integer> order) {
        this.order = order;
    }

    public void setMatrix(List<List<Character>> matrix) {
        this.matrix = matrix;
    }
}
