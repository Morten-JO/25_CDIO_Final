package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.GUIController;
import controllers.GameController;
import controllers.Language;

/**
 * Date: 12/01/2016
 *
 * Project: 25_cdio-final
 *
 * File: TestGameController.java
 *
 * Created by: Morten Jørvad
 *
 * Github: https://github.com/Mortenbaws
 *
 * Email: morten2094@gmail.com
 */

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
		GUIController.debugModeReturnTypeBoolean = true;
		assertTrue(controller.getTurn() == 0);
		while(controller.getCup().getSumOfDice(0) == controller.getCup().getSumOfDice(1)){
			controller.getCup().rollDices();
		}
		controller.handleTurnChange();
		assertTrue(controller.getTurn() == 1);
	}
}