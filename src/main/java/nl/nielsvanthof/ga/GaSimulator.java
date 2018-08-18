package nl.nielsvanthof.ga;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class GaSimulator {
    private double crossOverRate;
    private double mutationRate;
    private boolean elitism;
    private int populationSize;
    private int iterationNumber;

    GaSimulator(double crossOverRate, double mutationRate, boolean elitism, int populationSize, int iterationNumber) {
        this.crossOverRate = crossOverRate;
        this.mutationRate = mutationRate;
        this.elitism = elitism;
        this.populationSize = populationSize;
        this.iterationNumber = iterationNumber;
    }

    void simulate() {
        List<Individual> initialPopulation = new ArrayList<>();
        List<Individual> population = new ArrayList<>();

        // Override compare method so individuals can be compared by their function values
        Comparator<Individual> c = (o1, o2) -> (int)(o2.getFunctionValue() - o1.getFunctionValue());

        // Create initial population
        for(int i = 0; i < populationSize; i++) {
            Individual individual = new Individual();
            initialPopulation.add(individual);
        }

        // Sort initial population
        initialPopulation.sort(c);

        // Set population
        if(elitism) {
            // Set some individuals from initial population into new population
            double elitePercentage = 40;
            int newPopulationSize = (int)(populationSize * elitePercentage / 100);

            for (int i = 0; i < populationSize - newPopulationSize; i++) {
                population.add(initialPopulation.get(i));
            }

        } else {
            population = initialPopulation;
        }

        populationSize = population.size();

        // Perform needed number of iterations
        for(int iteration = 0; iteration < iterationNumber; iteration++) {
            // Perform crossover of two random individuals from population
            Individual[] twoParents = selectTwoParents(population);
            Individual crossoverChild = twoParents[0].crossover(twoParents[1], crossOverRate);

            // If child fits task better than the worst fitting individual in population, change that individual to new child
            if(crossoverChild.getFunctionValue() > population.get(populationSize - 1).getFunctionValue()) {
                population.remove(populationSize - 1);
                population.add(crossoverChild);

                // Keep the population sorted
                population.sort(c);
            }

            // Perform mutation of random individual in population
            Random r = new Random();
            int mutateIndex = r.nextInt(populationSize);

            Individual mutateIndividual = population.get(mutateIndex);
            Individual mutatedIndividual = mutateIndividual.mutation(mutationRate);

            // If mutated individual fits task better than the worst fitting individual in population, change that individual to mutated one
            if(mutatedIndividual.getFunctionValue() > population.get(populationSize - 1).getFunctionValue()) {
                population.remove(populationSize - 1);
                population.add(mutatedIndividual);

                population.sort(c);
            }
        }

        // Display results
        System.out.println("-------Printing results-------");
        System.out.println("Average fitness of last population: " + calculateAverageFitness(population));
        System.out.println("Best fitness: " + population.get(0).getFunctionValue());

        System.out.println();
        System.out.println("Best Individual\n" + population.get(0));
    }

    // Calculate average fitness value for given population
    private double calculateAverageFitness(List<Individual> population) {
        double val = 0;

        // Calculate sum of function values for all population
        for (int i = 0; i < populationSize; i++) {
            val += population.get(i).getFunctionValue();
        }

        val /= populationSize;

        return val;
    }

    // Select two parents randomly from population
    private Individual[] selectTwoParents(List<Individual> population) {
        int size = population.size();
        Random rand = new Random();

        // Get two random individuals from the population
        int firstParentIndex = rand.nextInt(size);
        int secondParentIndex = rand.nextInt(size);

        // Make sure that individuals are different
        while (firstParentIndex == secondParentIndex) {
            secondParentIndex = rand.nextInt(size);
        }

        Individual[] twoParents = new Individual[2];

        twoParents[0] = population.get(firstParentIndex);
        twoParents[1] = population.get(secondParentIndex);

        return twoParents;
    }
}
