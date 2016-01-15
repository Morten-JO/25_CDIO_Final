package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GameController;
import field.Street;

public class TestUnPawnProperty {

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
	public void test_UnPawnProperty() {
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[1]).setIsPawn(true);
		((Street) gc.getFieldController().getFields()[1]).unPawnProperty(gc, gc.getPlayerController().getPlayer(0));
		assertEquals(29400, gc.getPlayerController().getPlayer(0).getBalance());
		assertEquals(((Street) gc.getFieldController().getFields()[1]).getIsPawn(),false);
		
	}

}
