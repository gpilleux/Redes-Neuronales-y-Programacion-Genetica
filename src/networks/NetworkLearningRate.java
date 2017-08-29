package networks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NetworkLearningRate {

	public static void main(String[] args) {
		
		NeuralNetwork nw = new NeuralNetwork(2, 2);
		
		double learningRate = 0.1;
		
		List<List<Double>> combinations = new ArrayList<List<Double>>();
		List<Double> comb1 = new ArrayList<Double>(); // 0,0
		List<Double> comb2 = new ArrayList<Double>(); // 0,1
		List<Double> comb3 = new ArrayList<Double>(); // 1,0
		List<Double> comb4 = new ArrayList<Double>(); // 1,1
		
		comb1.add((double) 0); comb1.add((double) 0);
		comb2.add((double) 0); comb2.add((double) 1);
		comb3.add((double) 1); comb3.add((double) 0);
		comb4.add((double) 1); comb4.add((double) 1);
		
		combinations.add(comb1); combinations.add(comb2);
		combinations.add(comb3); combinations.add(comb4);
		
		//list of frecuency of chosen combination
		List<Integer> chosen = new ArrayList<Integer>();
		chosen.add(0); chosen.add(0); chosen.add(0); chosen.add(0);
		
		//list of expected // changing values here we can teach the network
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0); expected.add(1); expected.add(1); expected.add(0);

		int trials = 100000;
		
		int countAssertsBefore = 0;
		for(int i=0; i<trials; i++){
			int randComb = ThreadLocalRandom.current().nextInt(0, 4);
			nw.feedNetwork(combinations.get(randComb));
			double compareTo = 0;
			if(nw.getOutput() > 0.5)
				compareTo = 1;
			if(compareTo == expected.get(randComb))
				countAssertsBefore++;
			nw.clearInputList();
		}
		System.out.println(1.0*countAssertsBefore/trials);
		
		//training camp
		
		for(int i=0; i<trials; i++){
			nw.feedNetwork(combinations.get(0));
			nw.backwardPropagation(expected.get(0));
			nw.updateNetwork(learningRate);
			chosen.set(0, chosen.get(0)+1);
			
			nw.feedNetwork(combinations.get(1));
			nw.backwardPropagation(expected.get(1));
			nw.updateNetwork(learningRate);
			chosen.set(1, chosen.get(1)+1);
			
			nw.feedNetwork(combinations.get(2));
			nw.backwardPropagation(expected.get(2));
			nw.updateNetwork(learningRate);
			chosen.set(2, chosen.get(2)+1);
			
			nw.feedNetwork(combinations.get(3));
			nw.backwardPropagation(expected.get(3));
			nw.updateNetwork(learningRate);
			chosen.set(3, chosen.get(3)+1);
		}
		
		int countAssertsAfter = 0;
		for(int i=0; i<trials; i++){
			int randComb = ThreadLocalRandom.current().nextInt(0, 4);
			nw.feedNetwork(combinations.get(randComb));
			double compareTo = 0;
			if(nw.getOutput() > 0.5)
				compareTo = 1;
			if(compareTo == expected.get(randComb))
				countAssertsAfter++;
			nw.clearInputList();
		}
		System.out.println(1.0*countAssertsAfter/trials);
		
		
		//check 'equally' distributed learning points given
		System.out.println(chosen);
	}

}
