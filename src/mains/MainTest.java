package mains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
	
	public static List<Double> stringToList(String str){
		List<Double> dbList = new ArrayList<Double>();
		String[] strArray = str.split(" ");
		for(int i=0; i<strArray.length; i++){
			dbList.add(Double.parseDouble(strArray[i]));
		}
		return dbList;
	}

	public static void main(String[] args) {
		String oracion = "Esta es una, oración legítima.";
		oracion = oracion.replace(".", "").replaceAll(",", "").replaceAll(";", "").replaceAll(":", "");
		//System.out.println(oracion);
		
		try{
			File inputFile = new File("C:/Users/GPG/workspace/Tarea3RedNeuro/ora.txt");
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			String content = reader.readLine();
			content = content.replaceAll("(?s)<U+.*?>", "");
			content = content.replaceAll(",", "").replaceAll(" +", " ");
			
			System.out.println(content.split("1")[1].trim());
		}catch(Exception e){
			System.out.println(e);
		}
		

	}

}
