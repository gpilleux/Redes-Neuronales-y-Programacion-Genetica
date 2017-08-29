package networks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class ErrorPlot extends Application {
	
	/**
	 * Guarda el archivo de datos
	 * Options: tipos de plots que están disponibles para graficar
	 * Mapa para asociar el tipo de extension que le corresponde a cada tipo de Plot
	 */
	
	private ChoiceBox<String> options;
	

	public static void main(String[] args){
		launch(args);
	}
	
	/**
	 * Ejecuta la aplicación
	 * Coloca los elementos en la posición deseada
	 * Agrega las opciones disponibles
	 * Draw Button: llama la función que grafica
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

	protected Node buildPlot(List<Number> x, List<Number> y) throws NumberFormatException, IOException {
			String title = "Error vs. Taught Trials";
			String xLAbel = "Number of trials";
			String yLabel = "Error";
			String seriesName = "serie1";

			LinePlot<Number, Number> lineplot = new LinePlot<>(new NumberAxis(), new NumberAxis());
				
			lineplot.addSeries(x, y, seriesName);
				
			lineplot.setTitle(title);
			lineplot.setXLabel(xLAbel);
			lineplot.setYLabel(yLabel);
				
			return lineplot.getPlot();
			
	}
	
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
		/*
		for(int i=0; i<trials; i++){
			int rand = ThreadLocalRandom.current().nextInt(0, 3);
			nw.feedNetwork(combinations.get(rand));
			nw.backwardPropagation(expected.get(rand));
			nw.updateNetwork(learningRate);
		}
		*/
		
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
}
