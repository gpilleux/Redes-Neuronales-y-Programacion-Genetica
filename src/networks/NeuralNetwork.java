package networks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sigmoid.SigmoidNeuron;

public class NeuralNetwork {
	private List<SigmoidNeuron> inputLayer;
	private List<List<SigmoidNeuron>> hiddenLayers;
	//private List<SigmoidNeuron> outputLayer;
	private SigmoidNeuron output;
	
	private List<Number> errorList;
	
	
	/*
	 * numberInputs: number of inputs being fed (# of weights)
	 * numberInputLayer: number of neurons of the inputLayer
	 * hiddenLayers: how many hidden layers
	 * numbersHiddenLayer: list of the numbers of neurons of each layer
	 */
	public NeuralNetwork(int numberInputs, int numberInputLayer, int hiddenLayers, List<Integer> numbersHiddenLayer){
		this.setUpVariables();
		this.createInputLayer(numberInputs, numberInputLayer);
		this.createHiddenLayers(hiddenLayers, numbersHiddenLayer);
		this.createOutputLayer();
		this.connectNetwork();
	}

	/*
	 * if no hidden layers, create input and output layer
	 * numberInputs: number of inputs being fed (weights)
	 * numberInputLayer: number of neurons of the inputLayer
	 */
	public NeuralNetwork(int numberInputs, int numberInputLayer){
		this.setUpVariables();
		this.createInputLayer(numberInputs, numberInputLayer);
		this.createOutputLayer();
		this.connectNetwork();
	}
	
	private void setUpVariables(){
		this.inputLayer = new ArrayList<SigmoidNeuron>();
		this.hiddenLayers = new ArrayList<List<SigmoidNeuron>>();
		this.errorList = new ArrayList<Number>();
	}
	
	/*
	 * numberInputs: number of inputs being fed
	 * numberInputLayer: number of neurons of the inputLayer
	 */
	private void createInputLayer(int numberInputs, int numberInputLayer) {
		for(int i=0; i<numberInputLayer; i++){
			this.inputLayer.add(createNeuron(numberInputs));
		}
	}
	
	/*
	 * hiddenLayers: how many layers will the network have
	 * numberHiddenLayer: list of neurons on each layer
	 */
	private void createHiddenLayers(int hiddenLayers, List<Integer> numberHiddenLayer) {
		//first hidden layer based on the inputLayer
		this.hiddenLayers.add(createListSigmoids(numberHiddenLayer.get(0), this.inputLayer.size()));
		for(int i=1; i<hiddenLayers; i++){
			//every other layer is based on the previous layer
			this.hiddenLayers.add(createListSigmoids(numberHiddenLayer.get(i), this.hiddenLayers.get(i-1).size()));
		}
	}
	
	/*
	 * returns a List of neurons
	 */
	private List<SigmoidNeuron> createListSigmoids(int numberNeurons, int numberWeights){
		List<SigmoidNeuron> snList = new ArrayList<SigmoidNeuron>();
		for(int i=0; i<numberNeurons; i++){
			snList.add(this.createNeuron(numberWeights));
		}
		return snList;
	}
	
	private void createOutputLayer(){
		//if there're no hidden layers, create output based on inputLayer
		if(this.hiddenLayers.size() <= 0){
			//this.outputLayer.add(createNeuron(this.inputLayer.size()));
			this.output = createNeuron(this.inputLayer.size());
		}
		//there're hidden layers, create output based on the last hidden layer
		else{
			int opWeights = this.hiddenLayers.get(this.hiddenLayers.size() - 1).size();
			//this.outputLayer.add(createNeuron(opWeights));
			this.output = createNeuron(opWeights);
		}
	}
	
	//returns a neuron with a determined number of weights
	private SigmoidNeuron createNeuron(int numberWeights){
		return new SigmoidNeuron(numberWeights);
	}

	
	/*
	 * make conections between layers
	 */
	private void connectNetwork(){
		//if no hidden layers, connect inputlayer to the output
		if(this.hiddenLayers.size() <= 0){
			for(SigmoidNeuron inp : this.inputLayer){
				//add the connection to the inputList
				inp.addConection(this.output);
				//add the getsFedBy connection
				this.output.addGetsFedBy(inp);
			}
		}
		//exists hidden layer
		else{
			//first connect inputLayer to the first hidden layer
			for(SigmoidNeuron inp : this.inputLayer){
				for(SigmoidNeuron hid : this.hiddenLayers.get(0)){ //first hidden layer
					inp.addConection(hid);
					hid.addGetsFedBy(inp);
				}
			}
			//connect every other layer to each other (except last hidden layer that goes to the output)
			int i=0, j=1;
			while(i < this.hiddenLayers.size()-1 && j < this.hiddenLayers.size()){
				for(SigmoidNeuron ith : this.hiddenLayers.get(i)){
					for(SigmoidNeuron jth : this.hiddenLayers.get(j)){
						ith.addConection(jth);
						jth.addGetsFedBy(ith);
					}
				}
				i++; j++;
			}
			//connect last hidden layer to the output
			for(SigmoidNeuron last : this.hiddenLayers.get(this.hiddenLayers.size() - 1)){
				last.addConection(this.output);
				this.output.addGetsFedBy(last);
			}
			
		}
	}
	
