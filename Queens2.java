import java.lang.Math;
import java.util.*;


public class Queens2
{
    private static int boardSize = 10;

    public static double[] rank(Integer values[])
    {
        double rank[] = new double[values.length];
        double rank_pos= 0.0;
        Integer[] temp = new Integer[values.length];
        for(int i = 0; i< values.length; i++)
        {
            temp[i] = values[i];
        }
        Arrays.sort(temp);

        for(int i = 0; i< temp.length; i++)
        {
            for(int j = 0; j< temp.length;j++)
            {
                if (temp[i]==values[j]&& rank[j]==0.0)
                {
                    rank[j] = rank_pos;
                    rank_pos = rank_pos+1.0;
                }
                
            }
         }
        Integer c = 0;
        for(int i = 0; i< values.length; i++)
         {
             c = values[i];
             int [] pos = new int[100];
             int count =0;
             for(int j = 0; j< values.length;j++)
             {
                if(c == values[j])
                {
                    pos[count] = j;
                    count++; 
                }
            }

            if (count >1)
            {
                double sum =0.0;
                for (int m= 0; m<count;m++)
                {
                    int location = pos[m];
                    sum = sum + rank[location];
                }
                sum = sum /count;
                for (int m= 0; m<count;m++)
                {
                    int location = pos[m];
                    rank[location] = sum;
                }
            }
        }
        
        return rank;
    }
    
    public static double linearRankingProb(double rank, double s, int populationSize)
    {
        double r =rank;
        double population= (double)populationSize;
        double first = (2.0-s)/population;
        double second = (2.0*r*(s-1))/(population*(population-1.0));
        double probability = first + second;
        return probability;
    }

    public static Integer[][] linearRankingSelect(Integer[][] population, double s)
    {
        // The first three parts of this method have been written for you...
        // But the fourth part - selecting the two parents - is up to you!
        
        Integer [][] parents = new Integer [2][boardSize]; // will hold the selected parents
        int popSize = population.length;
        
        // 1. determine the fitness of each member of the population
        Integer [] fitness = getFitnesses(population);
        
        // 2. hence determine the ranking of each member by its fitness
        double [] rank = rank(fitness);
        
        // 3. determine the cumulative probability of selecting each member, using the linear ranking formula
        double [] cumulative = new double [popSize];
        cumulative[0] = linearRankingProb(rank[0], s, popSize);
        for (int index = 1; index < popSize; index ++)
        {
            cumulative[index] = cumulative[index-1] + linearRankingProb(rank[index], s, popSize);
        }
        
        // 4. finally, select two parents, based on the cumulative probabilities
        int first;
        int second;
        double rand_0 = (Math.random()*1);
        first  = 0;
        while(cumulative[first]< rand_0)
        {
            first++;
        }
        second = first;

        while (second == first)
        {
            double rand_1 = (Math.random()*1);
            second = 0;
            while(cumulative[second]<rand_1)
            {
                second++;
            }
        }
        parents[0] = population[first];
        parents[1] = population[second];
        
        return parents;
    }
    
    public static Integer[][] survivorSelection(Integer[][] population, Integer [][] children)
    {
        
        Integer [][] newPop = new Integer [population.length][boardSize];
        int new_length = population.length + children.length;
        Integer [][] pop = new Integer [new_length][boardSize];
        for (int i = 0; i< new_length; i++)
        {
            for (int j=0; j< boardSize; j++)
            {
                if(i<10)
                {
                    pop[i][j] = population[i][j];
                }
                else if (i>=10)                
                {
                    int x = i-10;
                    pop[i][j] = children[x][j]; 
                }
            }
        }

        int [] fit = new int[new_length];
        for (int i =0; i< new_length; i++)
        {
            fit[i] = Queens.measureFitness(pop[i]);
        }
        Arrays.sort(fit);
        int x= fit.length-1;
        int [] fit_newPop = new int[population.length];
        for(int i = 0; i<fit_newPop.length; i++)
        {
            fit_newPop[i] = fit[x];
            if(i>0)
            {
                int flag = 0;
                for(int j = i; j>=0; j--)
                {
                    if(fit_newPop[i]== fit_newPop[j])
                    {
                        flag++;
                    }
                }
                if (flag>1)
                {
                    i--;
                }
            }
            x--;
        }

        int count = 0;
        int pos_temp = 0;
        while(count<population.length)
        {
            int fit_temp = fit_newPop[pos_temp] ;
            for (int i =0; i<new_length; i++)
            {
                int temp = Queens.measureFitness(pop[i]);
                if(temp == fit_temp&& count<population.length)
                {
                    for (int j =0; j<boardSize; j++)
                    {
                        newPop[count][j] = pop[i][j];
                    }
                    count++;
                }
            }
            pos_temp++;
        }
        return newPop;
    }

    public static int geneticDiversity(Integer[][] population)
    {
        int uniqueTypes = 10;

        for(int i = 0; i< boardSize; i++)
        {
            int c= 0;
            int type = 0;
 
            while (c<boardSize)
            {
                int count = 0;

                for(int j = 0; j< boardSize;j++)
                {
                    if ((population[i][j] == population[c][j])&& c>=i)
                    {
                        count++;

                    }
                }

                if(count ==boardSize)
                {
                    type++;
                }
                c++;
            }
            uniqueTypes = uniqueTypes -(type-1);
         }
        
        return uniqueTypes;
    }
    
    public static Integer[] inversionMutate(Integer[] genotype, double p)
    {
        Integer [] temp =  new Integer[boardSize];
        int gene1, gene2;
        gene1 = (int)(Math.random()*10);
        gene2 = (int)(Math.random()*10);
        int x= 0;
        if (gene2< gene1)
        {
            x= gene2;
            gene2 = gene1;
            gene1 = x;
        }
        SplittableRandom random = new SplittableRandom();
        boolean probability = random.nextInt(0,1)<= p;
        if(probability)
        {
            if (gene1 != gene2)
            {
                for (int i = 0; i< boardSize; i++)
                {
                    if (i>=gene1 && i<=gene2)
                    {
                        temp[i] =genotype[i];
                    }
                }
                int count = gene2;
                for (int j = gene1 ; j<=gene2 ; j++)
                {
                    genotype[j] = temp[count];
                    count--;
                }
            }
        }

        return genotype;
    }

    private static Integer[] getFitnesses(Integer [][] population)
    {
        int popSize = population.length;
        Integer [] fitness = new Integer [popSize];
        
        for (int index = 0; index < popSize; index ++)
        {
            fitness[index] = Queens.measureFitness(population[index]);
        }
        
        return fitness;
    }
}
