package com.cryptoapp.cryptotrainingapp;

public class VigenereResponse {
    private String result;
    private String explanation;

    public VigenereResponse(String result, String explanation) {
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
