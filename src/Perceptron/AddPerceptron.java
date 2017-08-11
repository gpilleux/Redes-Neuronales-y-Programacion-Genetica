package Perceptron;

public class AddPerceptron {
	private Perceptron p1;
	private Perceptron p2;
	private Perceptron p3;
	private Perceptron p4;
	private Perceptron p5;
	
	private double sum;
	private double carry;
	
	//5 NAND perceptrons
	public AddPerceptron(){
		p1 = new Perceptron(-2, -2, 3);
		p2 = new Perceptron(-2, -2, 3);
		p3 = new Perceptron(-2, -2, 3);
		p4 = new Perceptron(-2, -2, 3);
		p5 = new Perceptron(-2, -2, 3);
		
		this.setConections();
	}
	
	//set the connections based on the network seen in class
	private void setConections() {
		p1.addConection(p2);
		p1.addConection(p3);
		p1.addConection(p5);
		p1.addConection(p5);
		
		p2.addConection(p4);
		
		p3.addConection(p4);
	}

	//sets in initial inputs 
	public void feed(double x1, double x2){
		p1.setInput(x1);
		p1.setInput(x2);
		
		p2.setInput(x1);
		
		p3.setInput(x2);
		
		//save values
		this.passResults();
		
	}
	
	//saves the values of the sum and carry
	private void passResults(){
		this.sum = p4.getOutput();
		this.carry = p5.getOutput();
	}
	
	public double getSum(){
		return this.sum;
	}
	
	public double getCarry(){
		return this.carry;
	}
	
	public Perceptron getP1(){
		return this.p1;
	}
	
	public Perceptron getP2(){
		return this.p2;
	}
	
	public Perceptron getP3(){
		return this.p3;
	}
	
	public Perceptron getP4(){
		return this.p4;
	}
	
	public Perceptron getP5(){
		return this.p5;
	}

}
