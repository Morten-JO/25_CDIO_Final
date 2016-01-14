package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dices.Cup;

public class TestCup {
	private Cup cup;

	@Before
	public void setUp() throws Exception {
		cup = new Cup(2,6);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test//min=2; max=14;
	public void testRollDiceBoundaries() {
		for(int i = 0; i<10000;i++){
			cup.rollDices();
			if(cup.getDiceSum()<2 || cup.getDiceSum()>14)
				assertTrue(false);
		}
	}

	@Test
	public void testSums(){
		cup.rollDices();
		int firstSum = cup.getDiceSum();
		cup.rollDices();
		assertEquals(firstSum, cup.getLastDiceSum());
		
	}
}
