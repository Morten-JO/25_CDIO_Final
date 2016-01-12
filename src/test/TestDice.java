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
}
