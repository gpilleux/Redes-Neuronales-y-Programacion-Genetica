package networks;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

import sigmoid.SigmoidNeuron;

public class NeuralNetworkTest {
	
	private NeuralNetwork nc; //networkconnections (nc)
	private NeuralNetwork nwAnd;
	private NeuralNetwork nwXor;
	private List<List<Double>> combinations;
	private double learningRate;
	

	@Before
	public void setUp(){
		//connections testing
		List<Integer> hiddenLayers = new ArrayList<Integer>();
		hiddenLayers.add(3); hiddenLayers.add(2);
		//2 inputs, neuron formation: 2,3,2,1
		nc = new NeuralNetwork(2, 2, 2, hiddenLayers);
		
		
		//AND / XOR testing
		learningRate = 0.1;
		
		combinations = new ArrayList<List<Double>>();
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
	}
	
	@Test
	public void connectionsTest(){
		//connections/getsFedBy of input layer
		for(int i=0; i<nc.getInputLayer().size(); i++){
			SigmoidNeuron inp = nc.getInputLayer().get(i);
			for(int j=0; j<inp.getConections().size(); j++){
				//verify the connection (-->)
				assertEquals(inp.getConections().get(j), nc.getHiddenLayers().get(0).get(j));
				//verify the getsFedBy (<--)
				assertEquals(nc.getHiddenLayers().get(0).get(j).getGetsFedBy().get(i), inp);
			}
		}
		//connections/getsFedBy of hidden layers
		for(int h=0; h<nc.getHiddenLayers().size()-1; h++){
			List<SigmoidNeuron> lBefore = nc.getHiddenLayers().get(h);
			List<SigmoidNeuron> lAfter = nc.getHiddenLayers().get(h+1);
			for(int i=0; i<lBefore.size(); i++){
				SigmoidNeuron ith = lBefore.get(i);
				for(int j=0; j<lAfter.size(); j++){
					SigmoidNeuron jth = lAfter.get(j);
					assertEquals(ith.getConections().get(j), jth);
					assertEquals(jth.getGetsFedBy().get(i), ith);
				}
			}
		}
		//connections/getsFedBy of output
		List<SigmoidNeuron> lastHidden = nc.getHiddenLayers().get(nc.getHiddenLayers().size()-1);
		for(int i=0; i<lastHidden.size(); i++){
			SigmoidNeuron hid = lastHidden.get(i);
			for(int j=0; j<hid.getConections().size(); j++){
				//verify the connection (-->)
				assertEquals(hid.getConections().get(j), nc.getOutputLayer());
				//verify the getsFedBy (<--)
				assertEquals(nc.getOutputLayer().getGetsFedBy().get(i), hid);
			}
		}
	}

	@Test
	public void andTest() {
		nwAnd = new NeuralNetwork(2, 2);
		//first train
		
		//list of expected
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0); expected.add(0); expected.add(0); expected.add(1);
		
		int trials = 10000;
		for(int i=0; i<trials; i++){
			// random value from [0,4]
			int randComb = ThreadLocalRandom.current().nextInt(0, 4);
			//feed the random combination
			nwAnd.feedNetwork(combinations.get(randComb));
			//backward propagate
			nwAnd.backwardPropagation(expected.get(randComb));
			//learning
			nwAnd.updateNetwork(learningRate);
		}

		for(List<Double> comb : combinations){
			int compareTo = 0;
			nwAnd.feedNetwork(comb);
			if(nwAnd.getOutput() > 0.5)
				compareTo = 1;
			assertEquals((int)expected.get(combinations.indexOf(comb)), compareTo);
			nwAnd.clearInputList();
		}
	}
	
	@Test
	public void XORTest() {
		nwXor = new NeuralNetwork(2, 2);
		//first train
		
		//list of expected
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0); expected.add(1); expected.add(1); expected.add(0);
		
		int trials = 100000;
		for(int i=0; i<trials; i++){
			// random value from [0,4]
			int randComb = ThreadLocalRandom.current().nextInt(0, 4);
			//feed the random combination
			nwXor.feedNetwork(combinations.get(randComb));
			//backward propagate
			nwXor.backwardPropagation(expected.get(randComb));
			//learning
			nwXor.updateNetwork(learningRate);
		}
		
		for(List<Double> comb : combinations){
			int compareTo = 0;
			nwXor.feedNetwork(comb);
			if(nwXor.getOutput() > 0.5)
				compareTo = 1;
			assertEquals((int)expected.get(combinations.indexOf(comb)), compareTo);
			nwXor.clearInputList();
		}
	}
	
	

}
