package controllers;

import java.util.ArrayList;

import field.Field;
import field.Jail;
import player.Player;

public class GameController {

	private boolean gameOver;
	private Field[] fields = new Field[40];
	private ArrayList<Player> players;
	public GameController(){
		for(int i = 0; i < fields.length; i++){
			fields[i] = new Jail("j", "g", "b", i);
		}
	}
	
	public void setPlayers(ArrayList<String> players){
		//add players here
	}
	
	public Field[] getFields(){
		return fields;
	}
	
	public void startGame(){
		while(!gameOver){
			
		}
	}
	
	public PlayerController getPlayerController(){
		return null;
	}
	
}
