package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.GameController;
import field.Brewery;

public class TestBrewery {
	
	private GameController gc;

	@Before
	public void setUp() throws Exception 
	{
		gc = new GameController();
		gc.getGUIController().isInDebugMode = true;
		String name [] = {"Kristofer", "mar"};
		gc.getPlayerController().createPlayers(name);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLandOn() 
	{
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Brewery)gc.getFieldController().getFields()[12]).setOwner(gc.getPlayerController().getPlayer(0));
		gc.getPlayerController().getPlayer(0).setPosition(12);
		assertTrue(gc.getFieldController().getFields()[12].landOn(gc));
	}
	
	@Test
	public void testGetRent() 
	{
//		Tester om spiller 1 betaler 100 i rente hvis han lander på spiler 0's brewery når han kun ejer et 
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Brewery)gc.getFieldController().getFields()[12]).setOwner(gc.getPlayerController().getPlayer(0));
		
		gc.getPlayerController().getPlayer(1).setPosition(12);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[12].landOn(gc);
		
		assertEquals(30000-((Brewery)gc.getFieldController().getFields()[12]).getRent(gc), gc.getPlayerController().getPlayer(1).getBalance());
	}
	
	@Test
	public void testGetRentWithBothOwned()
	{
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Brewery)gc.getFieldController().getFields()[12]).setOwner(gc.getPlayerController().getPlayer(0));
		((Brewery)gc.getFieldController().getFields()[28]).setOwner(gc.getPlayerController().getPlayer(0));
		
		gc.getPlayerController().getPlayer(1).setPosition(12);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[12].landOn(gc);
		
		assertEquals(30000-((Brewery)gc.getFieldController().getFields()[12]).getRent(gc), gc.getPlayerController().getPlayer(1).getBalance());
	}
}
