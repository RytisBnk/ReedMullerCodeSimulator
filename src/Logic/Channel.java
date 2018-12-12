package Logic;

import java.util.Random;

public class Channel {
    private double probability;
    private static final int upperBound = 100000;

    /**
     * @param probability - tikimybe kad ivyks klaida
     */
    public Channel(double probability) {
        this.probability = probability;
    }

    /**
     * Keicia duomenis pagal tikimybe (padaro klaidas atsistiktinai)
     * @param data - dvejetainis tekstas kuris bus siunciamas kanalu
     * @return tekstas su padarytomis klaidomis
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
