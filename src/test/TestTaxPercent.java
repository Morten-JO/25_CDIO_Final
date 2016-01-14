package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GameController;
import field.TaxPercent;
import field.Street;

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
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).setAmountOfHotels(1);
		((Street)gc.getFieldController().getFields()[3]).setAmountOfHouses(4);
		gc.getPlayerController().getPlayer(0).adjustBalance(-11400);// hvad det koster at købe de 2 grunde plus 1 hotel og 4 huse
		assertEquals(30000-14400, gc.getPlayerController().getCurrentPlayer().getBalance());
	}

}
