import java.lang.Math;
import java.util.*;


public class Queens
{
    private static int boardSize = 10;
    
    public static Integer [] randomGenotype()
    {
        Integer [] genotype = new Integer [boardSize];

        int temp;
        int duplicate;
        for (int i= 0; i<10; i++)
        {
            temp = (int)(Math.random()*10+1.0);
            if(i==0)
            {
                genotype[i] = temp;
            }
            else
            {
                duplicate = 0;
                for (int j= 0; j<i;j++)
                {
                    if (temp == genotype[j])
                    duplicate++;
                }
                if(duplicate ==0)
                genotype[i] = temp;
                else
                i--;
            }
            
        }
        return genotype;
    }     

    public static Integer[] swapMutate(Integer[] genotype, double p)
    {
    int temp;
    int gene1, gene2;
    gene1 = (int)(Math.random()*10);
    gene2 = (int)(Math.random()*10);
    SplittableRandom random = new SplittableRandom();
    boolean probability = random.nextInt(0,1)<= p;
    if(probability)
    {
    if (gene1 != gene2)
    {
        temp = genotype[gene1];
        genotype[gene1] = genotype[gene2];
        genotype[gene2] = temp;
    }
    }
    
    
    return genotype;
   }

    public static Integer[][] cutAndCrossfill(Integer[] parent0, Integer[] parent1)
    {
    Integer [][] children = new Integer [2][boardSize];
    
    for (int i =0; i<boardSize; i++)
    {
        if(i<5)
        {
            children[0][i] = parent0[i];
            children[1][i] = parent1[i];
        }
        else
        {
            children[0][i] = parent1[i];
            children[1][i] = parent0[i];
        }
    }
    // for the first child
    int count0 = 0;
    int n=boardSize;
    for (int i = 5; i < n ; i++)
    {
        int flag = 0;
        int temp0 = children[0][i];
        for (int j = 0 ; j<i; j++)
        {
            if (temp0 == children[0][j])
            {
                flag = 1;
                n--;
            }

        }
        if (flag==1)
        {
            count0++;
            for(int x=i+1; x< boardSize; x++)
            {
                children[0][x-1] = children[0][x];
            }
            flag = 0;
        }
    }
    count0 = 5-count0;
    int pos = (boardSize- count0) +1;

        for (int i = 0; i< 5; i++)
        {
            int flag = 0;
            int temp = 0;
            for (int j = 0; j<boardSize-count0; j++)
            {
                if(parent1[i]== children[0][j])
                {
                    temp = parent1[i];
                    flag = 1;
                }
            }
            if (flag==0)
            {
                children[0][pos] = parent1[i];
                pos++;
            }
        }
    
    // for the second child
    int count1 = 0;
    int n1=boardSize;
    for (int i = 5; i < n1 ; i++)
    {
        int flag = 0;
        int temp1 = children[1][i];
        for (int j = 0 ; j<i; j++)
        {
            if (temp1 == children[1][j])
            {
                flag = 1;
                n1--;
            }

        }
        if (flag==1)
        {
            count1++;
            for(int x=i+1; x< boardSize; x++)
            {
                children[1][x-1] = children[1][x];
            }
            flag = 0;
        }
    }
    count1 = 5-count1;
    int pos1 = (boardSize- count1) +1;

        for (int i = 0; i< 5; i++)
        {
            int flag = 0;
            int temp = 0;
            for (int j = 0; j<boardSize-count1; j++)
            {
                if(parent0[i]== children[1][j])
                {
                    temp = parent0[i];
                    flag = 1;
                }
            }
            if (flag==0)
            {
                children[1][pos1] = parent0[i];
                pos1++;
            }
        }
    
    
    return children;
   }


    public static int measureFitness(Integer [] genotype)
    {

    int count= 0;
    int [][] board = new int[10][10];
    for (int i = 0; i<10; i ++)
    {
        for (int j = 0; j<10 ; j++)
        {
            if (genotype[i] == (j+1))
            {
                board [i][j] = genotype[i];
            }
            else
            {
                board [i][j] = 0;
            }
        }
    }
    // the outer loop to iterate over the geno type 
    for (int i = 0; i<10; i ++)
    {
        for (int j = 0; j<10 ; j++)
        {
            int temp_x = 0;
            int temp_y = 0;
            if (board[i][j]!=0)
            {
                temp_x = i;
                temp_y = j;

                //loop for the upper left diagonal
                while(i >0 && j>0)
                {

                    i--;
                    j--;
                    if(i>=0&& j<10)
                    {
                    if (board[i][j]!= 0)
                    {
                        count++;
                    }
                }
                }
                i = temp_x;
                j = temp_y;
                // loop for upper right daigonal
                while (i>0 && j<10 )
                {
                    i--;
                    j++;
                    if(i>=0&& j<10)
                    {
                    if (board[i][j]!= 0)
                    {
                        count++;
                    }
                }
                }
                i = temp_x;
                j = temp_y;
                // loop for bottom left diagonal
                while (i<10 && j>0 )
                {
                    i++;
                    j--;
                    if(i<10 && j>=0)
                    {
                    if (board[i][j]!= 0)
                    {
                        count++;
                    }
                }
                }
                i = temp_x;
                j = temp_y;
                // loop for bottom right diagonal
                while (i<10 && j<10)
                {
                    i++;
                    j++;
                    if(i<10&&j<10)
                    {
                    if (board[i][j]!= 0)
                    {
                        count++;
                    }
                }
                }
                i = temp_x;
                j = temp_y;
            }
        }
    }
    count = count/2;
    int fitness = 45 - count;
    return fitness;
   }

}

