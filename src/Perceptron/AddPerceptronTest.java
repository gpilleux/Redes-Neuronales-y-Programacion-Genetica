package Perceptron;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AddPerceptronTest {
	
	private AddPerceptron ap;
	
	@Before
	public void setup() {
		ap = new AddPerceptron();
	}
	
	
	@Test
	public void conectionsP1Test() {
		Perceptron p1 = ap.getP1();
		List<Perceptron> listp1 = p1.getConections();
		
		Perceptron p2 = ap.getP2();
		Perceptron p3 = ap.getP3();
		Perceptron p5 = ap.getP5();
		
		assertEquals(listp1.get(0), p2);
		assertEquals(listp1.get(1), p3);
		assertEquals(listp1.get(2), p5);
		assertEquals(listp1.get(3), p5);
		
	}
	
	@Test
	public void conectionsP2Test() {
		Perceptron p2 = ap.getP2();
		List<Perceptron> listp2 = p2.getConections();
		
		Perceptron p4 = ap.getP4();
		
		assertEquals(listp2.get(0), p4);
		
	}
	
	@Test
	public void conectionsP3Test() {
		Perceptron p3 = ap.getP3();
		List<Perceptron> listp3 = p3.getConections();
		
		Perceptron p4 = ap.getP4();
		
		assertEquals(listp3.get(0), p4);
		
	}
	
	@Test
	public void feedTest(){
		ap.feed(1, 0);
		
		Perceptron p1 = ap.getP1();
		Perceptron p2 = ap.getP2();
		Perceptron p3 = ap.getP3();
		
		assertEquals((int)p1.getIn1(), 1);
		assertEquals((int)p1.getIn2(), 0);
		
		assertEquals((int)p2.getIn1(), 1);
		assertEquals((int)p2.getIn2(), 1);
		
		assertEquals((int)p3.getIn1(), 1);
		assertEquals((int)p3.getIn2(), 0);
		
	}
	
	@Test
	public void addTest(){
		ap.feed(0, 0);
		assertEquals((int)ap.getSum(), 0);
		assertEquals((int)ap.getCarry(), 0);
		
		ap.feed(0, 1);
		assertEquals((int)ap.getSum(), 1);
		assertEquals((int)ap.getCarry(), 0);
		
		ap.feed(1, 0);
		assertEquals((int)ap.getSum(), 1);
		assertEquals((int)ap.getCarry(), 0);
		
		ap.feed(1, 1);
		assertEquals((int)ap.getSum(), 0);
		assertEquals((int)ap.getCarry(), 1);

	}

}
