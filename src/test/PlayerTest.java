package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import player.Player;

public class PlayerTest {
		
	private Player player;
	
	
	@Before
	public void setUp() throws Exception 
	{
		player = new Player("kristofer");
		player.setPosition(15);
		player.setJailed(false);
		player.setFirstRoundCompleted(false);
		
	}

	@Test
	public void testGetBalance() 
	{
		assertEquals(30000 , player.getBalance());
	}
	
	@Test
	public void testAdjustBalance() 
	{
		player.adjustBalance(20000);
		assertEquals(50000 , player.getBalance());
	}

	@Test
	public void testGetPosition() 
	{
		assertEquals(15, player.getPosition());
	}
	
	@Test
	public void testIsJailed() 
	{
		player.setJailed(true);
		assertTrue(player.isJailed());
	}
	
	@Test
	public void testGetFirstRoundCompleted() 
	{
		player.setFirstRoundCompleted(true);
		assertTrue(player.getFirstRoundCompleted());
	}
}
