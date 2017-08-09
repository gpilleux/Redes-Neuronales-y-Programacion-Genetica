import java.util.concurrent.ThreadLocalRandom;

public class Main {

	public static void main(String[] args) {
		Perceptron p = new Perceptron();
		p.completeSetUp(-3, 2, 1);
		double c = 0.1;
		double trainedOutput;
		
		for(int i=0; i<10; i++){
			double x = ThreadLocalRandom.current().nextInt(-50, 51);
			double y = ThreadLocalRandom.current().nextInt(-50, 51);
			//function: y = 2x + 1
			if(2*x + 1 >= y){trainedOutput = 1;}
			else{trainedOutput = 0;}
			p.train(x, y, trainedOutput, c);
		}
		
		System.out.println();
		
	}

}
