package controllers;

import java.util.ArrayList;

import field.Field;
import field.Jail;
import field.Street;
import field.Ownable;
import player.Player;
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
			
			ArrayList<String> options = new ArrayList<String>();
			if(fieldController.getPropertyValueNotPawned(playerController.getCurrentPlayer()) > 0){
				options.add("Trade");
				options.add("Pawn");
			}
			if(fieldController.ownsEntireStreet(playerController.getCurrentPlayer())){
				options.add("Build house");
			}
			options.add("End turn");
			boolean stillDoingThings = true;
			String[] array = new String[options.size()];
			array = options.toArray(array);
			boolean boughtHouse = false;
			while(stillDoingThings){
				String option = guiController.askDropDownQuestion("What would you like to do?", array);
				switch(option){
					case "Trade":
						Field[] arrayOfTradeFields = fieldController.getAllOwnedProperties(playerController.getCurrentPlayer());
						String[] fieldsTrade = new String[arrayOfTradeFields.length];
						for(int i = 0; i < fieldsTrade.length; i++){
							fieldsTrade[i] = arrayOfTradeFields[i].getName();
						}
						String chosenInput = guiController.askDropDownQuestion("Which property would you like to trade?", fieldsTrade);
						Field chosenField = null;
						for(int i = 0; i < fieldsTrade.length; i++){
							if(chosenInput.equals(arrayOfTradeFields[i].getName())){
								chosenField = arrayOfTradeFields[i];
								break;
							}
						}
						if(chosenField != null){
							if(guiController.askYesNoQuestion("Are you sure you want to trade "+chosenInput+"?")){
								int amount = guiController.getUserIntegerInput("How much do you want for "+chosenInput+"?");
								Player[] players = new Player[playerController.getPlayerList().size()];
								players = playerController.getPlayerList().toArray(players);
								String[] playerNames = new String[players.length];
								for(int i = 0; i < playerNames.length; i++){
									playerNames[i] = players[i].getName();
								}
								String playerChoice = guiController.askDropDownQuestion("What player would you like to trade to?", playerNames);
								for(int i = 0; i < playerNames.length; i++){
									if(playerChoice.equals(playerNames[i])){
										if(players[i].getBalance() >= amount){
											if(guiController.askYesNoQuestion("Do you, "+playerNames[i]+" want to buy "+chosenField.getName()+" for "+amount+"?")){
												if(chosenField instanceof Street){
													if((((Street)chosenField).getamountOfHotels() + ((Street)chosenField).getamountOfHouses()) > 0){
														((Street)chosenField).sellBuilding(this);
													}
												}
												playerController.getCurrentPlayer().adjustBalance(amount);
												((Ownable)chosenField).setOwner(players[i]);
												players[i].adjustBalance(-amount);
												guiController.showMessage(players[i].getName()+" bought property "+chosenField.getName()+" for "+amount+".");
											}
											else{
												guiController.showMessage("Player "+playerNames[i]+" didnt want to buy that property!");
											}
										}
										break;
									}
								}
								
							}
						}
						break;
					case "Pawn":
						if(guiController.askYesNoQuestion("Would you like to pawn?")){
							Field[] arrayOfOwnedFields = fieldController.getAllOwnedProperties(playerController.getCurrentPlayer());
							String[] fieldNames = new String[arrayOfOwnedFields.length];
							for(int i = 0; i < fieldNames.length; i++){
								fieldNames[i] = arrayOfOwnedFields[i].getName();
							}
							String choice = guiController.askDropDownQuestion("Which would you like to pawn?", fieldNames);
							if(guiController.askYesNoQuestion("Are you sure you want to pawn "+choice+"?")){
								for(int i = 0; i < fieldNames.length; i++){
									if(fieldNames[i].equals(arrayOfOwnedFields[i].getName())){
										((Ownable)(arrayOfOwnedFields[i])).pawnProperty(playerController.getCurrentPlayer());
									}
								}
							}
						}
						else{
							
						}
						break;
					case "Build house":
						if(boughtHouse){
							guiController.showMessage("You have already bought a house this round.");
						}
						else{
							Field[] fields = fieldController.getOwnedFullStreets(playerController.getCurrentPlayer(), this);
							String[] strings = new String[fields.length];
							for(int i = 0; i < fields.length; i++){
								strings[i] = fields[i].getName();
							}
							String answer = guiController.askDropDownQuestion("Which would you like to buy a house on?", strings);
							for(int i = 0; i < strings.length; i++){
								if(answer.equals(strings[i])){
									if(guiController.askYesNoQuestion("Would you like to buy a house on "+fields[i].getName()+" for "+((Street)fields[i]).getPrice())){
										((Street)fieldController.getFields()[fields[i].getNumber()]).buyBuilding(this);
										guiController.updateHouses(fieldController.getFields());
										boughtHouse = true;
										break;
									}
									else{
										break; 
									}
								}
							}
						}
						break;
					case "End turn":
						stillDoingThings = false;
						break;
				}
				options = new ArrayList<String>();
				if(fieldController.getPropertyValueNotPawned(playerController.getCurrentPlayer()) > 0){
					System.out.println(fieldController.getPropertyValue(playerController.getCurrentPlayer()));
					options.add("Trade");
					options.add("Pawn");
				}
				if(fieldController.ownsEntireStreet(playerController.getCurrentPlayer())){
					options.add("Build house");
				}
				options.add("End turn");
				guiController.updateAllPlayersBalance(playerController.getPlayerList());
				array = new String[options.size()];
				array = options.toArray(array);
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
	
	public void handleRemovePlayer(int playerId){
		guiController.showMessage("You couldnt pay and are now out of the game!");
		guiController.removePlayer(playerController.getCurrentPlayer(), fieldController.getFields());
		if(turn > playerId){
			turn--;
		}
		playerController.getPlayerList().remove(playerId);

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
				guiController.showMessage("You are out of jail!");
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
				guiController.showMessage("You are out of jail!");
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
