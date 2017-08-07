import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		AddPerceptron ap = new AddPerceptron();
		ap.feed(1, 1);
		
		Perceptron p1 = ap.getP1();
		List<Perceptron> listp1 = p1.getConections();
		
		Perceptron p2 = ap.getP2();
		Perceptron p3 = ap.getP3();
		Perceptron p4 = ap.getP4();
		Perceptron p5 = ap.getP5();
		
		//System.out.println(listp1.get(0).equals(p2));
		//System.out.println(p2);
		
		//listp1.clear();
		/*
		System.out.println(p1.getOutput());
		System.out.println(p2.getOutput());
		System.out.println(p3.getOutput());
		System.out.println(p4.getOutput());
		System.out.println(p5.getOutput());
		*/
		System.out.println(p1.getIn1());
		System.out.println(p1.getIn2());
		
		System.out.println(p2.getIn1());
		System.out.println(p2.getIn2());
		
		System.out.println(p3.getIn1());
		System.out.println(p3.getIn2());
		
		System.out.println(p4.getIn1());
		System.out.println(p4.getIn2());
		
		System.out.println(p5.getIn1());
		System.out.println(p5.getIn2());
		
		//System.out.println(ap.getSum());

		System.out.println(p4.getOutput());
		System.out.println(p5.getOutput());
		/*
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(2);
		System.out.println(list.get(0));
		*/
		
	}

}
