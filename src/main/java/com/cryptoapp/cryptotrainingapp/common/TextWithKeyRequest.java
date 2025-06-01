package com.cryptoapp.cryptotrainingapp.common;

public class TextWithKeyRequest {
    private String text;
    private String key;

    public String getText() {
        return text;
    }

    public String getKey() {
        return key;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
