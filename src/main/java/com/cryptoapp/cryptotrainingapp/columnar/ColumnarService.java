package com.cryptoapp.cryptotrainingapp.columnar;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;


@Service
public class ColumnarService {

    public ColumnarResult encryptExplained(String plaintext, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) plaintext.length() / columns);
        char[][] matrix = new char[rows][columns];

        // Заповнення матриці
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                matrix[r][c] = index < plaintext.length() ? plaintext.charAt(index++) : 'X';
            }
        }

        Integer[] order = getKeyOrder(key);
        StringBuilder encrypted = new StringBuilder();
        char[][] rearranged = new char[rows][columns];

        // Шифрування та створення rearrangedMatrix
        for (int newCol = 0; newCol < columns; newCol++) {
            int originalCol = order[newCol];
            for (int r = 0; r < rows; r++) {
                rearranged[r][newCol] = matrix[r][originalCol];
                encrypted.append(matrix[r][originalCol]);
            }
        }

        return new ColumnarResult(
                encrypted.toString(),
                toCharList(key),
                Arrays.asList(order),
                toListMatrix(matrix),
                toListMatrix(rearranged),
                null
        );
    }

    public ColumnarResult decryptExplained(String ciphertext, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) ciphertext.length() / columns);
        char[][] matrix = new char[rows][columns];
        Integer[] order = getKeyOrder(key);

        // Заповнення матриці при дешифруванні
        int index = 0;
        for (int colIndex : order) {
            for (int r = 0; r < rows; r++) {
                if (index < ciphertext.length()) {
                    matrix[r][colIndex] = ciphertext.charAt(index++);
                }
            }
        }

        // Формування результату
        StringBuilder decrypted = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                decrypted.append(matrix[r][c]);
            }
        }

        return new ColumnarResult(
                decrypted.toString().replaceAll("X+$", ""),
                toCharList(key),
                Arrays.asList(order),
                toListMatrix(matrix),
                null,
                toListMatrix(matrix)
        );
    }

    private Integer[] getKeyOrder(String key) {
        Character[] keyChars = key.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Integer[] order = IntStream.range(0, key.length()).boxed().toArray(Integer[]::new);
        Arrays.sort(order, Comparator.comparingInt(i -> keyChars[i]));
        return order;
    }

    private List<Character> toCharList(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    private List<List<Character>> toListMatrix(char[][] matrix) {
        List<List<Character>> result = new ArrayList<>();
        for (char[] row : matrix) {
            List<Character> rowList = new ArrayList<>();
            for (char c : row) {
                rowList.add(c);
            }
            result.add(rowList);
        }
        return result;
    }
}