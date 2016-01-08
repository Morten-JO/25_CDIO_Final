package controllers;

import java.util.ArrayList;

import player.Player;

public class PlayerController {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentPlayer;

	public PlayerController() {
		setCurrentPlayer(0);
	}
	
	public void createPlayers(String[] names){
		for(int i = 0; i<names.length; i++){
			players.add(new Player(names[i]));
		}
	}
	
	//get a specified Player object
	public Player getPlayer(int i){
		return players.get(i);
	}
	
	//index of current player
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
	//set index of current player
	public void setCurrentPlayer(int i){
		currentPlayer = i;
	}
	
	public ArrayList<Player> getPlayerList(){
		return players;
	}
	
	/*
	//CONVENIENCE METHODS - TO AVOID LONG CALLS
	public int getBalance(int i){
		return players.get(i).getAcc().getBalance();
	}
	
	public boolean adjustBalance(int i, int amount){
		return players.get(i).getAcc().adjustBalance(amount);
	}*/

}
