package genetic;

import java.util.List;

public class RobotFindExit {
	
	char[][] maze = generateMaze(10, 10);
	
	List<Gene[]> population = Gene.generatePopulation(1000, 25);

	public static void main(String[] args) {

	}

	
	
	
	public char[][] generateMaze(int lenght, int width){
		char[][] maze = new char[lenght][width];
		return maze;
	}
}
