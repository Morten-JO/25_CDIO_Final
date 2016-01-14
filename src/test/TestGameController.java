package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.GUIController;
import controllers.GameController;
import controllers.Language;
import field.Bonus;
import field.Street;
import field.Tax;


public class TestGameController {

	private GameController controller;
	
	@Before
	public void setUp() throws Exception {
		controller = new GameController();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWinWorks() {
		controller.getPlayerController().createPlayers(new String[]{"Test"});
		GUIController.isInDebugMode = true;
		controller.startGame();
		assertTrue(controller.checkIfGameIsWon());
	}
	
	@Test
	public void testSwapTurns(){
		controller.getPlayerController().createPlayers(new String[]{"Test","Test2"});
		GUIController.isInDebugMode = true;
		GUIController.debugModeReturnTypeString = Language.GameController_EndTurn;
		GUIController.debugModeReturnTypeBoolean = false;
		assertTrue(controller.getTurn() == 0);
		while(controller.getCup().getSumOfDice(0) == controller.getCup().getSumOfDice(1)){
			controller.getCup().rollDices();
		}
		controller.handleTurnChange();
		assertTrue(controller.getTurn() == 1);
	}
	
	@Test
	public void testSuccesfulGame(){
		for(int i = 0; i < 1; i++){
			controller = new GameController();
			controller.getPlayerController().createPlayers(new String[]{"Test","Test2", "Test3"});
			for(int x = 0; x < controller.getFieldController().getFields().length; x++){
				controller.getFieldController().setField(x, new Tax(Language.FieldController_Taxfield, Language.FieldController_PayTaxAmount, "", 38, -2000));
			}
			GUIController.isInDebugMode = true;
			GUIController.debugModeReturnTypeString = Language.GameController_EndTurn;
			GUIController.debugModeReturnTypeBoolean = false;
			controller.startGame();
			assertTrue(controller.checkIfGameIsWon());
		}
	}
	
	@Test
	public void testHandleRemovePlayer(){
		controller = new GameController();
		controller.getPlayerController().createPlayers(new String[]{"Test","Test2", "Test3"});
		controller.getPlayerController().getPlayer(0).adjustBalance(-30500);
		GUIController.isInDebugMode = true;
		GUIController.debugModeReturnTypeString = Language.GameController_EndTurn;
		GUIController.debugModeReturnTypeBoolean = false;
		controller.handleRemovePlayer(controller.getPlayerController().getPlayer(0));
		assertEquals(controller.getPlayerController().getPlayerList().size(), 2);
	}
}