package mains;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import networks.DataParser;
import networks.NeuralNetwork;

public class SeedPrediction {

	public static void main(String[] args) throws IOException {
		
		/*
		 * get the data and make HashMap
		 */
		List<List<Double>> data = DataParser.parseToNetwork("seed_data.txt");
		
		//expected is the last list
		List<Double> expected = data.get(data.size()-1);
		//System.out.println(data.get(data.size()-1) == expected); //true
		data.remove(data.size() - 1);
		
		List<Double> one = new ArrayList<Double>(); one.add((double)1); one.add((double)0); one.add((double)0);
		List<Double> two = new ArrayList<Double>(); two.add((double)0); two.add((double)1); two.add((double)0);
		List<Double> three = new ArrayList<Double>(); three.add((double)0); three.add((double)0); three.add((double)1);
		
		//System.out.println(data.get(data.size()-1) == expected); //false (removed)
		Map<Double, List<Double>> tripleExpected = new HashMap<Double, List<Double>>();
		tripleExpected.put((double)1, one);
		tripleExpected.put((double)2, two);
		tripleExpected.put((double)3, three);
		
		/*
		 * make NeuralNetwork
		 */
		
		List<Integer> hiddenLayer = new ArrayList<Integer>();
		hiddenLayer.add(5);
		
		// 7 inputs, 3 inputNeuron, 1 hidden layer (5 Neurons), 3 outputNeurons
		NeuralNetwork nw = new NeuralNetwork(7, 5, 3);
		
		/*
		 * Normalize data
		 */
		List<List<Double>> normalizedData = nw.normalizationList(data, 1, 0);
		//System.out.println(normalizedData);
		
		/*
		 * separate data 80 training - 20 testing
		 */
		//80%
		List<List<Double>> trainSet = nw.generateRandoms(normalizedData, expected, 0.8);
		List<Double> trainExpected = trainSet.get(trainSet.size() - 1);
		trainSet.remove(trainSet.size() - 1);
		//20%
		List<List<Double>> testSet = nw.generateRandoms(normalizedData, expected, 0.2);
		List<Double> testExpected = testSet.get(testSet.size() - 1);
		testSet.remove(testSet.size() - 1);
		
		
		/*
		 * before training, check performance
		 */
		
		
		
		/*
		 * train with 80% of the data
		 */
		
		double learningRate = 0.1;
		int trainTrials = 100;
		
		for(int j=0; j<trainTrials; j++){
			for(int i=0; i<trainSet.size(); i++){
				//System.out.println(trainSet.get(i) + " " + tripleExpected.get(trainExpected.get(i)));
				nw.trainNetwork(trainSet.get(i), tripleExpected.get(trainExpected.get(i)), learningRate);
				//break;
			}
		}

		/*
		 * test with 20% of data
		 */
		
		int assertsAfterTrain = 0;
		for(int i=0; i<testSet.size(); i++){
			nw.feedNetwork(testSet.get(i));
			double exp = testExpected.get(i);
			//get max value from output
			double maxOut = Collections.max(nw.getOutput());
			int prediction = nw.getOutput().indexOf(maxOut) + 1;
			if(prediction == exp)
				assertsAfterTrain++;
			nw.clearInputList();
			//System.out.println(nw.getOutput() + " " + prediction + " " + exp);
		}
		System.out.println(1.0*assertsAfterTrain/testSet.size());
		
	}

}
