package mains;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import networks.NeuralNetwork;

public class AndNetwork {

	public static void main(String[] args) {
		
		NeuralNetwork nw = new NeuralNetwork(2, 2, 1);
		
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
		
		//list of expected
		List<Double> expected = new ArrayList<Double>();
		expected.add((double)0); expected.add((double)0);
		expected.add((double)0); expected.add((double)1);
		
		//training camp
		//System.out.println(nw.getOutputLayer().getWeightList());
		
		int trials = 10000;
		int trainMethod = 1;
		if(trainMethod == 0){
			System.out.println("first");
			for(int i=0; i<trials; i++){
				int randComb = ThreadLocalRandom.current().nextInt(0, 4);
				chosen.set(randComb, chosen.get(randComb)+1);
				List<Double> exp = new ArrayList<Double>(); exp.add(expected.get(randComb));
				nw.trainNetwork(combinations.get(randComb), exp, learningRate);
				//nw.feedNetwork(combinations.get(randComb));
				
				//nw.backwardPropagation(exp);
				//nw.updateNetwork(learningRate);
			}
		}
		else{
			System.out.println("second");
			for(int i=0; i<trials; i++){
				// random value from [0,4]
				int randComb = ThreadLocalRandom.current().nextInt(0, 4);
				//update value from chosen
				chosen.set(randComb, chosen.get(randComb)+1);
				//feed the random combination
				//System.out.println(combinations.get(randComb));
				nw.feedNetwork(combinations.get(randComb));
				//backward propagate
				List<Double> exp = new ArrayList<Double>(); exp.add(expected.get(randComb));
				nw.backwardPropagation(exp);
				//System.out.println(expected.get(randComb));
				//learning
				nw.updateNetwork(learningRate);
				//System.out.println(nw.getOutputLayer().getOutput());
			}
		}
		
		//check 'equally' distributed learning points given
		System.out.println(chosen);
		
		//check weights after train
		//System.out.println(nw.getOutputLayer().getWeightList());
		//System.out.println(nw.getOutputLayer().getOutput());
		
		//post training
		// 0, 0
		nw.feedNetwork(combinations.get(0));
		nw.clearInputList();
		System.out.println(combinations.get(0));
		System.out.println(nw.getOutput());
		
		// 0, 1
		nw.feedNetwork(combinations.get(1));
		nw.clearInputList();
		System.out.println(combinations.get(1));
		System.out.println(nw.getOutput());
		
		// 1, 0
		nw.feedNetwork(combinations.get(2));
		nw.clearInputList();
		System.out.println(combinations.get(2));
		System.out.println(nw.getOutput());
		
		// 1, 1
		nw.feedNetwork(combinations.get(3));
		nw.clearInputList();
		System.out.println(combinations.get(3));
		System.out.println(nw.getOutput());

	}

}
