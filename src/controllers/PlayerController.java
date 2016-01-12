package controllers;

import java.util.ArrayList;

import player.Player;

public class PlayerController {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentPlayer = 0;

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
		if(i<0)i=0;
		return players.get(i);
	}
	
	//get current player object
	public Player getCurrentPlayer(){
		return players.get(currentPlayer);
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

	public int getCurrentPlayerIndex(){
		return currentPlayer;
	}
	
	public int getTotalValueOfPlayer(Player player, FieldController controller) {
		int value = player.getBalance() + controller.getPropertyValue(player);
		return value;
	}
	
	public int getTotalPawnValueOfPlayer(Player player, FieldController controller){
		int value = player.getBalance() + (int)(controller.getPropertyValue(player)*0.5);
		return value;
	}
	
}
