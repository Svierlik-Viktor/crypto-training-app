package com.cryptoapp.cryptotrainingapp.ecdsa;

public class ECDSAResultExplanation {
    private String message;
    private String base64Signature;
    private boolean valid;
    private String htmlFormatted;

    // Getters and setters
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getBase64Signature() {
        return base64Signature;
    }
    public void setBase64Signature(String base64Signature) {
        this.base64Signature = base64Signature;
    }

    public boolean isValid() {
        return valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getHtmlFormatted() {
        return htmlFormatted;
    }
    public void setHtmlFormatted(String htmlFormatted) {
        this.htmlFormatted = htmlFormatted;
    }
}
