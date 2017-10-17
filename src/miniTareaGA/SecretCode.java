package miniTareaGA;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SecretCode {
	//List<Individual> population;
	Individual[] population;
	int[] secretCode;
	
	Individual[] newPopulation;
	
	boolean verifiedCode;
	int[] guessedCode;
	
	public SecretCode(int[] secretCode, int numbPopulation, int topLimit){
		population = Individual.generateIndividuals(numbPopulation, secretCode.length, topLimit);
		this.secretCode = secretCode;
		this.newPopulation = new Individual[numbPopulation];
		verifiedCode = false;
	}
	
	//computes the fitness for each individual as: number of right numbers / total of numbers
	public void evaluateFitness(){
		double totalFitness = 0;
		for(Individual ind : population){
			int tempoFitness = countSameGenes(ind.genes);
			totalFitness += tempoFitness;
			ind.fitnessValue = tempoFitness;
			
			//System.out.println("countsameGenes: " + tempoFitness);
		}
		//normalize: divide each individuals fitness by the total of fitness
		if(totalFitness > 0){
			for(Individual ind : population){
				ind.fitnessValue = ind.fitnessValue/totalFitness;
			}
		}
	}
	
	//counts how many genes are the same from the secret code to each individual
	public int countSameGenes(int[] individualGenes){
		int same = 0;
		for(int i=0; i<individualGenes.length; i++){
			if(this.secretCode[i] == individualGenes[i])
				same++;
		}
		return same;
	}
	
	public Individual selection(){
		Arrays.sort(this.population, (a,b) -> a.fitnessValue > b.fitnessValue ? -1 : 1);
		/*
		System.out.println("Population sorted by fitness: ");/*
		for(Individual i : population)
			System.out.print(i.fitnessValue + " ");
		
		for(Individual in : this.population){
			for(int c : in.genes)
				System.out.print(c + " ");
			System.out.println("fitness: " + in.fitnessValue);
		}
		//System.out.println();
		*/
		double[] accFitness = accumulatedFitness();
		/*
		System.out.println();
		System.out.println("Accumulated Fitness: ");
		
		for(double a : accFitness)
			System.out.print(a + " ");
		System.out.println();
		*/
		double R = ThreadLocalRandom.current().nextDouble();
		
		//System.out.println("R: " + R);
		
		int index = 0;
		// the last index whose accumulated normalized value is smaller than R is index-1
		for(; index<accFitness.length-1; index++){
			if(R < accFitness[index+1] && R >= accFitness[index])
				break;
		}
		return population[index];//index > 0 ? population[index-1] : population[index];
	}
	
	public double[] accumulatedFitness(){
		Arrays.sort(this.population, (a,b) -> a.fitnessValue > b.fitnessValue ? -1 : 1);
		double[] accFitness = new double[this.population.length];
		accFitness[0] = population[0].fitnessValue;
		for(int i=1; i<this.population.length; i++){
			accFitness[i] = accFitness[i-1] +  population[i].fitnessValue;
		}
		return accFitness;
	}
	
	public void reproduction(){
		//repeat until new population number is met (same as before's population)
		for(int i=0; i<this.population.length; i++){
			Individual p1 = this.selection();
			Individual p2 = this.selection();
			/*
			System.out.println("Selected1 ");
			for(int g : p1.genes)
				System.out.print(g + " ");
			System.out.println("Selected2 ");
			for(int g : p2.genes)
				System.out.print(g + " ");
			*/
			Individual offSpring = new Individual(this.secretCode.length);
			int crossOverSpot = (int)(this.secretCode.length * ThreadLocalRandom.current().nextDouble(1));
			
			//System.out.println("crossOverSpot: " + crossOverSpot);
			
			//copy first random spotted genes into offspring
			for(int firstGenes=0; firstGenes<crossOverSpot; firstGenes++)
				offSpring.genes[firstGenes] = p1.genes[firstGenes];
			//copy second random spotted gened into offspring
			for(int secondGenes=crossOverSpot; secondGenes<this.secretCode.length; secondGenes++)
				offSpring.genes[secondGenes] = p2.genes[secondGenes];
			//mutate
			double randomMutate = ThreadLocalRandom.current().nextDouble(1);
			if(randomMutate < 0.1)
				this.mutate(offSpring);
			//add the offspring to the new population
			this.newPopulation[i] = offSpring;
		}
		//change previous population with new population IF AND ONLY IF the new population are't all equal
		if(!this.everyEqualOffSpring())
			this.rotatePopulation();
		this.verifyCode();
		/*
		System.out.println("Population sorted by fitness: ");
		for(Individual in : this.population){
			for(int c : in.genes)
				System.out.print(c + " ");
			System.out.println("fitness: " + in.fitnessValue);
		}
		*/
	}
	
	public void mutate(Individual ind){
		int firstGene = ThreadLocalRandom.current().nextInt(0, ind.genes.length);
		int secondGene = ThreadLocalRandom.current().nextInt(0, ind.genes.length);
		int geneAux = ind.genes[firstGene];
		ind.genes[firstGene] = ind.genes[secondGene];
		ind.genes[secondGene] = geneAux;
	}
	
	//after population rotation, needs to be fitness evaluated AGAIN!
	public void rotatePopulation(){
		this.population = this.newPopulation;
		this.newPopulation = new Individual[population.length];
		this.evaluateFitness();
	}
	
	//checks if the code from the individual is the same as the code of the user
	public boolean isSameCode(int[] newCode){
		int sameCode = 0;
		for(int i=0; i<newCode.length; i++){
			if(this.secretCode[i] == newCode[i])
				sameCode++;
		}
		return sameCode == secretCode.length;
	}
	
	//checks if any of the populations "genes" is the same as the user's code
	public void verifyCode(){
		for(Individual i : this.population){
			if(isSameCode(i.genes)){
				this.guessedCode = i.genes;
				this.verifiedCode = true;
				break;
			}
		}
	}
	
	//checks if members of the population are all the same
	public boolean everyEqualComb(){
		int countSameCombs = 0;
		int[] firstComb = this.population[0].genes;
		for(Individual ind : population){
			if(isSameComb(firstComb, ind.genes))
				countSameCombs++;
		}
		return countSameCombs == population.length;
	}
	
	//checks if members of the NEW population are all the same
	public boolean everyEqualOffSpring(){
		int countSameCombs = 0;
		int[] firstComb = this.newPopulation[0].genes;
		for(Individual ind : this.newPopulation){
			if(isSameComb(firstComb, ind.genes))
				countSameCombs++;
		}
		return countSameCombs == this.newPopulation.length;
	}
	
	//checks if the int[] are the same
	public boolean isSameComb(int[] firstComb, int[] secondComb){
		int sameCode = 0;
		for(int i=0; i<firstComb.length; i++){
			if(firstComb[i] == secondComb[i])
				sameCode++;
		}
		return sameCode == secretCode.length;
	}
	
}
