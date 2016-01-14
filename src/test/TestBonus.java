package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import field.Bonus;
import controllers.GameController;
import field.Street;

public class TestBonus {

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
		((Bonus) gc.getFieldController().getFields()[20]).landOn(gc);
		assertEquals(35000, gc.getPlayerController().getCurrentPlayer().getBalance());
	}
	
	@Test 
	public void test_SetBonus () {
		((Bonus) gc.getFieldController().getFields()[20]).setBonus(1000);
		assertEquals(1000,((Bonus) gc.getFieldController().getFields()[20]).getBonus());
	}

}
