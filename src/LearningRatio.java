import java.util.concurrent.ThreadLocalRandom;

public class LearningRatio {

	public static void main(String[] args) {
		Perceptron p = new Perceptron();
		p.completeSetUp(-3, 2, 1);
		
		int trials = 10000;
		
		int countAssertsBefore = 0;
		for(int i=0; i<trials; i++){
			double x = ThreadLocalRandom.current().nextInt(-50, 51);
			double y = ThreadLocalRandom.current().nextInt(-50, 51);
			p.setCompleteInput(x, y);
			int perceptronOut = (int)p.getSignal();
			if(2*x + 1 >= y){
				//1
				if(perceptronOut == 1)
					countAssertsBefore++;
			}else{
				//0
				if(perceptronOut == 0)
					countAssertsBefore++;
			}
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
			p.train(x, y, trainedOutput, c);
		}
		
		int countAssertsAfter = 0;
		for(int i=0; i<trials; i++){
			double x = ThreadLocalRandom.current().nextInt(-50, 51);
			double y = ThreadLocalRandom.current().nextInt(-50, 51);
			p.setCompleteInput(x, y);
			int perceptronOut = (int)p.getSignal();
			if(2*x + 1 >= y){
				//1
				if(perceptronOut == 1)
					countAssertsAfter++;
			}else{
				//0
				if(perceptronOut == 0)
					countAssertsAfter++;
			}
		}
		System.out.println(1.0*countAssertsAfter/trials);
		

	}

}
