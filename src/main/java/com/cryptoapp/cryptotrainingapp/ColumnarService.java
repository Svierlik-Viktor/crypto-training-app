package com.cryptoapp.cryptotrainingapp;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ColumnarService {

    public ColumnarResponse encryptExplained(String plaintext, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) plaintext.length() / columns);
        char[][] matrix = new char[rows][columns];

        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                matrix[r][c] = index < plaintext.length() ? plaintext.charAt(index++) : 'X';
            }
        }

        Integer[] order = getKeyOrder(key);

        StringBuilder encrypted = new StringBuilder();
        for (int colIndex : order) {
            for (int r = 0; r < rows; r++) {
                encrypted.append(matrix[r][colIndex]);
            }
        }

        return new ColumnarResponse(
                encrypted.toString(),
                toCharList(key),
                Arrays.asList(order),
                toListMatrix(matrix)
        );
    }

    public ColumnarResponse decryptExplained(String ciphertext, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) ciphertext.length() / columns);
        char[][] matrix = new char[rows][columns];

        Integer[] order = getKeyOrder(key);

        int index = 0;
        for (int colIndex : order) {
            for (int r = 0; r < rows; r++) {
                if (index < ciphertext.length()) {
                    matrix[r][colIndex] = ciphertext.charAt(index++);
                }
            }
        }

        StringBuilder decrypted = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                decrypted.append(matrix[r][c]);
            }
        }

        return new ColumnarResponse(
                decrypted.toString().replaceAll("X+$", ""),
                toCharList(key),
                Arrays.asList(order),
                toListMatrix(matrix)
        );
    }

    private Integer[] getKeyOrder(String key) {
        Character[] keyChars = new Character[key.length()];
        for (int i = 0; i < key.length(); i++) keyChars[i] = key.charAt(i);

        Integer[] order = new Integer[key.length()];
        for (int i = 0; i < key.length(); i++) order[i] = i;

        Arrays.sort(order, Comparator.comparingInt(i -> keyChars[i]));
        return order;
    }

    private List<Character> toCharList(String s) {
        List<Character> list = new ArrayList<>();
        for (char c : s.toCharArray()) list.add(c);
        return list;
    }

    private List<List<Character>> toListMatrix(char[][] matrix) {
        List<List<Character>> list = new ArrayList<>();
        for (char[] row : matrix) {
            List<Character> rowList = new ArrayList<>();
            for (char c : row) rowList.add(c);
            list.add(rowList);
        }
        return list;
    }
}
