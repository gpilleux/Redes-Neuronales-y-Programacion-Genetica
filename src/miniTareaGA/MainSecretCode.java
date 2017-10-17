package miniTareaGA;

public class MainSecretCode {

	public static void main(String[] args) {
		int[] secretcode = new int[]{1, 1, 1, 1, 1, 1, 1};
		//insert secret code, number of population, max int
		SecretCode sc = new SecretCode(secretcode, 30, 10);
		sc.evaluateFitness();
		/*
		System.out.println("Starting...");
		System.out.println("Population genes BEFORE ");
		for(Individual in : sc.population){
			for(int c : in.genes)
				System.out.print(c + " ");
			System.out.println();
		}
		*/
		sc.verifyCode();
		int i=0;
		while(!sc.verifiedCode){
			sc.reproduction();
			/*
			System.out.println("Population genes during " + i);
			for(Individual in : sc.population){
				System.out.print("# same genes: " + sc.countSameGenes(in.genes) + " genes: ");
				for(int c : in.genes)
					System.out.print(c + " ");
				System.out.println();
			}
			*/
			i++;
			if(sc.everyEqualComb()){
				System.out.println("Every combination is the same !");
				break;
			}
		}
		//System.out.println(i);
		
		if(!sc.everyEqualComb()){
			System.out.println("Verified code in " + i + " attempts!");
			System.out.println("Your code is ");
			for(int c : sc.guessedCode)
				System.out.print(c + " ");
		}
		
		/*
		System.out.println();
		System.out.println("Population genes AFTER ");
		
		for(Individual in : sc.population){
			for(int c : in.genes)
				System.out.print(c + " ");
			System.out.println();
		}
		*/
		
	}

}
