package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dices.Dice;

public class TestDice {
	
	private static Dice dice;
	

	@Before
	public void setUp() throws Exception {
		dice = new Dice(6);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testSides() {
		assertEquals(6, dice.getSides());
	}

	@Test
	public void testGetLastSum() {
		dice.hitDice();
		int sum = dice.getSum();
		dice.hitDice();
		assertEquals(dice.getLastSum(), sum);
	
	}
	
	@Test
	public void test() {
		assertEquals(6, dice.getSides());
	}
	
	@Test	// test if dice is symmetric.
	public void testDiceIsSymetric() {
		int testSize = 10000;
		double tl = testSize*0.18;	// Tolerance level over 10000 rolls = 18%
		int one = 0, two = 0, tree = 0, four = 0, five = 0, six = 0;
		
		for(int i=1; i< testSize; i++){
			dice.hitDice();
			dice.hitDice();
			if(dice.getSum() == 1) one++;
			if(dice.getSum() == 2) two++;
			if(dice.getSum() == 3) tree++;
			if(dice.getSum() == 4) four++;
			if(dice.getSum() == 5) five++;
			if(dice.getSum() == 6) six++;
		}
		assertTrue(one<tl && two<tl && tree<tl && four<tl && five<tl && six<tl);
	}
	
}
