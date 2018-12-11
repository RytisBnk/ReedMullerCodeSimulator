package Logic;

import java.util.Random;

public class Channel {
    private double probability;
    private static final int upperBound = 100000;

    /**
     * @param probability - probability that any character sent through a channel will be altered (chance of error)
     */
    public Channel(double probability) {
        this.probability = probability;
    }

    /**
     * @param data - binary string that will be sent through a channel
     * @return modified data string, containing errors based on probability of failure
     */
    public String sendData(String data) {
        Random generator = new Random();
        char[] characters = data.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomNumber = generator.nextInt(upperBound) + 1;
            if (randomNumber < upperBound * probability) {
                characters[i] = (characters[i] == '0') ? '1' : '0';
            }
        }
        return new String(characters);
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
