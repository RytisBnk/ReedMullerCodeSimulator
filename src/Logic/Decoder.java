package Logic;

public class Decoder {
    private int m;
    private Matrix[] hmiMatrices;

    public Decoder(int m) {
        this.m = m;
        hmiMatrices = createHmiMatrices();
    }

    public String decode(String message) {
        BinaryConverter converter = new BinaryConverter();
        Vector[] receivedVectors = converter.splitBinaryStringToVectors(message, (int) Math.pow(2, m));
        receivedVectors = alterVectors(receivedVectors);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < receivedVectors.length; i++) {
            Matrix computedWMVector = computeWMVector(receivedVectors[i], m);
            int largestAbsolutePosition = getLargestAbsoluteValuePosition(computedWMVector);
            if (computedWMVector.getValues()[largestAbsolutePosition] < 0) {
                builder.append("0");
            }
            else {
                builder.append("1");
            }
            builder.append(getReverseBinary(largestAbsolutePosition, m ));
        }

        return builder.toString();
    }

    private Vector[] alterVectors(Vector[] vectors) {
        Vector[] result = new Vector[vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            int[] vectorData = vectors[i].getValues();
            for (int j = 0; j < vectorData.length; j++) {
                vectorData[j] = (vectorData[j] == 0) ? (-1) : vectorData[j];
            }
            result[i] = new Vector(vectors[i].getWidth(), vectorData);
        }
        return result;
    }

    private Matrix computeWMVector(Vector vector, int i) {
        if (i == 1) {
            return vector.multiply(hmiMatrices[i - 1]);
        }
        else {
            return computeWMVector(vector, i -1).multiply(hmiMatrices[i - 1]);
        }
    }

    private Matrix[] createHmiMatrices() {
        MatrixFactory factory = new MatrixFactory();
        Matrix[] result = new Matrix[m];
        for (int i = 1; i <= m; i++) {
            int identityMatrix1Size = (int) Math.pow(2, m - i);
            int identityMatrix2Size = (int) Math.pow(2, i - 1);
            Matrix H = factory.createHadamard2Matrix();
            Matrix identity1 = factory.createIdentityMatrix(identityMatrix1Size);
            Matrix identity2 = factory.createIdentityMatrix(identityMatrix2Size);
            result[i - 1] = identity1.getKroneckerProduct(H).getKroneckerProduct(identity2);
        }
        return result;
    }

    private int getLargestAbsoluteValuePosition(Matrix vector) {
        int maxValue = 0;
        int index = 0;
        int[] values = vector.getValues();
        for (int i = 0; i < values.length; i++) {
            if (values[i] < 0) {
                values[i] *= (-1);
            }
            if (values[i] > maxValue) {
                maxValue = values[i];
                index = i;
            }
        }
        return index;
    }

    private String getReverseBinary(int number, int vectorLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(Integer.toBinaryString(number));
        builder.reverse();
        int missingLength = vectorLength - builder.length();
        for (int i = 0; i < missingLength; i++) {
            builder.append("0");
        }
        return builder.toString();
    }
}
