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

	@Test//min=2; max=12;
	public void testRollDiceBoundaries() {
		for(int i = 0; i<10000;i++){
			cup.rollDices();
			assertTrue(cup.getDiceSum()<=12 && cup.getDiceSum()>=2);
		}
	}

	@Test 	// test if it is possible to get same hit with two dices.
	public void testSameHit(){
		int testSize = 10000;
		int sameHit=0;
		
		for (int i = 0; i < testSize; i++) {
			cup.rollDices();
			if (cup.isSameHit()){
				sameHit++;
			}
		}
		assertTrue(sameHit > 0);				// assert Same hit is possible.
		assertFalse(sameHit > (testSize*0.2));	// assert same hit is possible less than 20% off times rolling dices.
	}
}
