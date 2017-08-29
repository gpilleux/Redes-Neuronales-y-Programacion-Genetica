package sigmoid;

import java.util.concurrent.ThreadLocalRandom;

public class SigmoidLearningRate {

	public static void main(String[] args) {
		SigmoidNeuron sn = new SigmoidNeuron();
		sn.setWeights(-3, 2);
		sn.setBias(1);
		
		int trials = 1000;
		
		int countAssertsBefore = 0;
		for(int i=0; i<trials; i++){
			double x = ThreadLocalRandom.current().nextInt(-50, 51);
			double y = ThreadLocalRandom.current().nextInt(-50, 51);
			sn.setInput(x);
			sn.setInput(y);
			int perceptronOut = (int)sn.getSignal();
			//System.out.println(perceptronOut);
			if(2*x + 1 >= y){
				//1
				if(perceptronOut == 1)
					countAssertsBefore++;
			}else{
				//0
				if(perceptronOut == 0)
					countAssertsBefore++;
			}
			sn.getInputList().clear();
		}
		System.out.println(1.0*countAssertsBefore/trials);
		
		
		//training camp
		double c = 0.1;
		double trainedOutput;
		
		int trainTrials = 10;
		
		for(int i=0; i<trainTrials; i++){
			double x = ThreadLocalRandom.current().nextInt(-50, 51);
			double y = ThreadLocalRandom.current().nextInt(-50, 51);
			//function: y = 2x + 1
			if(2*x + 1 >= y){
				trainedOutput = 1;
			}else{
				trainedOutput = 0;
			}
			sn.train(x, y, trainedOutput, c);
		}
		
		
		int countAssertsAfter = 0;
		for(int i=0; i<trials; i++){
			double x = ThreadLocalRandom.current().nextInt(-50, 51);
			double y = ThreadLocalRandom.current().nextInt(-50, 51);
			sn.setInput(x);
			sn.setInput(y);
			int perceptronOut = (int)sn.getSignal();
			if(2*x + 1 >= y){
				//1
				if(perceptronOut == 1)
					countAssertsAfter++;
			}else{
				//0
				if(perceptronOut == 0)
					countAssertsAfter++;
			}
			sn.getInputList().clear();
		}
		System.out.println(1.0*countAssertsAfter/trials);
	}
	

}
