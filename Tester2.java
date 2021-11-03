/*
 *  DO NOT SUBMIT THIS CLASS WITH YOUR ASSIGNMENT
 *
 *  This class is only provided so that you can test your code
 *  for Assignment 3.
 *
 *  ALL your code MUST be placed in the Queens2.java class.
 *
 *  MH October 2021
 */
public class Tester2
{
    private static int boardSize = 10;
    
	public static void main(String[] args)
	{
        // TEST A: rank a set of values
        Integer [] values1 = new Integer[]{60, 20, 40, 30, 50, 10};
        Integer [] values2 = new Integer[]{70, 50, 80, 35, 50, 90};
        
        double [] ranks1 = Queens2.rank(values1);
        double [] ranks2 = Queens2.rank(values2);
        
        // the known correct rankings
        double [] correctRanks1 = new double[]{5, 1, 3, 2, 4, 0};
        double [] correctRanks2 = new double[]{3, 1.5, 4, 0, 1.5, 5};
        
        System.out.println("\nA. Testing rank a set of values");
        System.out.print("ranks1:  Correct: "); printArray(correctRanks1, 1);
        System.out.print("        Computed: "); printArray(ranks1, 1);
        System.out.print("\nranks2:  Correct: "); printArray(correctRanks2, 1);
        System.out.print("        Computed: "); printArray(ranks2, 1);
        
        // TEST B: calculate linear ranking probability, using s = 1.5
        
        // the known correct probabilities to 4 d.p. for each value in correctRanks1 from above
        double [] correctProbabilities = new double[]{0.25, 0.1167, 0.1833, 0.15, 0.2167,0.0833};
         
        // calculate the probability for each value
        double [] calculatedProbabilities = new double[6];
        for (int index = 0; index < 6; index ++)
        {
            calculatedProbabilities[index] = Queens2.linearRankingProb(correctRanks1[index], 1.5, 6);
        }
        
        System.out.println("\nB. Testing calculate linear ranking probability");
        System.out.print("Correct:    "); printArray(correctProbabilities, 4);
        System.out.print("Calculated: "); printArray(calculatedProbabilities, 4);
        
        
        // TEST C: perform linear-ranking parent selection
        // create a population to test
        int popSize = 5;
        Integer [][] population1 = new Integer [popSize][boardSize];
        population1 [0] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        population1 [1] = new Integer[]{ 9, 5, 6, 10, 8, 1, 3, 2, 4, 7 };
        population1 [2] = new Integer[]{ 9, 4, 3, 1, 2, 5, 10, 7, 8, 6 };
        population1 [3] = new Integer[]{ 7, 8, 9, 1, 10, 2, 3, 4, 5, 6 };
        population1 [4] = new Integer[]{ 10, 6, 4, 2, 8, 5, 9, 1, 3, 7 };
        
        // the known fitnesses for this population
        Integer [] fitnesses = new Integer [popSize];
        for (int index = 0; index < popSize; index ++)
        {
            fitnesses [index] = Queens.measureFitness(population1 [index]);
        }
        
        // expected frequencies after 10000 runs of linear-ranking parent selection, when s = 1.5
        // in reality the actual number of times each is chosen will vary slightly
        Integer [] expectedFrequency = new Integer [popSize];
        expectedFrequency [0] = 2200; // based on P[selected 1st] + P[selected 2nd]
        expectedFrequency [1] = 4100;
        expectedFrequency [2] = 4900;
        expectedFrequency [3] = 3200;
        expectedFrequency [4] = 5600;
        
        Integer [][] parents = new Integer [2][boardSize];
        int [] tally = new int [population1.length];
        
        double s = 1.5; // the parameter for linear ranking weightings
        System.out.println("\nC. Testing Linear Ranking Parent Selection (s = 1.5, 10000 runs):");
        System.out.println("Number of times each parent was selected:");
        for (int count = 0; count < 10000; count ++)
        {
            parents = Queens2.linearRankingSelect(population1, s);
            int fitness0 = Queens.measureFitness(parents[0]);
            int fitness1 = Queens.measureFitness(parents[1]);
            for (int index = 0; index < population1.length; index ++)
            {
                if (fitness0 == fitnesses[index]) { tally[index] ++; }
                if (fitness1 == fitnesses[index]) { tally[index] ++; }
            }
        }
        // should see actual frequencies close to expected frequencies
        for (int index = 0; index < population1.length; index ++)
        {
            System.out.println(index + ". rough expectation: " + expectedFrequency[index] + ", actual: " + tally[index] );
        }
        
        // TEST D: perform (μ + λ) survivor selection (replace population with best children)
        System.out.println("\nD. Testing (μ + λ) Survivor Selection:");
        // Initialize population
        popSize = 10;
        System.out.println("Original Population:");
        Integer [][] population2 = createTestPopulation(popSize);
        
        // create random population of children
        System.out.println("\n Children:");
        Integer [][] children = createTestPopulation(popSize*2);
        
        population2 = Queens2.survivorSelection(population2, children);
        
        System.out.println("\nFittest 10 individuals form the new population:");
        for (int index = 0; index < 10; index ++)
        {
            System.out.print(index + ": Fitness: " + Queens.measureFitness(population2[index]) + ", Genotype: ");
            printGenotype(population2[index]);
        }
        
        // TEST E: measure genetic diversity
        // create a new population to test
        popSize = 10;
        Integer [][] population3 = new Integer [popSize][boardSize];
        population3 [0] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        population3 [1] = new Integer[]{ 9, 5, 6, 10, 8, 7, 1, 3, 2, 4 };
        population3 [2] = new Integer[]{ 9, 4, 3, 1, 2, 5, 10, 7, 8, 6 };
        population3 [3] = new Integer[]{ 7, 8, 9, 1, 10, 2, 3, 4, 5, 6 };
        population3 [4] = new Integer[]{ 10, 6, 4, 2, 8, 5, 9, 1, 3, 7 };
        population3 [5] = new Integer[]{ 3, 2, 7, 4, 10, 1, 8, 9, 6, 5 };
        population3 [6] = new Integer[]{ 10, 9, 8, 6, 7, 2, 3, 4, 1, 5 };
        population3 [7] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        population3 [8] = new Integer[]{ 9, 5, 6, 10, 8, 7, 1, 3, 2, 4 };
        population3 [9] = new Integer[]{ 9, 4, 3, 1, 2, 5, 10, 7, 8, 6 };
        
        int actual = 7;
        int returned = Queens2.geneticDiversity(population3);
        
        System.out.println("\nE. Testing Genetic Diversity:");
        System.out.println("Number of different genotypes present:");
        System.out.println("Correct: " + actual);
        System.out.println("Computed: " + returned);
        
        // TEST F: perform inversion mutation on the population
        // create a new population to test
        popSize = 5;
        Integer [][] population4 = new Integer [popSize][boardSize];
        population4 [0] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        population4 [1] = new Integer[]{ 9, 5, 6, 10, 8, 7, 1, 3, 2, 4 };
        population4 [2] = new Integer[]{ 9, 4, 3, 1, 2, 5, 10, 7, 8, 6 };
        population4 [3] = new Integer[]{ 7, 8, 9, 1, 10, 2, 3, 4, 5, 6 };
        population4 [4] = new Integer[]{ 10, 6, 4, 2, 8, 5, 9, 1, 3, 7 };
        
        System.out.println("\nF. Testing Inversion mutation: (80% rate) ");
        for (int index = 0; index < popSize; index ++)
        {
            System.out.print(index + ". Before: ");
            printArray( population4[index]);
            population1 [index] = Queens2.inversionMutate(population4[index], 0.8);
            System.out.print("   After:  ");
            printArray( population1[index]);
            System.out.println();
        }
        
        System.out.println("END OF TESTING\n\n");
	}
    
    // worker method prints the contents of an integer array to console
    public static void printArray(Integer[] array)
    {
        for (int index = 0; index < array.length; index ++)
        {
            System.out.print(" " + array[index]);
        }
        System.out.println();
    }
    
    // worker method prints the contents of a double array to console (to 4 d.p.)
    public static void printArray(double[] array, int precision)
    {
        for (int index = 0; index < array.length; index ++)
        {
            System.out.print(" ");
            System.out.printf("%."+precision+"f", array[index]);
        }
        System.out.println();
    }
    
    // worker method creates a randomly seeded population of the required size
    public static Integer[][] createTestPopulation(int popSize)
    {
        Integer[][] testPop = new Integer[popSize][boardSize];
        
        for (int index = 0; index < popSize; index ++)
        {
            testPop [index] = Queens.randomGenotype();
            System.out.print(index + ": Fitness: " + Queens.measureFitness(testPop[index]) + ", Genotype: ");
            printArray(testPop[index]);
        }
        
        return testPop;
    }
    
    // outputs a genotype to console
    public static void printGenotype(Integer[] genotype)
    {
        for (int index = 0; index < 10; index ++)
        {
            System.out.print(" " + genotype[index]);
        }
        System.out.println();
    }
}
