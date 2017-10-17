package genetic;

import java.util.List;

public class RouletteSelection {

	public static Gene selectGene(Gene[] genes){
	    float totalScore = 0;
	    float runningScore = 0;
	    for (Gene g : genes)
	    {
	        totalScore += g.score;
	    }

	    float rnd = (float) (Math.random() * totalScore);

	    for (Gene g : genes)
	    {   
	        if (    rnd>=runningScore &&
	                rnd<=runningScore+g.score)
	        {
	            return g;
	        }
	        runningScore+=g.score;
	    }

	    return null;
	}
	
	/*                                                                                                                                                                                                                      
	public static Gene[] selectIndividual(List<Gene[]> population){
	    float totalScore = 0;
	    float runningScore = 0;
	    for (Gene g : population)
	    {
	        totalScore += g.score;
	    }

	    float rnd = (float) (Math.random() * totalScore);

	    for (Gene g : population)
	    {   
	        if (    rnd>=runningScore &&
	                rnd<=runningScore+g.score)
	        {
	            return g;
	        }
	        runningScore+=g.score;
	    }

	    return null;
	}
	*/
}
