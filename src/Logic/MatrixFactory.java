package Logic;

import java.util.Arrays;

public class MatrixFactory {
    /**
     * Sukuria generuojancia matrica R(1,m) kodui
     * @param m - R(1,m) parametras
     * @return Matrix objekta representuojanti kodo generuojancia matrica
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

    /**
     * Sukuria nurodytu laipsnio vienetine matrica
     * @param size - vienetines matricos laipsnis
     * @return size X size dydzio Matrix objektas
     */
    public Matrix createIdentityMatrix(int size) {
        Matrix matrix = new Matrix(size, size);
        int[][] data = matrix.getData();
        for (int i = 0; i < matrix.getHeight(); i++) {
            data[i][i] = 1;
        }
        matrix.setValues(data);
        return matrix;
    }

    /**
     * Sukuria 2 eiles Hadamardo matrica (1 eilute: 1, 1. 2 eilute: 1, -1)
     * @return MAtrix objektas, representuojantis 2 eiles hadamardo matrica
     */
    public Matrix createHadamard2Matrix() {
        Matrix matrix = new Matrix(2, 2);
        matrix.setValues(new int[][]{
                new int[]{1, 1},
                new int[]{1, -1}
        });
        return matrix;
    }

    /**
     * Sugeneruoja skaiciu masyva, kuriame puse reiksmiu bus 1, kiat puse - 0
     * @param length - masyvo dydis, turi buti lyginis skaicius
     * @param maxConsecutiveSymbols - kiek to paties simbolio rasyti is eiles, pvz: jei 1, 010101..., jei 2 tai 00110011...
     * @return generuojancios matricos reiksmiu eilute
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

    /**
     * Apskaiciuoja po kiek 1 ar 0 is eiles rasyti 1 generuojancios matricos tam tikroje eiluteje
     * @param rowIndex - eilutes indeksas
     * @param m - R(1,m) kodo parametras
     * @return skaicius, nusakantis kiek to paties simbolio is eiles reikia rasyti konkreciu indeksu pazymetoje eiluteje
     */
    private int getMaxConsecutive(int rowIndex, int m) {
        return (int) (Math.pow(2, m) / Math.pow(2, rowIndex + 1));
    }
}
