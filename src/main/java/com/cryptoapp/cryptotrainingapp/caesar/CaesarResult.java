package com.cryptoapp.cryptotrainingapp.caesar;

public class CaesarResult {
    private String inputText;
    private int shift;
    private String resultText;
    private String explanation;

    public CaesarResult(String inputText, int shift, String resultText, String explanation) {
        this.inputText = inputText;
        this.shift = shift;
        this.resultText = resultText;
        this.explanation = explanation;
    }

    public String getInputText() {
        return inputText;
    }

    public int getShift() {
        return shift;
    }

    public String getResultText() {
        return resultText;
    }

    public String getExplanation() {
        return explanation;
    }
}
