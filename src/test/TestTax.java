package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import field.Tax;

import controllers.GameController;
import field.Bonus;

public class TestTax {
	private GameController gc ; 
	@Before 
	public void setUp ()throws Exception 
	{
	gc = new GameController ();
	gc.getGUIController().isInDebugMode = true ;
	String name [] = {"Tobias","Frantsen"};
	gc.getPlayerController().createPlayers(name);	
	}
	
	
	@Test
	public void test_LandOnBonus() {
		((Tax) gc.getFieldController().getFields()[38]).landOn(gc);
		assertEquals(28000, gc.getPlayerController().getCurrentPlayer().getBalance());
	}
	
	@Test 
	public void test_SetBonus () {
		((Tax) gc.getFieldController().getFields()[38]).setTax(5000);
		assertEquals(5000,((Tax) gc.getFieldController().getFields()[38]).getTax());
	}

}
