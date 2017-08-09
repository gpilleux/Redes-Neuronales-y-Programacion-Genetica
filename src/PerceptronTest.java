import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PerceptronTest {
	private Perceptron p;
	
	@Before
	public void setup() {
		p = new Perceptron();
	}
	
	
	@Test
	public void nandTest() {
		p.setWeights(-2, -2);
		p.setBias(3);
		
		p.setCompleteInput(0, 0);
		assertEquals((int)p.getSignal(), 1);
		
		p.setCompleteInput(0, 1);
		assertEquals((int)p.getSignal(), 1);
		
		p.setCompleteInput(1, 0);
		assertEquals((int)p.getSignal(), 1);
		
		p.setCompleteInput(1, 1);
		assertEquals((int)p.getSignal(), 0);
	}
	
	
	@Test
	public void orTest(){
		p.setWeights(2,3);
		p.setBias(-1);
		
		p.setCompleteInput(0, 0);
		assertEquals((int)p.getSignal(), 0);
		
		p.setCompleteInput(0, 1);
		assertEquals((int)p.getSignal(), 1);
		
		p.setCompleteInput(1, 0);
		assertEquals((int)p.getSignal(), 1);
		
		p.setCompleteInput(1, 1);
		assertEquals((int)p.getSignal(), 1);
	}
	
	@Test
	public void andTest(){
		p.setWeights(2, 2);
		p.setBias(-3);
		
		p.setCompleteInput(0, 0);
		assertEquals((int)p.getSignal(), 0);
		
		p.setCompleteInput(0, 1);
		assertEquals((int)p.getSignal(), 0);
		
		p.setCompleteInput(1, 0);
		assertEquals((int)p.getSignal(), 0);
		
		p.setCompleteInput(1, 1);
		assertEquals((int)p.getSignal(), 1);
	}

}
