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

    /**
     * pakeicia nurodytos matricos eilutes reiksmes
     * @param rowIndex - eilutes indeksas, pradedant nuo 0
     * @param values - reiksmiu masyvas
     */
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

    /**
     * pakeicia nurodyto matricos stulpelio reiksmes
     * @param columnIndex - stulpelio indeksas, pradedant nuo 0
     * @param values - reiksmiu masyvas
     */
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

    /**
     * Grazina nurodyta matricos eilute
     * @param rowIndex - indeksas 0, ... this.height
     * @return reiksmiu masyvas
     */
    public int[] getRow(int rowIndex) {
        int[] result = new int[width];
        for (int i = 0; i < result.length; i ++) {
            result[i] = data[rowIndex][i];
        }
        return result;
    }

    /**
     * Grazina nurodyta matricos stulpeli
     * @param columnIndex - indeksas 0, ... this.width
     * @return reiksmiu masyvas
     */
    public int[] getColumn(int columnIndex) {
        int[] result = new int[this.height];
        for (int i = 0; i < this.height; i++) {
            result[i] = data[i][columnIndex];
        }
        return result;
    }

    /**
     * Nustato naujas eilucku ir stulpeliu reiksmes
     * @param values this.height * this.width reiksmiu masyvas
     */
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

    /**
     * @return visos matricos reiksmes vienmacio masyvo formatu
     * Reiksmes imamos taip: data[0][1], data[0][2] , ... data[1][0] ...
     */
    public int[] getValues() {
        int[] result = new int[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i * width + j] = data[i][j];
            }
        }
        return result;
    }

    /**
     * Sudaugina einamaja matrica su i metoda paduota matrica
     * @param matrix - matrica is kurios dauginti. Turi tenkinti salyga this.width == matrix.height
     * @return einamosios ir i metoda paduotos matricos daugybos rezultatas
     */
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

    /**
     * Sudaugina dvi matricas moduliu 2
     * @param matrix - matrica is kurios dauginti. Turi tenkinti salyga this.width == matrix.height
     * @return einamosios ir i metoda paduotos matricos daugybos rezultatas
     */
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

    /**
     * Grazina kronecker produkta einanamosios ir i metoda paduotos matricos
     * @param matrix - 2 kroneckerio produkto operandas
     * @return kroneckerio produktas nurodytu matricu
     */
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

    /**
     * grazina masyva matricu is kuriu susides kroneckerio produkto rezultatas
     * @param matrix - matrica kuri bus dauginama is einamosios matricos koordinaciu, taip generuojant tarpines matricas
     * @return
     */
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

    /**
     * Padaugina visus masyve esancius skaicius is multiplier parametru nurodyto skaiciaus
     * @param array - skaiciu masyvas, kuris bus dauginamas
     * @param multiplier - skaicius is kurio bus dauginama
     * @return array masyvas padaugintas is multiplier
     */
    private int[] multiplyIntArray(int[] array, int multiplier) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] * multiplier;
        }
        return result;
    }

    /**
     * Iterpia i metoda paduota matrica i einamaja matrica (einamoji matrica turi buti didesniu dimensiju negu iterpiama matrica)
     * @param matrix - matrica kuri bus iterpiama i einamaja
     * @param startLine - nuo kurios einamosios matricos eilutes pradeti iterpineti reiksmes
     * @param startColumn - nuo kurio einamosios matricos stulpelio pradeti iterpineti reiksmes
     */
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

    /**
     * Grazina matricos reiksmes dvieju dimensiju masyvo formatu
     * @return this.data
     */
    public int[][] getData() {
        int[][] result = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = data[i][j];
            }
        }
        return result;
    }

    /**
     * Pakeicia matricos reiksmes naujomis reiksmemis
     * @param values - naujos matricos reiksmes dvieju dimensiju masyvo formatu (masyvo dimensijos turi sutapti su matricos dimensijomis)
     */
    public void setValues(int[][] values) {
        if (values.length == data.length) {
            data = values;
        }
        else throw new RuntimeException("The number of values in the array doesn't match the dimensions of the matrix \n"
                + "Matrix dimensions: " + height + "x" + width
                + "Number of values in the parameter array: " + values.length);
    }

    /**
     * @return String tipo matricos reprezentacija.
     * Visos reiksmes i string kintamaji surasomis be tarpu ar kitu specialiu simboliu.
     * Reiksmes imamos taip: data[0][1], data[0][2] , ... data[1][0] ...
     */
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
