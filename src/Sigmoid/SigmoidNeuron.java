package sigmoid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SigmoidNeuron {
	private List<Double> weightList;
	private double bias;
	
	private List<Double> inputList;
	
	private double output;
	private double error;
	private double delta;
	
	private double expected;
	
	//forward propagation
	private List<SigmoidNeuron> conections;
	
	//backward propagations
	private List<SigmoidNeuron> getsFedBy;
	
	public SigmoidNeuron(){
		weightList = new ArrayList<Double>();
		inputList = new ArrayList<Double>();
		conections = new ArrayList<SigmoidNeuron>();
		getsFedBy = new ArrayList<SigmoidNeuron>();
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
		//if there're two elements in the list, send the signal to the connections
		if(this.inputList.size() == this.weightList.size()){
			this.sendSignal();
		}
	}
	
	//send the resulting output of this to all the connections associated
	public void sendSignal() {
		this.getSignal();
		for(SigmoidNeuron sn : this.conections){
			sn.setInput(this.getOutput());
		}
	}

	public double getOutput(){
		return this.output;
	}
	
	//set the i-th weight
	public void setIthWeight(int index, double newWeight){
		this.weightList.set(index, newWeight);
	}

	//change to exception => throws Null pointer exception? 
	//the inputlist is cleared when the weights are updated
	public double getSignal() {
		if(this.inputList.size() == this.weightList.size()){
			double value = this.bias;
			for(int i=0; i<inputList.size(); i++){
				value += this.weightList.get(i) * this.inputList.get(i);
			}
			this.output = 1.0 / (1 + Math.exp(-value));
			return output;
		}
		//number of weights not same as of inputs => returns "error" = -1
		this.output = -1;
		return this.output;
	}
	
	public void train(double x, double y, double desired, double learningRate){
		this.setInput(x); this.setInput(y);
		
		//when i tried doing newInpList = this.inputList it wouldn't
		//save it even before calling getSignal() ---> pointers !
		List<Double> inpList = new ArrayList<Double>();
		for(int i=0; i < this.inputList.size(); i++){
			inpList.add(this.inputList.get(i));
		}
		
		double thisOut = this.getSignal();
		this.inputList.clear();
		
		if(thisOut > desired){
			this.bias -= this.bias*learningRate;
			for(int i=0; i<this.weightList.size(); i++){
				double newWeight = this.weightList.get(i) - inpList.get(i)*learningRate;
				this.setIthWeight(i, newWeight);
			}
		}
		else if(thisOut < desired){
			this.bias += this.bias*learningRate;
			for(int i=0; i<this.weightList.size(); i++){
				double newWeight = this.weightList.get(i) + inpList.get(i)*learningRate;
				this.setIthWeight(i, newWeight);
			}
		}
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
	
	//add the connections of the Sigmoid. Forward (right) propagation
	public void addConection(SigmoidNeuron sn){
		this.conections.add(sn);
	}
	
	
	//add getsFedBy connections of the Sigmoid. Backward (left) propagation
	public void addGetsFedBy(SigmoidNeuron sn){
		this.getsFedBy.add(sn);
	}
	
	//returns the List of Sigmoid that THIS feeds to
	public List<SigmoidNeuron> getConections(){
		return this.conections;
	}
	
	//returns List of Sigmoid that feeds to THIS
	public List<SigmoidNeuron> getGetsFedBy(){
		return this.getsFedBy;
	}
	
	
	/*
	 From this point on, code goes directly related to the Network 
	*/
	
	//calculates 
	public void calculateErrorDelta(int neuronPosition){
		this.error = 0;
		this.delta = 0;
		//if it's the last layer (only 1 neuron) (doesnt have connections)
		if(this.conections.size() <= 0){
			//System.out.println("entered once");
			this.error = this.expected - this.output;
			this.delta = this.error * this.transferedDerivative(this.output);
			return;
		}
		//not the last layer
		for(SigmoidNeuron sn : this.conections){
			this.error += sn.getIthWeight(neuronPosition) * sn.getDelta();
		}
		this.delta = this.error * this.transferedDerivative(this.output);
	}

	public double getDelta() {
		return this.delta;
	}
	
	public double getError() {
		return this.error;
	}

	private double transferedDerivative(double output) {
		return output*(1 - output);
	}
	
	//must delete the inputlist for next inputs
	public void updateWeights(double learningRate){
		//update bias
		this.bias += learningRate * this.delta;
		
		//update each weight. ithWeight = ithWeight + learningRate * delta * ithInput
		for(int i=0; i<this.weightList.size(); i++){
			double newIthWeight = this.getIthWeight(i) + learningRate * this.delta * this.inputList.get(i);
			this.weightList.set(i, newIthWeight);
		}
		this.inputList.clear();
	}
	
	
	// creates a SigmoidNeuron with numberWeights of weights, random from [1, 2]
	public SigmoidNeuron(int numberWeights){
		//set all the private variables 
		this();
		//adds the number of weights needed
		for(int i=0; i<numberWeights; i++){
			double newWeight = ThreadLocalRandom.current().nextDouble(0, 1); //random weight [1,2]
			this.weightList.add(newWeight);
		}
		this.bias = ThreadLocalRandom.current().nextDouble(-3, 0);
	}
	
	/*
	 * Getters / Setters
	 */
	
	public void setExpected(double expected){
		this.expected = expected;
	}
	
	
}
