package miniTareaGA;

import java.util.concurrent.ThreadLocalRandom;

public class Individual {

	double fitnessValue;
	int[] genes;
	
	public Individual(int numberGenes){
		this.fitnessValue = 0;
		this.genes = new int[numberGenes];
	}
	
	public static Individual[] generateIndividuals(int population, int numberGenes, int topLimit){
		Individual[] ind = new Individual[population];
		//initialize individuals
		for(int i=0; i<population; i++){
			ind[i] = new Individual(numberGenes);
		}
		//initialize genes for each individual, random ints [0,9]
		for(Individual i : ind){
			for(int g=0; g<numberGenes; g++){
				i.genes[g] = ThreadLocalRandom.current().nextInt(1, topLimit);
			}
		}
		return ind;
	}
}
