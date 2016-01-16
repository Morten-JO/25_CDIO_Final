package controllers;

import java.util.ArrayList;

import player.Player;

public class PlayerController {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentPlayer = 0;
	private int jailFreeCardsInPlayers;

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
	
	//used for loop in getmoneyfromallCC, because "for each"-loop was crashing due to mutating array being looped. had to use regular for
	public int getCurrentPlayerIndex(){
		return currentPlayer;
	}
	
	public int getAmountOfJailFreeCardsInPlayers(){
		int amountOfJailFreeCardsAmongstPlayers = 0;
		for(int i = 0; i<players.size();i++){
			amountOfJailFreeCardsAmongstPlayers += players.get(i).getJailFreeCards();
		}
		return amountOfJailFreeCardsAmongstPlayers;
	}
	
	//adds jail free card to current player
	public void addJailFreeCard(){
		int newInt = getCurrentPlayer().getJailFreeCards();
		getCurrentPlayer().setJailFreeCards(newInt++);
		
		if(getCurrentPlayer().getJailFreeCards()>2)
			System.out.println("Player has more than 2 jail free ccs. Handler logic must be wrong in controller");
	}
	
	//removes jail free card from current player
	public void removeJailFreeCard(){
		int newInt = getCurrentPlayer().getJailFreeCards();
		getCurrentPlayer().setJailFreeCards(newInt--);
		
		if(getCurrentPlayer().getJailFreeCards()<0)
			System.out.println("Player has less than 0 jail free ccs. Handler logic must be wrong in controller");
	}
	
	public int getTotalValueOfPlayer(Player player, FieldController fc) {
		int value = player.getBalance() + fc.getPropertyValueNotPawned(player);
		return value;
	}
	
	public int getTotalPawnValueOfPlayer(Player player, FieldController fc){
		int value = player.getBalance() + (int)(fc.getPropertyValueNotPawned(player)*0.5);
		return value;
	}
	
}
