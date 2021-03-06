package networks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DifferentLearningRate extends Application {
	
	/**
	 * Guarda el archivo de datos
	 * Options: tipos de plots que est�n disponibles para graficar
	 * Mapa para asociar el tipo de extension que le corresponde a cada tipo de Plot
	 */
	
	private ChoiceBox<String> options;
	

	public static void main(String[] args){
		launch(args);
	}
	
	/**
	 * Ejecuta la aplicaci�n
	 * Coloca los elementos en la posici�n deseada
	 * Agrega las opciones disponibles
	 * Draw Button: llama la funci�n que grafica
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@Override
	public void start(Stage stage) throws NumberFormatException, IOException {
        stage.setTitle("JPlot");

        BorderPane pane = new BorderPane();

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        
        options = new ChoiceBox<>();
        options.getItems().addAll("LinePlot Sample");

        options.setValue("LinePlot Sample");

        Button drawButton = new Button("Draw");
        pane.setCenter(training());


        hbox.getChildren().addAll(new Label("Plot Type:"), options, drawButton);
        pane.setTop(hbox);

        Scene scene  = new Scene(pane, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

	protected Node buildPlot(List<Number> x, List<Number> y0, List<Number> y1, List<Number> y2, List<Number> y3, List<Number> y4, List<Number> y5) throws NumberFormatException, IOException {
			String title = "Error vs. Taught Trials";
			String xLAbel = "Number of trials";
			String yLabel = "Error";
			String LR0 = "Learning Rate = 0.1";
			String LR1 = "Learning Rate = 0.2";
			String LR2 = "Learning Rate = 0.3";
			String LR3 = "Learning Rate = 0.5";
			String LR4 = "Learning Rate = 0.7";
			String LR5 = "Learning Rate = 1";

			LinePlot<Number, Number> lineplot = new LinePlot<>(new NumberAxis(), new NumberAxis());
				
			lineplot.addSeries(x, y0, LR0);
			lineplot.addSeries(x, y1, LR1);
			lineplot.addSeries(x, y2, LR2);
			lineplot.addSeries(x, y3, LR3);
			lineplot.addSeries(x, y4, LR4);
			lineplot.addSeries(x, y5, LR5);
				
			lineplot.setTitle(title);
			lineplot.setXLabel(xLAbel);
			lineplot.setYLabel(yLabel);
				
			return lineplot.getPlot();
			
	}
	
	public Node training() throws NumberFormatException, IOException{
		
List<List<Double>> data = DataParser.parseToNetwork("seed_data.txt");
		
		//expected is the last list
		List<Double> expected = data.get(data.size()-1);
		//System.out.println(data.get(data.size()-1) == expected); //true
		data.remove(data.size() - 1);
		
		List<Double> one = new ArrayList<Double>(); one.add((double)1); one.add((double)0); one.add((double)0);
		List<Double> two = new ArrayList<Double>(); two.add((double)0); two.add((double)1); two.add((double)0);
		List<Double> three = new ArrayList<Double>(); three.add((double)0); three.add((double)0); three.add((double)1);
		
		//System.out.println(data.get(data.size()-1) == expected); //false (removed)
		Map<Double, List<Double>> tripleExpected = new HashMap<Double, List<Double>>();
		tripleExpected.put((double)1, one);
		tripleExpected.put((double)2, two);
		tripleExpected.put((double)3, three);
		
		/*
		 * make NeuralNetwork
		 */
		
		// 7 inputs, 5 inputNeuron, 3 outputNeurons
		NeuralNetwork nw = new NeuralNetwork(7, 5, 3);
		NeuralNetwork nw1 = new NeuralNetwork(7, 5, 3);
		NeuralNetwork nw2 = new NeuralNetwork(7, 5, 3);
		NeuralNetwork nw3 = new NeuralNetwork(7, 5, 3);
		NeuralNetwork nw4 = new NeuralNetwork(7, 5, 3);
		NeuralNetwork nw5 = new NeuralNetwork(7, 5, 3);
		
		/*
		 * Normalize data
		 */
		List<List<Double>> normalizedData = nw.normalizationList(data, 1, 0);
		//System.out.println(normalizedData);
		
		/*
		 * separate data 80 training - 20 testing
		 */
		//80%
		List<List<Double>> trainSet = nw.generateRandoms(normalizedData, expected, 0.8);
		List<Double> trainExpected = trainSet.get(trainSet.size() - 1);
		trainSet.remove(trainSet.size() - 1);
		//20%
		List<List<Double>> testSet = nw.generateRandoms(normalizedData, expected, 0.2);
		List<Double> testExpected = testSet.get(testSet.size() - 1);
		testSet.remove(testSet.size() - 1);
		
		
		/*
		 * train with 80% of the data
		 */
		int trials = 3;
		
		double learningRate = 0.1;
		for(int j=0; j <trials; j++){
			for(int i=0; i<trainSet.size(); i++){
				//System.out.println(trainSet.get(i) + " " + tripleExpected.get(trainExpected.get(i)));
				nw.trainNetwork(trainSet.get(i), tripleExpected.get(trainExpected.get(i)), 0.1);
				nw1.trainNetwork(trainSet.get(i), tripleExpected.get(trainExpected.get(i)), 0.2);
				nw2.trainNetwork(trainSet.get(i), tripleExpected.get(trainExpected.get(i)), 0.3);
				nw3.trainNetwork(trainSet.get(i), tripleExpected.get(trainExpected.get(i)), 0.5);
				nw4.trainNetwork(trainSet.get(i), tripleExpected.get(trainExpected.get(i)), 0.7);
				nw5.trainNetwork(trainSet.get(i), tripleExpected.get(trainExpected.get(i)), 1.0);
				//break;
			}
		}
		
		
		List<Number> y = nw.getErrorList();
		List<Number> y1 = nw1.getErrorList();
		List<Number> y2 = nw2.getErrorList();
		List<Number> y3 = nw3.getErrorList();
		List<Number> y4 = nw4.getErrorList();
		List<Number> y5 = nw5.getErrorList();
		
		
		List<Number> x = new ArrayList<Number>();
		for(int i=0; i<y.size(); i++){
			x.add(i+1);
		}
		
		
		
		
		return buildPlot(x, y, y1, y2, y3, y4, y5);
	}
	/*
	public Node training() throws NumberFormatException, IOException{
		int trials = 10000;
		
		NeuralNetwork nw = new NeuralNetwork(2, 2, 1);
		
		double learningRate = 0.1;
		List<List<Double>> combinations = new ArrayList<List<Double>>();
		List<Double> comb1 = new ArrayList<Double>(); // 0,0
		List<Double> comb2 = new ArrayList<Double>(); // 0,1
		List<Double> comb3 = new ArrayList<Double>(); // 1,0
		List<Double> comb4 = new ArrayList<Double>(); // 1,1
		
		comb1.add((double) 0); comb1.add((double) 0);
		comb2.add((double) 0); comb2.add((double) 1);
		comb3.add((double) 1); comb3.add((double) 0);
		comb4.add((double) 1); comb4.add((double) 1);
		
		combinations.add(comb1); combinations.add(comb2);
		combinations.add(comb3); combinations.add(comb4);
		//list of expected // changing values here we can teach the network
		List<Double> expected = new ArrayList<Double>();
		expected.add((double)0); expected.add((double)1);
		expected.add((double)1); expected.add((double)0);
		
		//training
		
		for(int i=0; i<trials; i++){
			int rand = ThreadLocalRandom.current().nextInt(0, 3);
			nw.feedNetwork(combinations.get(rand));
			nw.backwardPropagation(expected.get(rand));
			nw.updateNetwork(learningRate);
		}
		
		
		for(int i=0; i<trials; i++){
			nw.feedNetwork(combinations.get(0));
			List<Double> exp1 = new ArrayList<Double>(Arrays.asList(expected.get(0)));
			nw.backwardPropagation(exp1);
			nw.updateNetwork(learningRate);
			
			nw.feedNetwork(combinations.get(1));
			List<Double> exp2 = new ArrayList<Double>(Arrays.asList(expected.get(1)));
			nw.backwardPropagation(exp2);
			nw.updateNetwork(learningRate);
			
			nw.feedNetwork(combinations.get(2));
			List<Double> exp3 = new ArrayList<Double>(Arrays.asList(expected.get(2)));
			nw.backwardPropagation(exp3);
			nw.updateNetwork(learningRate);
			
			nw.feedNetwork(combinations.get(3));
			List<Double> exp4 = new ArrayList<Double>(Arrays.asList(expected.get(3)));
			nw.backwardPropagation(exp4);
			nw.updateNetwork(learningRate);
		}
		
		
		List<Number> y = nw.getErrorList();
		
		List<Number> x = new ArrayList<Number>();
		for(int i=0; i<y.size(); i++){
			x.add(i+1);
		}
		
		return buildPlot(x, y);
	}
	*/
}
