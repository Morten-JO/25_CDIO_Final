package controllers;

import java.util.ArrayList;

import field.Field;
import field.Jail;
import playerMO.Player;
import dices.Cup;


public class GameController {
	public PlayerController playerController;
	public FieldController fieldController;
	public ChancecardController chancecardController;
	public Cup cup;
	private boolean gameOver;
	
	public GameController(){
	
	}
	
	

	
	public void startGame(){
		while(!gameOver){
			
		}
	}
	
	public PlayerController getPlayerController(){
		return null;
	}

	public int getTurn() {
		// TODO Auto-generated method stub
		return 0;
	}



	public void setPlayerController(PlayerController playerController) {
		this.playerController = playerController;
	}




	public void setPlayers(ArrayList<String> addPlayers) {
		// TODO Auto-generated method stub
		
	}

	public FieldController getFieldController(){
		return fieldController;
	}




	public Cup getCup() {
		// TODO Auto-generated method stub
		return cup;
	}


	
}
