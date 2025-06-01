package com.cryptoapp.cryptotrainingapp.rsa;

public class RSAResultExplanation {
    private String inputText;
    private String inputBytes;
    private String base64BeforeEncryption;
    private String encryptedBase64;
    private String decryptedBase64;
    private String outputText;
    private String htmlFormatted;

    // Геттери та сеттери

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getInputBytes() {
        return inputBytes;
    }

    public void setInputBytes(String inputBytes) {
        this.inputBytes = inputBytes;
    }

    public String getBase64BeforeEncryption() {
        return base64BeforeEncryption;
    }

    public void setBase64BeforeEncryption(String base64BeforeEncryption) {
        this.base64BeforeEncryption = base64BeforeEncryption;
    }

    public String getEncryptedBase64() {
        return encryptedBase64;
    }

    public void setEncryptedBase64(String encryptedBase64) {
        this.encryptedBase64 = encryptedBase64;
    }

    public String getDecryptedBase64() {
        return decryptedBase64;
    }

    public void setDecryptedBase64(String decryptedBase64) {
        this.decryptedBase64 = decryptedBase64;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getHtmlFormatted() {
        return htmlFormatted;
    }

    public void setHtmlFormatted(String htmlFormatted) {
        this.htmlFormatted = htmlFormatted;
    }
}
