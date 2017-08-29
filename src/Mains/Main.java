package mains;

import java.util.ArrayList;
import java.util.List;

import networks.NeuralNetwork;
import sigmoid.SigmoidNeuron;

public class Main {

	public static void main(String[] args) {
		SigmoidNeuron sn1 = new SigmoidNeuron();
		sn1.setWeights(-2, -2);
		sn1.setBias(3);
		
		SigmoidNeuron sn2 = new SigmoidNeuron();
		sn2.setWeights(-2, -2);
		sn2.setBias(3);
		
		SigmoidNeuron sn3 = new SigmoidNeuron();
		sn3.setWeights(-2, -2);
		sn3.setBias(3);
		
		//1 y 2 apuntan hacia 3
		sn1.addConection(sn3);
		sn2.addConection(sn3);
		
		//3 apunta hacia 1 y 2
		sn3.addGetsFedBy(sn1);
		sn3.addGetsFedBy(sn2);
		
		sn1.setInput(0);
		sn1.setInput(0);
		
		sn2.setInput(1);
		sn2.setInput(1);
		/*
		System.out.println(sn1.getOutput());
		System.out.println(sn2.getOutput());
		System.out.println(sn3.getOutput());
		*/
		sn3.setExpected(1);
		sn3.calculateErrorDelta(0);
		//System.out.println(sn3.getError());
		//System.out.println(sn3.getDelta());
		
		List<List<Double>> combinations = new ArrayList<List<Double>>();
		
		List<Double> comb1 = new ArrayList<Double>(); 
		List<Double> comb2 = new ArrayList<Double>();
		List<Double> comb3 = new ArrayList<Double>(); 
		List<Double> comb4 = new ArrayList<Double>();
		
		comb1.add((double) 9); comb1.add((double) 0);
		comb2.add((double) 4); comb2.add((double) -1);
		comb3.add((double) 10); comb3.add((double) 5);
		comb4.add((double) -1); comb4.add((double) 4);
		
		combinations.add(comb1); combinations.add(comb2);
		combinations.add(comb3); combinations.add(comb4);
		
		NeuralNetwork nw = new NeuralNetwork(2, 2, 1);
		
		List<Double> maxMin = nw.calculateMaxMin(combinations);
		double normalized = nw.normalized(comb1.get(1), maxMin.get(2*1), maxMin.get(2*1+1), 1, 0);
		
		System.out.println(maxMin);
		System.out.println(normalized);
		
		int i=0;
		double dH = maxMin.get(2*i);
		double dL = maxMin.get(2*i + 1);
		nw.feedNetwork(nw.normalizedList(combinations.get(i), dH, dL, 1, 0));
		System.out.println(nw.getOutput());
		
		
		
		
		
		
		
		/*
		System.out.println(sn1.getConections().get(0).equals(sn3));
		System.out.println(sn2.getConections().get(0).equals(sn3));
		System.out.println(sn3.getConections());
		*/
		
		
	}

}
