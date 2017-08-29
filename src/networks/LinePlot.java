package networks;

/**
 * Grafica en estilo lineas
 * Posee un t�tulo, ejes tabulados y cantidad ilimitada de posibles series para graficar
 */

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class LinePlot<T1, T2> {
	private Axis<T1> xAxis;
	private Axis<T2> yAxis;
	private LineChart<T1, T2> plot;

	/**
	 * Constructor del gr�fico
     * Recibe los ejes del tipo T1 , T2
	 * @param xAxis eje X de tipo T1
	 * @param yAxis eje Y de tipo T2
	 */
	public LinePlot(Axis<T1> xAxis, Axis<T2> yAxis){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		plot = new LineChart<T1, T2>(xAxis, yAxis);
	}
	 /**
     * M�todo para agregar una serie al gr�fico
     * @param xData de tipo Number, ser�n los valores en el eje X
     * @param yData de tipo Number, puntos que ser�n graficados
	*/
	public void addSeries(List<T1> xData, List<T2> yData){
		addSeries(xData, yData, "");
	}
	/**
     * M�todo para agregar una serie al gr�fico
     * @param xData de tipo Number, ser�n los valores en el eje X
     * @param yData de tipo Number, puntos que ser�n graficados
     * @param seriesName nombre de la serie
	*/
	public void addSeries(List<T1> xData, List<T2> yData, String seriesName){
		if(xData.size() != yData.size())
			throw new IllegalArgumentException("X and Y data must have the same size");
		XYChart.Series<T1, T2> series = new XYChart.Series<>();
		for(int i=0; i<xData.size(); i++){
			series.getData().add(new XYChart.Data<>(xData.get(i), yData.get(i)));
		}
		series.setName(seriesName);
		plot.getData().add(series);
	}
	/**
     * Colocar t�tulo al gr�fico
     * @param title t�tulo del gr�fico
     */
	public void setTitle(String title){
		plot.setTitle(title);
	}
	 /**
     * Tabulaci�n eje X
     * @param label nombre eje X
     */
	public void setXLabel(String label){
		xAxis.setLabel(label);
	}
	/**
     * Tabulaci�n eje Y
     * @param label nombre eje Y
     */
	public void setYLabel(String label){
		yAxis.setLabel(label);
	}
	/**
     * M�todo para obtener el gr�fico de l�nea
     * @return gr�fico de l�nea
     */
	public LineChart<T1, T2> getPlot(){
		return plot;
	}
	/**
     * M�todo para obtener data de las series ingresadas
     * @return data de las series
     */
	public ObservableList<Series<T1, T2>> getPlotData(){
		return plot.getData();
	}
	/**
     * 
     * @return t�tulo del gr�fico
     */
	public Object getTitle() {
		return plot.getTitle();
	}
	/**
	 * 
	 * @return nombre del eje X
	 */
	public Object getXlabel() {
		return xAxis.getLabel();
	}
	/**
	 * 
	 * @return nombre del eje Y
	 */
	public Object getYlabel() {
		return yAxis.getLabel();
	}
}
