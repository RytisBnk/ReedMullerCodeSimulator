package Logic;

import java.util.Arrays;

public class Matrix {
    private int height;
    private int width;
    private int data[][];

    public Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        data = new int[height][width];
        for (int[] row : data) {
            Arrays.fill(row, 0);
        }
    }

    public void setRow(int rowIndex, int[] values){
        if (values.length == width) {
            for (int i = 0; i < width; i++) {
                data[rowIndex][i] = values[i];
            }
        }
        else throw new RuntimeException("The number of values doesn't match the number of columns in the matrix \n"
                + "Number of values: " + values.length
                + "Number of columns: " + width);
    }

    public void setColumn(int columnIndex, int[] values) {
        if (values.length == height) {
            for (int i = 0; i < height; i++) {
                data[i][columnIndex] = values[i];
            }
        }
        else throw new RuntimeException("The number of values doesn't match the number of lines in the matrix \n"
                + "Number of values: " + values.length
                + "Number of lines: " + height);
    }

    public int[] getRow(int rowIndex) {
        int[] result = new int[width];
        for (int i = 0; i < result.length; i ++) {
            result[i] = data[rowIndex][i];
        }
        return result;
    }

    public int[] getColumn(int columnIndex) {
        int[] result = new int[this.height];
        for (int i = 0; i < this.height; i++) {
            result[i] = data[i][columnIndex];
        }
        return result;
    }

    public void setValues(int[] values) {
        if (values.length == height * width) {
            for (int i= 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    data[i][j] = values[i * width + j];
                }
            }
        }
        else throw new RuntimeException("The number of values in the array doesn't match the dimensions of the matrix \n"
                + "Matrix dimensions: " + height + "x" + width
                + "Number of values in the parameter array: " + values.length);
    }

    public int[] getValues() {
        int[] result = new int[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i * width + j] = data[i][j];
            }
        }
        return result;
    }

    public Matrix multiply(Matrix matrix) {
        if (this.width == matrix.height) {
            Matrix result = new Matrix(this.height, matrix.width);
            int[][] resultData = result.getData();
            for (int i = 0; i < this.height; i ++) {
                int[] row = this.getRow(i);
                for (int j = 0; j < matrix.width; j++) {
                    int[] column = matrix.getColumn(j);
                    for (int k = 0; k < column.length; k++) {
                        resultData[i][j] += row[k] * column[k];
                    }
                }
            }
            result.setValues(resultData);
            return result;
        }
        else throw new RuntimeException("Dimensions of matrices do not match the dimensions required for multiplication \n"
                + "Matrix 1 dimensions: " + this.height + "x" + this.width
                + "Matrix 2 dimensions: " + matrix.height + "x" + matrix.width);
    }

    public Matrix multiplyMod2(Matrix matrix) {
        Matrix result = this.multiply(matrix);
        for (int i = 0; i < result.height; i++) {
            int[] row = result.getRow(i);
            for (int j = 0; j < row.length; j++) {
                row[j] %= 2;
            }
            result.setRow(i, row);
        }
        return result;
    }

    public Matrix getKroneckerProduct(Matrix matrix) {
        Matrix result = new Matrix(this.height * matrix.height, this.width * matrix.width);
        Matrix[][] partials = getPartialMatrices(matrix);
        for (int i = 0, a = 0; i < height; i++, a = i * matrix.height) {
            for (int j = 0, b = 0; j < width; j++, b = j * matrix.width) {
                result.insert(partials[i][j], a, b);
            }
        }
        return result;
    }

    private Matrix[][] getPartialMatrices(Matrix matrix) {
        Matrix[][] result = new Matrix[height][width];
        int[] values = matrix.getValues();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Matrix temp = new Matrix(matrix.getHeight(), matrix.getWidth());
                temp.setValues(multiplyIntArray(values, data[i][j]));
                result[i][j] = temp;
            }
        }
        return result;
    }

    private int[] multiplyIntArray(int[] array, int multiplier) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] * multiplier;
        }
        return result;
    }

    private void insert(Matrix matrix, int startLine, int startColumn) {
        int[][] isertionData = matrix.getData();
        for (int i = startLine; i < startLine + matrix.height; i++) {
            for (int j = startColumn; j < startColumn + matrix.width; j++) {
                data[i][j] = isertionData[i - startLine][j - startColumn];
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[][] getData() {
        int[][] result = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = data[i][j];
            }
        }
        return result;
    }

    public void setValues(int[][] values) {
        if (values.length == data.length) {
            data = values;
        }
        else throw new RuntimeException("The number of values in the array doesn't match the dimensions of the matrix \n"
                + "Matrix dimensions: " + height + "x" + width
                + "Number of values in the parameter array: " + values.length);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int[] row : data) {
            for (int value : row) {
                builder.append(value);
            }
        }
        return builder.toString();
    }
}
