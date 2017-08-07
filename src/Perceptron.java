import java.util.ArrayList;
import java.util.List;

public class Perceptron {
	private int w1;
	private int w2;
	private int bias;
	
	private int output;
	
	private int val1;
	private int val2;
	
	private List<Integer> inputList;
	
	private List<Perceptron> conections;

	
	public Perceptron(){
		this.w1 = 0;
		this.w2 = 0;
		this.bias = 0;
		inputList = new ArrayList<Integer>();
		conections = new ArrayList<Perceptron>();
	}
	
	public Perceptron(int w1, int w2, int bias){
		this.completeSetUp(w1, w2, bias);
		inputList = new ArrayList<Integer>();
		conections = new ArrayList<Perceptron>();
	}

	public void setWeights(int w1, int w2) {
		this.w1 = w1;
		this.w2 = w2;
	}
	
	public void setBias(int bias) {
		this.bias = bias;
	}
	
	
	public void completeSetUp(int w1, int w2, int bias){
		this.setWeights(w1, w2);
		this.setBias(bias);
	}

	//calculates the output depending on the inputs received
	public int getSignal() {
		if(inputList.size() > 1){
			int in1 = inputList.get(0);
			int in2 = inputList.get(1);
			//calculamos el valor
			int value = this.w1*in1 + this.w2*in2 + this.bias;
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
	
	public void setInput(int in1){
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
	public void setCompleteInput(int in1, int in2) {
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
	
	public List<Integer> getInputs(){
		return this.inputList;
	}
	
	//returns output of the perceptron
	public int getOutput(){
		return this.output;
	}
	
	public int getIn1(){
		return this.val1;
	}
	
	public int getIn2(){
		return this.val2;
	}
	
	

}
