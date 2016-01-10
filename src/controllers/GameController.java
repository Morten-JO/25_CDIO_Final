package controllers;

import java.util.ArrayList;

import field.Field;
import field.Jail;
import player.Player;
import desktop_resources.GUI;
import dices.Cup;


public class GameController {
	
	private int turn = 0;
	private GUIController guiController;
	private PlayerController playerController;
	private FieldController fieldController;
	private ChanceCardController chanceCardController;
	private Cup cup; 
	private boolean gameOver;
	private int countDicesTheSame = 0;
	
	public GameController(){
		fieldController = new FieldController();
		chanceCardController = new ChanceCardController();
		playerController = new PlayerController();
		guiController = new GUIController(this);
		cup = new Cup(2, 6);
	}
	
	public void startGame(){
		//what is this
		/*for(int i = 0; i<fieldController.getFields().length;i++){
			fieldController.getFields()[i].landOn(this);
			int bal = playerController.getCurrentPlayer().getBalance();
			System.out.println("current balance("+i+"): " + bal);
		}
		*/
		
		while(!gameOver){
			//temp
			boolean playerRemoved = false;
			//temp
			System.out.println("turn is: "+turn);
			if(countDicesTheSame >= 3){
				playerController.getCurrentPlayer().setJailed(true);
				playerController.getCurrentPlayer().setPosition(20); // dunno where jail is TEMP
				guiController.updatePlayerPositions(playerController.getPlayerList());
				guiController.showMessage("You were jailed for hitting 2x same kind 3 rounds in a row");
				countDicesTheSame = 0;
			}
			else{
				playerController.setCurrentPlayer(turn);
				guiController.showMessage(playerController.getCurrentPlayer().getName()+"'s turn to roll dices!");
				cup.rollDices();
				guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
				if(playerController.getCurrentPlayer().isJailed()){
					handleIfPlayerJailed();
				}
				else{
					boolean startBonus = false;
					int newPosition = playerController.getCurrentPlayer().getPosition() + cup.getDiceSum();
					if(newPosition > 39){
						newPosition -= 40;
						if(newPosition >= 1){
							startBonus = true;
						}
					}
					else if(playerController.getCurrentPlayer().getPosition() == 0){
						startBonus = true;
					}
					playerController.getCurrentPlayer().setPosition(newPosition);
					guiController.updatePlayerPositions(playerController.getPlayerList());
					if(startBonus){
						playerController.getCurrentPlayer().adjustBalance(4000);
						guiController.showMessage("You get 4000 for coming over start!");
					}
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
					guiController.showMessage(fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].getDescriptionText());
					if(!fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].landOn(this)){
						if(fieldController.getPropertyValue(playerController.getCurrentPlayer()) > 0){
							handlePawnPlayer();
						}
						if(!fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].landOn(this)){
							handleRemovePlayer();
							playerRemoved = true;
						}
					}
					if(cup.isSameHit()){
						countDicesTheSame++;
					}
					
				}
			}
			if(!playerRemoved){
				System.out.println("handled turn1");
				handleTurnChange();
			}
			playerRemoved = false;
			guiController.updateAllPlayersBalance(playerController.getPlayerList());
			guiController.updatePlayerPositions(playerController.getPlayerList());
			handleWinningConditions();
		}
	}
	
	public PlayerController getPlayerController(){
		return playerController;
	}

	public int getTurn() {
		return turn;
	}
	
	public FieldController getFieldController(){
		return fieldController;
	}

	private void handleRemovePlayer(){
		guiController.showMessage("You couldnt pay and are now out of the game!");
		guiController.removePlayer(playerController.getCurrentPlayer(), fieldController.getFields());
		playerController.getPlayerList().remove(playerController.getCurrentPlayer());
		if(playerController.getPlayerList().size() > 0){
			turn--;
		}
	}
	
	private void handlePawnPlayer(){
		guiController.showMessage("You couldnt pay and have to pawn");
		guiController.showMessage("pawing isnt created yet, TEMP!");
		
	}
	
	private void handleTurnChange(){
		if(cup.isSameHit()){
			System.out.println("was same hit!");
		}
		else{
			System.out.println("wasnt same hit, switching turns");
			turn++;
			if(turn >= playerController.getPlayerList().size()){
				System.out.println("setting turn to 0");
				turn = 0;
			}
			countDicesTheSame = 0;
		}
	}
	
	private void handleIfPlayerJailed(){
		boolean hittedOut = false;
		int hits = 1;
		if(cup.isSameHit()){
			playerController.getCurrentPlayer().setJailed(false);
			hittedOut = true;
		}
		while(!hittedOut){
			guiController.showMessage("You have "+(2)+" more tries to get out of jail!");
			guiController.showMessage("Roll the dice");
			cup.rollDices();
			guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
			if(cup.isSameHit()){
				playerController.getCurrentPlayer().setJailed(false);
				hittedOut = true;
				GUI.showMessage("You are out of jail!");
			}
			else{
				hits++;
			}
			if(hits >= 3){
				hittedOut = true;
			}
		}
		if(playerController.getCurrentPlayer().isJailed()){
			if(playerController.getCurrentPlayer().getBalance() >= 1000){
				boolean askQuestion = guiController.askYesNoQuestion("Would you like to pay to get out of jail for 1000?");
				if(askQuestion){
					playerController.getCurrentPlayer().adjustBalance(-1000);
					playerController.getCurrentPlayer().setJailed(false);
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
				}
			}
			else{
				GUI.showMessage("You are out of jail!");
			}
		}
	}

	private void handleWinningConditions(){
		if(playerController.getPlayerList().size() == 1){
			gameOver = true;
			guiController.showMessage(playerController.getPlayerList().get(0)+" have won the game, congratulations!");
		}
	}

	public Cup getCup() {
		return cup;
	}

	public ChanceCardController getChanceCardController(){
		return chanceCardController;
	}

	
	public GUIController getGUIController(){
		return guiController;
	}

	
}
