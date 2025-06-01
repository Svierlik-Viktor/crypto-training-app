package com.cryptoapp.cryptotrainingapp.common;

public class TextResponseWithExplanation {
    private String result;
    private String explanation;

    public TextResponseWithExplanation(String result, String explanation) {
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
