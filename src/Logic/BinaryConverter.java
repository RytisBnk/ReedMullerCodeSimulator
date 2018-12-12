package Logic;

public class BinaryConverter {
    /**
     * Konvertuoja teksta i dvejetaine teskto eilute (utf-8 simbolius i (0, 1)
     * @param text - is GUI paduotas tekstas
     * @return dvejetaine teksto reprezentacija String kintamajame
     */
    public String getBinaryString(String text) {
        StringBuilder builder = new StringBuilder();
        char[] characters = text.toCharArray();
        for (char character : characters) {
            String binaryString = Integer.toBinaryString(character);
            int missingZeroes = 8 - binaryString.length();
            if (missingZeroes > 0) {
                for (int i = 0; i < missingZeroes; i++) {
                    binaryString = "0".concat(binaryString);
                }
            }
            builder.append(binaryString);
        }
        return builder.toString();
    }

    /**
     * Konvertuoja dvejetaine teksto israiska i utf-8 formata
     * @param binaryString - devejetainis tekstas
     * @return utf-8 formato tekstas
     */
    public String getUTF8String(String binaryString) {
        StringBuilder builder = new StringBuilder();
        while (binaryString.length() > 7) {
            String characterBinaryString = binaryString.substring(0, 8);
            int characterNumericValue = Integer.parseInt(characterBinaryString, 2);
            char character = (char) characterNumericValue;
            builder.append(character);
            binaryString = binaryString.substring(8);
        }
        return builder.toString();
    }

    /**
     * Isskaido dvejetaine israiska i Vector klases objektus
     * @param message - devjetainis tekstas
     * @param vectorLength -  vektoriaus ilgis
     * @return Vector objektu masyvas, representuojantis paduota teksta
     */
    public Vector[] splitBinaryStringToVectors(String message, int vectorLength) {
        int vectorCount = message.length() / vectorLength;
        Vector[] result = new Vector[vectorCount];
        for (int i = 0; i < vectorCount; i++) {
            String vectorString = message.substring(0, vectorLength);
            int[] vectorData = convertBinaryStringToIntArray(vectorString);
            result[i] = new Vector(vectorLength, vectorData);
            message = message.substring(vectorLength);
        }
        return result;
    }

    /**
     * Iskaido dvejetaini teksta i skaiciu masyva
     * @param binaryString
     * @return integer array representation of binary string
     */
    private int[] convertBinaryStringToIntArray(String binaryString) {
        char[] characters = binaryString.toCharArray();
        int[] result = new int[characters.length];
        for (int i = 0; i < characters.length; i++) {
            result[i] = characters[i] - '0';
        }
        return result;
    }
}
