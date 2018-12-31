package Tests;

import Logic.Channel;
import Logic.Decoder;
import Logic.Encoder;

public class AccuracyTest {
    private final Encoder encoder;
    private final Decoder decoder;

    public AccuracyTest(int m) {
        encoder = new Encoder(m);
        decoder = new Decoder(m);
    }

    public double testAccuraccy(String binaryTestMessage, double probability, int iterations) {
        Channel channel = new Channel(probability);
        int counter = 0;
        for (int i = 0; i < iterations; i++) {
            String encodedMessage = encoder.encode(binaryTestMessage);
            String receivedMessage = channel.sendData(encodedMessage);
            String decodedMessage = decoder.decode(receivedMessage);
            if (decodedMessage.equals(binaryTestMessage)) {
                counter++;
            }
        }
        return (double) counter / iterations;
    }
}
