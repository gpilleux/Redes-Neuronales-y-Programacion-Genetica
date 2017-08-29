package networks;

/**
 * Grafica en estilo lineas
 * Posee un título, ejes tabulados y cantidad ilimitada de posibles series para graficar
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
	 * Constructor del gráfico
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
     * Método para agregar una serie al gráfico
     * @param xData de tipo Number, serán los valores en el eje X
     * @param yData de tipo Number, puntos que serán graficados
	*/
	public void addSeries(List<T1> xData, List<T2> yData){
		addSeries(xData, yData, "");
	}
	/**
     * Método para agregar una serie al gráfico
     * @param xData de tipo Number, serán los valores en el eje X
     * @param yData de tipo Number, puntos que serán graficados
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
     * Colocar título al gráfico
     * @param title título del gráfico
     */
	public void setTitle(String title){
		plot.setTitle(title);
	}
	 /**
     * Tabulación eje X
     * @param label nombre eje X
     */
	public void setXLabel(String label){
		xAxis.setLabel(label);
	}
	/**
     * Tabulación eje Y
     * @param label nombre eje Y
     */
	public void setYLabel(String label){
		yAxis.setLabel(label);
	}
	/**
     * Método para obtener el gráfico de línea
     * @return gráfico de línea
     */
	public LineChart<T1, T2> getPlot(){
		return plot;
	}
	/**
     * Método para obtener data de las series ingresadas
     * @return data de las series
     */
	public ObservableList<Series<T1, T2>> getPlotData(){
		return plot.getData();
	}
	/**
     * 
     * @return título del gráfico
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
