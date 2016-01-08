package controllers;

import java.util.ArrayList;

import field.Field;
import field.Jail;
import player.Player;
import dices.Cup;


public class GameController {
	
	public int turn;
	private GUIController guiController;
	private PlayerController playerController;
	private FieldController fieldController;
	private ChanceCardController chanceCardController;
	public Cup cup;//WHY ARE TURN AND CUP PUBLIC?
	private boolean gameOver;
	
	public GameController(){
		fieldController = new FieldController();
		chanceCardController = new ChanceCardController();
		playerController = new PlayerController();
		guiController = new GUIController(this);
	}
	
	public void startGame(){
		while(!gameOver){
			
		}
	}
	
	public PlayerController getPlayerController(){
		return playerController;
	}

	public int getTurn() {
		// TODO Auto-generated method stub
		return 0;
	}


	

	public FieldController getFieldController(){
		return fieldController;
	}




	public Cup getCup() {
		// TODO Auto-generated method stub
		return cup;
	}

	public ChanceCardController getChanceCardController(){
		return chanceCardController;
	}

	public int getTotalValueOfPlayer(Player player) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public GUIController getGUIController(){
		return guiController;
	}

	
}
