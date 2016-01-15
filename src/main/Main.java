package main;

import controllers.GameController;

public class Main {

	
	public static void main(String[] args) {
		GameController controller = new GameController();
		controller.getGUIController().startGUI(controller);
		controller.startGame();
	}
}
