package networks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataParser {

	public DataParser(){}
	
	public static List<List<Double>> parseToNetwork(String path) throws IOException{
		List<List<Double>> parsedData = new ArrayList<List<Double>>();
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			//first line
			String line = br.readLine();
			
			List<Double> expected = new ArrayList<Double>();
			
			while (line != null) {
				List<Double> newLine = new ArrayList<Double>();
				String[] splitLine = line.split("	");
				for(int i=0; i<splitLine.length - 1; i++){
					String eachElement = splitLine[i];
					newLine.add(Double.parseDouble(eachElement));
				}
				parsedData.add(newLine);
				String lastElement = splitLine[splitLine.length-1];
				expected.add(Double.parseDouble(lastElement));
				line = br.readLine();
			}
			//expected list is the last of the lists
			parsedData.add(expected);
		}catch(NumberFormatException e){
			throw new NumberFormatException("Datos en formato incorrecto");
		}finally {
			br.close();
		}
		return parsedData;
	}
}
