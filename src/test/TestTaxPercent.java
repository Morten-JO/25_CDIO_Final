package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GameController;
import field.TaxPercent;

public class TestTaxPercent {

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
	public void test_LandOnTaxPercentAndPay4000() {
		gc.getGUIController().debugModeReturnTypeString = "4000";
		((TaxPercent) gc.getFieldController().getFields()[4]).landOn(gc);
		assertEquals(26000, gc.getPlayerController().getCurrentPlayer().getBalance());
	}
	
	@Test 
	public void test_LandOnTaxPercentAndPay10Percent() {
		gc.getGUIController().debugModeReturnTypeString = "10 %";
		((TaxPercent) gc.getFieldController().getFields()[4]).landOn(gc);
		assertEquals(27000, gc.getPlayerController().getCurrentPlayer().getBalance());
	}

}
