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
		

	}

}
