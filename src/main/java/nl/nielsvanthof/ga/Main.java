package nl.nielsvanthof.ga;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double crossOverRate;
        double mutationRate;
        int populationSize;
        int iterationNumber;
        boolean elitism;
        char isElitism;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter crossover Rate: ");
        crossOverRate = scanner.nextDouble();

        System.out.println("Enter mutation Rate: ");
        mutationRate = scanner.nextDouble();

        System.out.println("Enter population size: ");
        populationSize = scanner.nextInt();

        System.out.println("Enter iteration number: ");
        iterationNumber = scanner.nextInt();

        System.out.println("Should there be use of elitism? (Y/N) ");
        scanner.nextLine();

        isElitism = scanner.nextLine().charAt(0);

        if (isElitism == 'Y' || isElitism == 'y') {
            elitism = true;
        } else {
            elitism = false;
        }

        GaSimulator g = new GaSimulator(crossOverRate, mutationRate, elitism, populationSize, iterationNumber);
        g.simulate();

        scanner.close();
    }
}