package Perceptron;
import java.util.ArrayList;
import java.util.List;

public class Perceptron {
	private double w1;
	private double w2;
	private double bias;
	
	private double output;
	
	private double val1;
	private double val2;
	
	private List<Double> inputList;
	
	private List<Perceptron> conections;

	
	public Perceptron(){
		this.w1 = 0;
		this.w2 = 0;
		this.bias = 0;
		inputList = new ArrayList<Double>();
		conections = new ArrayList<Perceptron>();
	}
	
	public Perceptron(double w1, double w2, double bias){
		this.completeSetUp(w1, w2, bias);
		inputList = new ArrayList<Double>();
		conections = new ArrayList<Perceptron>();
	}

	public void setWeights(double w1, double w2) {
		this.w1 = w1;
		this.w2 = w2;
	}
	
	public void setBias(double bias) {
		this.bias = bias;
	}
	
	
	public void completeSetUp(double w1, double w2, double bias){
		this.setWeights(w1, w2);
		this.setBias(bias);
	}

	//calculates the output depending on the inputs received
	public double getSignal() {
		if(inputList.size() > 1){
			double in1 = inputList.get(0);
			double in2 = inputList.get(1);
			//calculamos el valor
			double value = this.w1*in1 + this.w2*in2 + this.bias;
			//delete inputs from list
			this.inputList.clear();
			if(value <= 0 ){
				this.output = 0;
				return this.output;
			}
			this.output = 1;
			return this.output;
		}
		//if asked for a signal and doesn't have both inputs
		return -1;
	}
	
	public void setInput(double in1){
		//save the inputs of the perceptron
		if(inputList.size() == 0){
			this.val1 = in1;
		}else{
			this.val2 = in1;
		}
		
		this.inputList.add(in1);
		//if there're two elements in the list, send the signal to the connections
		if(this.inputList.size() > 1){
			this.sendSignal();
		}
	}
	
	//send the resulting output of this to all the connections associated
	public void sendSignal() {
		this.getSignal();
		for(Perceptron p : this.conections){
			p.setInput(this.getOutput());
		}
	}

	//testing purposes
	public void setCompleteInput(double in1, double in2) {
		this.inputList.add(in1);
		this.inputList.add(in2);
	}
	
	//add the connections of the Perceptron
	public void addConection(Perceptron p){
		this.conections.add(p);
	}
	
	
	public List<Perceptron> getConections(){
		return this.conections;
	}
	
	public List<Double> getInputs(){
		return this.inputList;
	}
	
	//returns output of the perceptron
	public double getOutput(){
		return this.output;
	}
	
	public double getIn1(){
		return this.val1;
	}
	
	public double getIn2(){
		return this.val2;
	}
	
	public double getWeight1(){
		return this.w1;
	}
	
	public double getWeight2(){
		return this.w2;
	}
	
	//recieves ordered pair and depending on the function returns 0 or 1
	public void train(double x, double y, double desired, double learningRate){
		this.setCompleteInput(x, y);
		this.getSignal();
		double thisOut = this.getOutput();
		
		double weight1 = this.getWeight1();
		double weight2 = this.getWeight2();
		
		//thisOut = 1; desired = 0 => restar
		if(thisOut > desired){
			this.setWeights(weight1 - learningRate*x, weight2 - learningRate*y);
		}
		//thisOut = 0; desired = 1 => sumar
		else if(thisOut < desired){
			this.setWeights(weight1 + learningRate*x, weight2 + learningRate*y);
		}
		
	}
	

}
