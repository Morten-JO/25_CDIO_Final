package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import player.Player;

public class PlayerTest {
		
	private Player player = new Player("Kristofer");
	
	
	@Before
	public void setUp() throws Exception 
	{
		assertEquals(player.getName(), "Kristofer");
		assertEquals(player.getBalance(),30000);
		assertEquals(player.getPosition(),0);
		assertFalse(player.isJailed());
		assertFalse(player.getFirstRoundCompleted());
	}

	
	@Test
	public void test() {
		player.adjustBalance(20000);			// add 20.000 in players account
		player.setPosition(15);					// moves player to position 15
		player.setJailed(true);					// puts player in jail
		player.setFirstRoundCompleted(true);	// player completed round
	}

	
	@After
	public void tearDown() throws Exception {
		
		assertEquals(player.getBalance(),50000);
		assertEquals(player.getPosition(),15);
		assertTrue(player.isJailed());
		assertTrue(player.getFirstRoundCompleted());
		
	}
	
	
	
}
