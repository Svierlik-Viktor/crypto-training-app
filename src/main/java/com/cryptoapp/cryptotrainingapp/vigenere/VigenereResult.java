package com.cryptoapp.cryptotrainingapp.vigenere;

public class VigenereResult {
    private String result;
    private String explanation;

    public VigenereResult(String result, String explanation) {
        this.result = result;
        this.explanation = explanation;
    }

    public String getResult() {
        return result;
    }

    public String getExplanation() {
        return explanation;
    }
}
