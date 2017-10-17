package genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Gene {
	
	float score;
	int picked;
	
	public Gene(float aScore){
		this.score = aScore;
		this.picked = 0;
	}

	public void picked() { this.picked++; }
	
	public static Gene[] generateGenes(int numberGenes){
		Gene[] genes = new Gene[numberGenes];
		
		int i=0;
		for(Gene g : genes){
			genes[i] = new Gene(ThreadLocalRandom.current().nextInt(0, 5)); //(float) cast
			i++;
		}
		
		return genes;
	}
	
	/*
	 * int populants = number of people in the population
	 * int numberGenes = number of genes for each person in the population
	 */
	public static List<Gene[]> generatePopulation(int populants, int numberGenes){
		List<Gene[]> population = new ArrayList<Gene[]>();
		
		for(int i=0; i<populants; i++){
			population.add(generateGenes(numberGenes));
		}
		
		return population;
	}
	
	public static List<Gene[]> mutate(List<Gene[]> af){
		List<Gene[]> newPopulation = new ArrayList<Gene[]>();

		return newPopulation;
	}
}

