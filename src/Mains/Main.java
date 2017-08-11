package Mains;

import Sigmoid.SigmoidNeuron;

public class Main {

	public static void main(String[] args) {
		SigmoidNeuron sn = new SigmoidNeuron();
		sn.setWeights(-3, 2);
		sn.setBias(1);
		System.out.println(sn.getWeightList());
		System.out.println(sn.getIthWeight(0));
		sn.getWeightList().get(0);
		System.out.println(sn.getWeightList());
	}

}
