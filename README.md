# 10-QueensGA
This is an extension of the 10 Queens Code. It completes all the Genetic Algortihms part 
left in the 10-Queens Code. There are six parts to this algorithm:
1. Rank: It ranks all the genotypes(positions where the queens are placed in a single configuration)
2. Linear Ranking Probility: this method calculates P[i], the linear ranking probability of selecting a genotype with rank i from a population
it takes as input a rank, s (the stochastic pressure parameter) and the population size (also known as μ) and calculates the rank using the formula

P[i](rank) = (2-s)/population + 2*rank*(s-1)/population*(population-1)

3. Linear Ranking Select: this method will pick two parents using the Roulette Wheel Selection over the array of rankings.
4. Survivor Selection : This method provides the fittest individual from the pool of parent population and children population
of the size of the current population. The survivability is based upon the fitness of individual genotypes.
5. Genotype Diversity : This method provides with different types of species present in the population
6. Inverse Mutation : This method enables to mutate the genes amongst themselves using Inverses Mutation with a given probability p.
