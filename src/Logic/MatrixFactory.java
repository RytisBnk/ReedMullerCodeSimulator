package Logic;

import java.util.Arrays;

public class MatrixFactory {
    /**
     * @param m
     * @return R(1,m) code generator matrix
     */
    public Matrix createGeneratorMatrix(int m) {
        int height = m + 1;
        int width = (int) Math.pow(2, m);
        Matrix matrix = new Matrix(height, width);
        for (int i = 0; i < height; i++) {
            if (i != height - 1) {
                int[] row = getFilledRow(width, getMaxConsecutive(i, m));
                matrix.setRow(i, row);
            }
            else {
                int[] row = new int[width];
                Arrays.fill(row, 1);
                matrix.setRow(i, row);
            }
        }
        int[][] data = matrix.getData();
        for (int i = 0, j = matrix.getHeight() - 1; i < matrix.getHeight(); i++, j--) {
            matrix.setRow(i, data[j]);
        }
        return matrix;
    }

    public Matrix createIdentityMatrix(int size) {
        Matrix matrix = new Matrix(size, size);
        int[][] data = matrix.getData();
        for (int i = 0; i < matrix.getHeight(); i++) {
            data[i][i] = 1;
        }
        matrix.setValues(data);
        return matrix;
    }

    public Matrix createHadamard2Matrix() {
        Matrix matrix = new Matrix(2, 2);
        matrix.setValues(new int[][]{
                new int[]{1, 1},
                new int[]{1, -1}
        });
        return matrix;
    }

    /**
     * @param length - the length of the generator matrix row (or number of columns in the generator matrix)
     * @param maxConsecutiveSymbols - maximum number of 1s or 0s to be written consecutively
     * @return generator matrix row
     */
    private int[] getFilledRow(int length, int maxConsecutiveSymbols) {
        int currentSymbol = 0;
        int counter = 0;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = currentSymbol;
            counter++;
            if (counter == maxConsecutiveSymbols) {
                currentSymbol = (currentSymbol == 0) ? 1 : 0;
                counter = 0;
            }
        }
        return result;
    }

    private int getMaxConsecutive(int rowIndex, int m) {
        return (int) (Math.pow(2, m) / Math.pow(2, rowIndex + 1));
    }
}
