package nl.nielsvanthof.ga;

import java.util.Random;

public class Individual {
    private String binaryPresentation;

    private Individual(String binaryPresentation) {
        this.binaryPresentation = binaryPresentation;
    }

    Individual() {
        // Generate random binary string
        StringBuilder binaryString = new StringBuilder();

        for(int i = 0; i < 5; i++)
        {
            if(Math.random() > 0.5) {
                binaryString.append("1");
            } else {
                binaryString.append("0");
            }
        }

        this.binaryPresentation = binaryString.toString();
    }

    // Mutation operator which flips a 0/1 bit
    Individual mutation(double mutationRate) {
        String mutatedString;

        // If mutation rate doesn't allow to mutate, return first parent
        if(Math.random() >= mutationRate) {
            return new Individual(binaryPresentation);
        }

        // Choose random place in the string to perform a mutation at that place
        Random r = new Random();
        int index = r.nextInt(binaryPresentation.length());

        // Switch chosen bit
        StringBuilder mutatedStringBuilder = new StringBuilder(binaryPresentation);
        if(binaryPresentation.charAt(index) == '0') {
            mutatedStringBuilder.setCharAt(index, '1');
            mutatedString = mutatedStringBuilder.toString();
        } else {
            mutatedStringBuilder.setCharAt(index, '0');
            mutatedString = mutatedStringBuilder.toString();
        }

        return new Individual(mutatedString);
    }

    // Crossover two parents
    Individual crossover(Individual parent, double crossOverRate) {
        // If crossover rate doesn't allow to do a crossover, return first parent
        if(Math.random() >= crossOverRate) {
            return new Individual(binaryPresentation);
        }

        Random random = new Random();

        // Select random place of the string where it should stop getting information from first parent and start getting from the second
        int breakIndex = 1 + random.nextInt(binaryPresentation.length() - 1);

        // Set child string based on strings of parents
        String childString = binaryPresentation.substring(0, breakIndex) + parent.getBinaryPresentation().substring(breakIndex);

        return new Individual(childString);
    }

    // Get function value with x equal to decimal presentation of individuals binary
    double getFunctionValue() {
        double decimalValue = getDecimalValue();
        return f(decimalValue);
    }

    private int getDecimalValue() {
        return Integer.parseInt(binaryPresentation, 2);
    }

    private String getBinaryPresentation() {
        return binaryPresentation;
    }

    // Function for maximization
    private static double f(double x) {
        return -x * x + 7 * x;
    }

    @Override
    public String toString() {
        return "Binary representation = " + binaryPresentation + "\nFunction value = " + getFunctionValue();
    }
}
