package com.cryptoapp.cryptotrainingapp.vigenere;

import org.springframework.stereotype.Service;

@Service
public class VigenereService {

    public VigenereResult encrypt(String text, String key) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase().replaceAll("[^A-Z]", "");

        if (key.isEmpty()) {
            throw new IllegalArgumentException("Ключ не може бути порожнім.");
        }

        StringBuilder result = new StringBuilder();
        StringBuilder explanation = new StringBuilder();

        explanation.append("Шифрування Віженера:<br><table border='1'><tr><th>Символ</th><th>Ключ</th><th>Результат</th></tr>");

        for (int i = 0, j = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            char keyCh = key.charAt(j % key.length());
            char encryptedChar = (char) (((ch + keyCh - 2 * 'A') % 26) + 'A');
            result.append(encryptedChar);

            explanation.append("<tr><td>").append(ch)
                    .append("</td><td>").append(keyCh)
                    .append("</td><td>").append(encryptedChar)
                    .append("</td></tr>");
            j++;
        }

        explanation.append("</table>");

        return new VigenereResult(result.toString(), explanation.toString());
    }

    public VigenereResult decrypt(String text, String key) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase().replaceAll("[^A-Z]", "");

        if (key.isEmpty()) {
            throw new IllegalArgumentException("Ключ не може бути порожнім.");
        }

        StringBuilder result = new StringBuilder();
        StringBuilder explanation = new StringBuilder();

        explanation.append("Розшифрування Віженера:<br><table border='1'><tr><th>Символ</th><th>Ключ</th><th>Результат</th></tr>");

        for (int i = 0, j = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            char keyCh = key.charAt(j % key.length());
            char decryptedChar = (char) (((ch - keyCh + 26) % 26) + 'A');
            result.append(decryptedChar);

            explanation.append("<tr><td>").append(ch)
                    .append("</td><td>").append(keyCh)
                    .append("</td><td>").append(decryptedChar)
                    .append("</td></tr>");
            j++;
        }

        explanation.append("</table>");

        return new VigenereResult(result.toString(), explanation.toString());
    }
}
