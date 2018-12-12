package Logic;

public class Encoder {
    private Matrix genMatrix;
    private int m;

    public Encoder(int m) {
        this.m = m;
        MatrixFactory factory = new MatrixFactory();
        this.genMatrix = factory.createGeneratorMatrix(m);
    }

    /**
     * Uzkoduoja duomenis R(1, m) kodu
     * @param data - dcejetaine simboliu eilute
     * @return uzkoduota dvejetaine simboliu eilute
     */
    public String encode(String data) {
        Vector[] dataVectors = splitToVectors(data);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dataVectors.length; i++) {
            Matrix encodedVector = dataVectors[i].multiplyMod2(genMatrix);
            builder.append(encodedVector.toString());
        }
        return builder.toString();
    }

    /**
     * Isskaido duomenis i m + 1 ilgio vektorius
     * @param message - dcejetaine simboliu eilute
     * @return Vector objektu masyvas
     */
    private Vector[] splitToVectors(String message) {
        int mod = message.length() % (m + 1);
        String correctedMessage = message;
        if (mod != 0) {
            int difference = (m + 1) - mod;
            for (int i = 0; i < difference; i++) {
                correctedMessage = "0".concat(correctedMessage);
            }
        }
        BinaryConverter converter = new BinaryConverter();
        return converter.splitBinaryStringToVectors(message, m + 1);
    }

    public Matrix getGenMatrix() {
        return genMatrix;
    }
}
