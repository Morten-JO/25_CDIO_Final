package controllers;

import java.util.ArrayList;

import player.Player;

public class PlayerController {

	public ArrayList<Player> players = new ArrayList<Player>();
	

	public Player getPlayer(int i) {
		return players.get(i);
	}
	public void setPlayers(ArrayList<String> players){
		//add players here
	}


}
