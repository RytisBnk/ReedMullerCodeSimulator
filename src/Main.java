import GUI.HomeFrame;
import Tests.AccuracyTest;

public class Main {
    public static void main(String[] args) {
        new HomeFrame();
//        System.out.println("M = 2, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(2,
//                0.0001,
//                "101",
//                10000,
//                0.0001)));
//        System.out.println("M = 3, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(3,
//                0.002,
//                "1010",
//                10000,
//                0.0001)));
//        System.out.println("M = 4, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(4,
//                0.021,
//                "10101",
//                10000,
//                0.0001)));
//        System.out.println("M = 5, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(5,
//                0.058,
//                "101010",
//                10000,
//                0.001)));
//        System.out.println("M = 6, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(6,
//                0.139,
//                "1010101",
//                10000,
//                0.001)));
//        System.out.println("M = 7, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(7,
//                0.207,
//                "10101011",
//                10000,
//                0.001)));
//        System.out.println("M = 8, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(8,
//                0.271,
//                "101010110",
//                1000,
//                0.01)));
//        System.out.println("M = 9, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(9,
//                0.330,
//                "1010101101",
//                1000,
//                0.01)));
//        System.out.println("M = 10, maximum error probability = "
//                + String.format("%.5f", getMaxProbability(10,
//                0.38,
//                "10101011011",
//                1000,
//                0.01)));
    }

    private static double getMaxProbability(int m, double startingProbability, String vector, int iterations, double probabilityHop) {
        AccuracyTest test = new AccuracyTest(m);
        double probability = startingProbability + probabilityHop;
        double accuracy = test.testAccuraccy(vector, startingProbability, iterations);
        while (accuracy > 0.99) {
            probability += probabilityHop;
            accuracy = test.testAccuraccy(vector, probability, iterations);
        }
        return probability;
    }
}
