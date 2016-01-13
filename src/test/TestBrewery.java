package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.GameController;
import field.Brewery;

public class TestBrewery {
	
	private GameController gc;
	private Brewery brewery;

	@Before
	public void setUp() throws Exception 
	{
		gc = new GameController();
		gc.getGUIController().isInDebugMode = true;
		brewery = new Brewery("tuborg", "Ejer", "Koster: ", 12, 3000, 1500, new int [] {100,200});
		String name [] = {"Kristofer", "mar"};
		gc.getPlayerController().createPlayers(name);

		
	}

	@After
	public void tearDown() throws Exception 
	{
	
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
	public void testDescription()
	{
//		Tester om de givende beskriverlser på gui brettet stemmer overens med de rigtige beskrivelser
		int [] testArray = new int[]{100,200};
		assertEquals("Tuborg", gc.getFieldController().getFields()[12].getName());
		assertEquals("Ejer", brewery.getDescriptionText());
		assertEquals("Koster: 3000" , brewery.getSubText());
		assertEquals(12, brewery.getNumber());
		assertEquals(3000, brewery.getPrice());
		assertEquals(1500, brewery.getPawnPrice());
	}
	
	@Test
	public void testGetRent() 
	{
//		Tester om spiller 1 betaler 100 i rente hvis han lander på spiler 0's brewery når han kun ejer et 
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Brewery)gc.getFieldController().getFields()[12]).setOwner(gc.getPlayerController().getPlayer(0));
		
		gc.getCup().rollDices();
		
		gc.getPlayerController().getPlayer(1).setPosition(12);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[12].landOn(gc);
		
		assertEquals(30000-((Brewery)gc.getFieldController().getFields()[12]).getRent(gc), gc.getPlayerController().getPlayer(1).getBalance());
	}
	
	@Test
	public void testGetRentWithBothOwned()
	{
//		Tester om spiller 1 betaler doublet i rente når en spilelr ejer begge breweryfelter. 
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Brewery)gc.getFieldController().getFields()[12]).setOwner(gc.getPlayerController().getPlayer(0));
		((Brewery)gc.getFieldController().getFields()[28]).setOwner(gc.getPlayerController().getPlayer(0));
		
		gc.getCup().rollDices();
		
		gc.getPlayerController().getPlayer(1).setPosition(12);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[12].landOn(gc);
		
		assertEquals(30000-((Brewery)gc.getFieldController().getFields()[12]).getRent(gc), gc.getPlayerController().getPlayer(1).getBalance());
	}
	
	
}
