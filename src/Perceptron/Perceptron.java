package perceptron;
import java.util.ArrayList;
import java.util.List;

public class Perceptron {
	private List<Double> weightList;
	private double bias;
	
	private double output;
	
	private double val1;
	private double val2;
	
	private List<Double> inputList;
	
	private List<Perceptron> conections;

	
	public Perceptron(){
		weightList = new ArrayList<Double>();
		inputList = new ArrayList<Double>();
		conections = new ArrayList<Perceptron>();
	}
	
	public Perceptron(double w1, double w2, double bias){
		this.completeSetUp(w1, w2, bias);
		inputList = new ArrayList<Double>();
		conections = new ArrayList<Perceptron>();
	}

	public void setWeights(double w1, double w2) {
		this.weightList.add(w1);
		this.weightList.add(w2);
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
		if(this.inputList.size() == this.weightList.size()){
			double value = this.bias;
			for(int i=0; i<this.inputList.size(); i++){
				value += this.weightList.get(i) * this.inputList.get(i);
			}
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
		return this.weightList.get(0);
	}
	
	public double getWeight2(){
		return this.weightList.get(1);
	}
	
	//recieves ordered pair and depending on the function returns 0 or 1
	public void train(double x, double y, double desired, double learningRate){
		this.setCompleteInput(x, y);
		
		List<Double> inpList = new ArrayList<Double>();
		for(int i=0; i < this.inputList.size(); i++){
			inpList.add(this.inputList.get(i));
		}
		
		double thisOut = this.getSignal();
		
		//thisOut = 1; desired = 0 => restar
		if(thisOut > desired){
			for(int i=0; i<this.weightList.size(); i++){
				double newWeight = this.weightList.get(i) - inpList.get(i)*learningRate;
				this.setIthWeight(i, newWeight);
			}
		}
		//thisOut = 0; desired = 1 => sumar
		else if(thisOut < desired){
			for(int i=0; i<this.weightList.size(); i++){
				double newWeight = this.weightList.get(i) + inpList.get(i)*learningRate;
				this.setIthWeight(i, newWeight);
			}
		}
		
	}

	private void setIthWeight(int index, double newWeight) {
		this.weightList.set(index, newWeight);
	}
	

}
