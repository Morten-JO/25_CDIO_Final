package controllers;

import java.util.ArrayList;

import field.Field;
import field.Jail;
import player.Player;
import dices.Cup;


public class GameController {
	
	public int turn;
	public PlayerController playerController;
	public FieldController fieldController;
	public ChancecardController chanceCardController;
	public Cup cup;
	private boolean gameOver;
	
	public GameController(){
		fieldController = new FieldController();
		chanceCardController = new ChanceCardController();
		
	}

	public void setPlayers(ArrayList<String> addPlayers) {
		String[] names = new String[addPlayers.size()];
		names = addPlayers.toArray(names);
		playerController = new PlayerController(names);
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


	
}