	public void feedNetwork(List<Double> inputs){
		//for each neuron, feed the inputs
		for(SigmoidNeuron sn : this.inputLayer){
			for(double inp : inputs){
				sn.setInput(inp);
			}
		}
	}
	
	
	public void backwardPropagation(double expected){
		this.output.setExpected(expected);
		this.output.calculateErrorDelta(0);
		
		//add error to list
		this.errorList.add(Math.pow(this.output.getError(), 2));
		
		//for each layer, starting from the last layer, calculate error&delta
		for(int i=this.hiddenLayers.size() - 1; i>=0; i--){
			List<SigmoidNeuron> list = this.hiddenLayers.get(i);
			for(SigmoidNeuron hidden : list){
				//System.out.println(hidden + " " + list.indexOf(hidden));
				hidden.calculateErrorDelta(list.indexOf(hidden));
			}
		}
		//calculate error&delta for inputLayer
		for(SigmoidNeuron inp : this.inputLayer){
			inp.calculateErrorDelta(this.inputLayer.indexOf(inp));
			//System.out.println(inp + " " + this.inputLayer.indexOf(inp));
		}
	}
	
	public void updateNetwork(double learningRate){
		//update inputLater
		for(SigmoidNeuron sn : this.inputLayer){
			sn.updateWeights(learningRate);
		}
		//update HiddenLayers
		for(List<SigmoidNeuron> list : this.hiddenLayers){
			for(SigmoidNeuron sn : list){
				sn.updateWeights(learningRate);
			}
		}
		this.output.updateWeights(learningRate);
	}
	
	
	public void clearInputList(){
		//clear inputlist in inputLayer
		for(SigmoidNeuron inp : this.inputLayer){
			inp.getInputList().clear();
		}
		//clear inputlist in every hiddenLayer
		for(List<SigmoidNeuron> list : this.hiddenLayers){
			for(SigmoidNeuron hidden : list){
				hidden.getInputList().clear();
			}
		}
		//clear inputlist in outputLayer
		this.output.getInputList().clear();
		
	}
	
	
	/*
	 * Getters
	 */
	public double getOutput(){
		return this.output.getOutput();
	}
	
	public List<SigmoidNeuron> getInputLayer(){
		return this.inputLayer;
	}
	
	public List<List<SigmoidNeuron>> getHiddenLayers(){
		return this.hiddenLayers;
	}
	
	public SigmoidNeuron getOutputLayer(){
		return this.output;
	}

	public List<Number> getErrorList() {
		
		List<Number> newErrorList = new ArrayList<Number>();
		
		for(int i=0; i<this.errorList.size()/4; i++){
			double suma= ((double)this.errorList.get(4*i) + (double)this.errorList.get(4*i+1)
			+ (double)this.errorList.get(4*i+2) + (double)this.errorList.get(4*i+3))/4;
			Number promedio = (Number)suma;
			newErrorList.add(promedio);
		}
		
		
		return newErrorList;
	}
	
	public List<List<Double>> normalizationList(List<List<Double>> aNormalizar, double nH, double nL){
		List<List<Double>> newNorm = new ArrayList<List<Double>>();
		for(List<Double> aNorm : aNormalizar){
			double dH = Collections.max(aNorm);
			double dL = Collections.min(aNorm);
			List<Double> newList = new ArrayList<Double>();
			for(double element : aNorm){
				newList.add(normalized(element, dH, dL, nH, nL));
			}
			newNorm.add(newList);
		}
		return newNorm;
	}
	
	public double normalized(double valorNorm, double dH, double dL, double nH, double nL){
		return (double)(valorNorm-dL)*(nH-nL)/(dH - dL) + nL;
	}
	
	public List<Double> normalizedList(List<Double> beforeNorm, double dH, double dL, double nH, double nL){
		List<Double> normList = new ArrayList<Double>();
		for(double val : beforeNorm){
			normList.add(this.normalized(val, dH, dL, nH, nL));
		}
		return normList;
	}
	
	// returns a list in format Max,Min, Max,Min
	// first two values corresponds to the first column ...
	public List<Double> calculateMaxMin(List<List<Double>> inputs){
		List<Double> maxMin = new ArrayList<Double>();
		//initialize maxMin with the lowest and highest double
		for(int i=0; i<inputs.get(0).size(); i++){
			maxMin.add(Double.MIN_VALUE); // max
			maxMin.add(Double.MAX_VALUE);
		}
		// initialize max,min with values from first list
		for(int i=0; i<inputs.size(); i++){
			for(int j=0; j<inputs.get(i).size(); j++){
				// if i,j > than max => max = i,j
				if(inputs.get(i).get(j) > maxMin.get(2*j)){
					maxMin.set(2*j, inputs.get(i).get(j));
				}
				// if i,j < than min => min = i,j
				if(inputs.get(i).get(j) < maxMin.get(2*j + 1)){
					maxMin.set(2*j+1, inputs.get(i).get(j));
				}
			}
		}
		return maxMin;
	}

}

// outputLayer !!
