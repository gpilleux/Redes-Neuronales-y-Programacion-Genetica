package Sigmoid;
import java.util.ArrayList;
import java.util.List;

public class SigmoidNeuron {
	private List<Double> weightList;
	private double bias;
	
	private List<Double> inputList;
	
	private double output;
	
	//private List<Perceptron> conections;
	
	public SigmoidNeuron(){
		weightList = new ArrayList<Double>();
		inputList = new ArrayList<Double>();
		//conections = new ArrayList<Perceptron>();
	}

	public void setWeights(double w1, double w2) {
		this.weightList.add(w1);
		this.weightList.add(w2);
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public void setInput(double in) {
		this.inputList.add(in);
	}
	
	public double getOutput(){
		return this.output;
	}
	
	//set the i-th weight
	public void setIthWeight(int index, double newWeight){
		this.weightList.set(index, newWeight);
	}

	//change to exception => throws Null pointer exception? 
	public double getSignal() {
		if(this.inputList.size() == this.weightList.size()){
			double value = this.bias;
			for(int i=0; i<inputList.size(); i++){
				value += this.weightList.get(i) * this.inputList.get(i);
			}
			double sigmoidFunction = 1.0 / (1 + Math.exp(-value));
			//limpiamos inputlist
			//this.inputList.clear();
			if(sigmoidFunction > 0.5){
				this.output = 1;
				return this.output;
			}
			this.output = 0;
			return this.output;
		}
		//la cantidad de weights no es la misma que de inputs => retorna "error" = -1
		this.output = -1;
		return this.output;
	}
	
	public void train(double x, double y, double desired, double learningRate){
		this.setInput(x); this.setInput(y);
		
		List<Double> inpList = this.getInputList();
		//System.out.println(inpList);
		
		double thisOut = this.getSignal();
		//System.out.println(inpList);
		if(thisOut > desired){
			for(int i=0; i<this.weightList.size(); i++){
				double newWeight = this.weightList.get(i) - inpList.get(i)*learningRate;
				this.setIthWeight(i, newWeight);
			}
		}
		else if(thisOut < desired){
			for(int i=0; i<this.weightList.size(); i++){
				double newWeight = this.weightList.get(i) + inpList.get(i)*learningRate;
				this.setIthWeight(i, newWeight);
			}
		}
		this.inputList.clear();
	}
	
	public double getIthWeight(int index){
		return this.weightList.get(index);
	}
	
	public List<Double> getWeightList(){
		return this.weightList;
	}
	
	public List<Double> getInputList(){
		return this.inputList;
	}
	

}
