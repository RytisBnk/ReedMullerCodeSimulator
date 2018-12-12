package Logic;

public class Decoder {
    private int m;
    private Matrix[] hmiMatrices;

    public Decoder(int m) {
        this.m = m;
        hmiMatrices = createHmiMatrices();
    }

    /**
     * dekoduoja teksta pagal greita Hadamardo transformacija
     * @param message - R(1,m) uzkoduotas dvejetainis tekstas
     * @return dekoduotas tekstas
     */
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

    /**
     * Visus 0 koordinates vektoriuje pakeicia i -1
     * @param vectors - vektoriu masyvas
     * @return pakeistas vektoriu masyvas, kur visos koordinates yra -1 arba 1
     */
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

    /**
     * Grazina vektoriu Wi, kur i = 1, 2, ... m, W1 = Wstart * H(1, m), Wi = W(i-1) * H(i, m)
     * Wstart -  is alterVectors metodo gautas vektorius
     * @param vector - vektoriu sekos pries tai einantis vektorius
     * @param i - vektoriaus indeksas
     * @return kitas vektorius auksciau apibreztoje sekoje
     */
    private Matrix computeWMVector(Vector vector, int i) {
        if (i == 1) {
            return vector.multiply(hmiMatrices[i - 1]);
        }
        else {
            return computeWMVector(vector, i -1).multiply(hmiMatrices[i - 1]);
        }
    }

    /**
     * Generuoja H(i,m) matricas, kurios veliau naudojamos generuoti Wi vektoriams
     * H(i,m) = I(2^(m-1)) x H x I(2^(i-1))
     * Kur I(N) - vienetine NxN matrica
     * H - 2 eiles hadamardo matrica [1, 1 ]
     *                               [1, -1]
     * Operacija x - kroneckerio produktas
     * @return matricu masyvas kur kieviena matrica tenkina virsuj nurodytas salygas
     */
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

    /**
     * Suranda didziausia modulines reiksmes pozicija vektoriuje (pradedant nuo 0)
     * @param vector - Vektorius kuriame ieskos reiksmes
     * @return pozicija, nuo 0 iki vektoriaus ilgio
     */
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

    /**
     * Gauna vectorLength parametro ilgio atvirsktine dvejetaine skaiciaus reprezentacija
     * Pvz: paduodama (4, 3) gautas atsakymas bus "001"
     * @param number - skaicius kurio reprezentacija norima gauti
     * @param vectorLength - reprezentacijos ilgis
     * @return String tipo devjetaine skaiciaus representacija su pasirinktu ilgiu
     */
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
