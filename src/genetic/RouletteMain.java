package genetic;

import java.util.concurrent.ThreadLocalRandom;

public class RouletteMain {

	public static void main(String[] args) {
		
		Gene[] genes = new Gene[4];
		int i=0;
		for(Gene g : genes){
			g = new Gene(ThreadLocalRandom.current().nextFloat()*10);
			genes[i] = g;
			i++;
		}
		
		for(Gene g : genes){
			System.out.println(g.score);
		}
		
		for(int j=0; j<100; j++){
			Gene newGene = RouletteSelection.selectGene(genes);
			for(Gene g : genes){
				if(g.score == newGene.score){
					g.picked();
				}
			}
		}
		
		for(Gene g : genes){
			System.out.println("score:" + g.score + " picked: " + " " + g.picked);
		}
	}

}
