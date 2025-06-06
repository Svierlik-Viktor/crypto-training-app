package com.cryptoapp.cryptotrainingapp.columnar;

import java.util.List;

public class ColumnarResult {
    private String result;
    private List<Character> key;
    private List<Integer> order;
    private List<List<Character>> matrix;
    private List<List<Character>> rearrangedMatrix;
    private List<List<Character>> decryptedMatrix;

    public ColumnarResult(String result, List<Character> key, List<Integer> order,
                          List<List<Character>> matrix,
                          List<List<Character>> rearrangedMatrix,
                          List<List<Character>> decryptedMatrix) {
        this.result = result;
        this.key = key;
        this.order = order;
        this.matrix = matrix;
        this.rearrangedMatrix = rearrangedMatrix;
        this.decryptedMatrix = decryptedMatrix;
    }

    public String getResult() { return result; }
    public List<Character> getKey() { return key; }
    public List<Integer> getOrder() { return order; }
    public List<List<Character>> getMatrix() { return matrix; }
    public List<List<Character>> getRearrangedMatrix() { return rearrangedMatrix; }
    public List<List<Character>> getDecryptedMatrix() { return decryptedMatrix; }

    public void setResult(String result) { this.result = result; }
    public void setKey(List<Character> key) { this.key = key; }
    public void setOrder(List<Integer> order) { this.order = order; }
    public void setMatrix(List<List<Character>> matrix) { this.matrix = matrix; }
    public void setRearrangedMatrix(List<List<Character>> rearrangedMatrix) { this.rearrangedMatrix = rearrangedMatrix; }
    public void setDecryptedMatrix(List<List<Character>> decryptedMatrix) { this.decryptedMatrix = decryptedMatrix; }
}
