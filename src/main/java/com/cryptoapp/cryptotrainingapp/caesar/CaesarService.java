package com.cryptoapp.cryptotrainingapp.caesar;

import org.springframework.stereotype.Service;

@Service
public class CaesarService {

    public CaesarResult encryptDetailed(String text, int shift) {
        StringBuilder result = new StringBuilder();
        StringBuilder explanation = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                int originalIndex = ch - 'A';
                int shiftedIndex = (originalIndex + shift) % 26;
                char encrypted = (char) ('A' + shiftedIndex);
                explanation.append(ch)
                        .append(" → ").append(encrypted)
                        .append(" (").append(originalIndex)
                        .append(" + ").append(shift)
                        .append(" % 26 = ").append(shiftedIndex)
                        .append(")").append("\n");
                result.append(encrypted);
            } else if (Character.isLowerCase(ch)) {
                int originalIndex = ch - 'a';
                int shiftedIndex = (originalIndex + shift) % 26;
                char encrypted = (char) ('a' + shiftedIndex);
                explanation.append(ch)
                        .append(" → ").append(encrypted)
                        .append(" (").append(originalIndex)
                        .append(" + ").append(shift)
                        .append(" % 26 = ").append(shiftedIndex)
                        .append(")").append("\n");
                result.append(encrypted);
            } else {
                explanation.append(ch).append(" → (не шифрується)").append("\n");
                result.append(ch);
            }
        }

        return new CaesarResult(text, shift, result.toString(), explanation.toString());
    }

    public CaesarResult decryptDetailed(String text, int shift) {
        int adjustedShift = 26 - (shift % 26);
        return encryptDetailed(text, adjustedShift);
    }
}
