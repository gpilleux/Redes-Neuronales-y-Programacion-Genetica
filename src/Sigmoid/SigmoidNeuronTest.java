package Sigmoid;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SigmoidNeuronTest {
	
	private SigmoidNeuron sn;
	
	@Before
	public void setup(){
		sn = new SigmoidNeuron();
	}
	
	
	@Test
	public void nandTest() {
		sn.setWeights(-2, -2);
		sn.setBias(3);
		
		sn.setInput(0); sn.setInput(0);
		assertEquals((int)sn.getSignal(), 1);
		
		sn.setInput(0); sn.setInput(1);
		assertEquals((int)sn.getSignal(), 1);
		
		sn.setInput(1); sn.setInput(0);
		assertEquals((int)sn.getSignal(), 1);
		
		sn.setInput(1); sn.setInput(1);
		assertEquals((int)sn.getSignal(), 0);
		
	}
	
	@Test
	public void orTest(){
		sn.setWeights(2,3);
		sn.setBias(-1);
		
		sn.setInput(0); sn.setInput(0);
		assertEquals((int)sn.getSignal(), 0);
		
		sn.setInput(0); sn.setInput(1);
		assertEquals((int)sn.getSignal(), 1);
		
		sn.setInput(1); sn.setInput(0);
		assertEquals((int)sn.getSignal(), 1);
		
		sn.setInput(1); sn.setInput(1);
		assertEquals((int)sn.getSignal(), 1);
	}
	
	@Test
	public void andTest(){
		sn.setWeights(2, 2);
		sn.setBias(-3);
		
		sn.setInput(0); sn.setInput(0);
		assertEquals((int)sn.getSignal(), 0);
		
		sn.setInput(0); sn.setInput(1);
		assertEquals((int)sn.getSignal(), 0);
		
		sn.setInput(1); sn.setInput(0);
		assertEquals((int)sn.getSignal(), 0);
		
		sn.setInput(1); sn.setInput(1);
		assertEquals((int)sn.getSignal(), 1);
	}

}
