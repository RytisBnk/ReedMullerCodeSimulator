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
     * @param data - binary string
     * @return RM(1,m) encoded binary string
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
     * @param message - binary string representing the data
     * @return array of Vector objects with a length of (m + 1), each containing (m + 1)/message_length of the data
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
