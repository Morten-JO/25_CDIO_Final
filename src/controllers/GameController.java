package controllers;

import java.util.ArrayList;

import field.Field;
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
	private String morten = "Morten er nice";
	
	public GameController(){
		fieldController = new FieldController();
		chanceCardController = new ChanceCardController();
		playerController = new PlayerController();
		guiController = new GUIController(this);
		cup = new Cup(2, 6);
	}
	
	public void startGame(){
		while(!gameOver){
			boolean playerRemoved = false;
			if(countDicesTheSame >= 3){
				playerController.getCurrentPlayer().setJailed(true);
				playerController.getCurrentPlayer().setPosition(10); // dunno where jail is TEMP
				guiController.updatePlayerPositions(playerController.getPlayerList());
				guiController.showMessage("Du er blevet fængslet for at slå 3x 2 ens i træk");
				countDicesTheSame = 0;
			}
			else{
				playerController.setCurrentPlayer(turn);
				guiController.showMessage(playerController.getCurrentPlayer().getName()+"'s tur til at slå!");
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
					if(playerController.getCurrentPlayer().getFirstRoundCompleted()){
						if(startBonus){
							playerController.getCurrentPlayer().adjustBalance(4000);
							guiController.showMessage("Du får 4000 for at komme over start!");
						}
					}
					playerController.getCurrentPlayer().setFirstRoundCompleted(true);
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
					if(fieldController.getFields()[playerController.getCurrentPlayer().getPosition()] instanceof Ownable){
						if(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getOwner() != null){
							if(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getOwner() != playerController.getCurrentPlayer()){
								if(!((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getIsPawn()){
									if(!(playerController.getCurrentPlayer().getBalance() >= ((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this))){
										if(playerController.getTotalValueOfPlayer(playerController.getCurrentPlayer(), fieldController) > ((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this)){
											guiController.showMessage("You cant pay for landing on "+fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].getName()+" and will have to pawn!");
											handlePawnPlayer(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this) - playerController.getCurrentPlayer().getBalance(), playerController.getCurrentPlayer());
										}
									}
								}
							}
						}
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
			if(!playerRemoved){
				ArrayList<String> options = new ArrayList<String>();
				if(!playerController.getCurrentPlayer().isJailed()){
					if(cup.isSameHit()){
						options.add("Kast terning igen");
					}
					else{
						options.add("Slut turen");
					}
					if(fieldController.getPropertyValueNotPawned(playerController.getCurrentPlayer()) > 0){
						options.add("Byt");
						options.add("Pantsæt");
					}
					if(fieldController.ownsEntireStreet(playerController.getCurrentPlayer())){
						options.add("Byg hus");
					}
				}
				else{
					options.add("Slut turen");
				}
				boolean stillDoingThings = true;
				String[] array = new String[options.size()];
				array = options.toArray(array);
				boolean boughtHouse = false;
				while(stillDoingThings){
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
					String option = guiController.askDropDownQuestion("Hvad vil du gøre?", array);
					if("Byt".equals(option)){
						Field[] arrayOfTradeFields = fieldController.getAllOwnedProperties(playerController.getCurrentPlayer());
						String[] fieldsTrade = new String[arrayOfTradeFields.length];
						for(int i = 0; i < fieldsTrade.length; i++){
							fieldsTrade[i] = arrayOfTradeFields[i].getName();
						}
						String chosenInput = guiController.askDropDownQuestion("Hvilken grund vil du bytte?", fieldsTrade);
						Field chosenField = null;
						for(int i = 0; i < fieldsTrade.length; i++){
							if(chosenInput.equals(arrayOfTradeFields[i].getName())){
								chosenField = arrayOfTradeFields[i];
								break;
							}
						}
						if(chosenField != null){
							if(guiController.askYesNoQuestion("Er du sikker på at du vil bytte "+chosenInput+"?")){
								int amount = guiController.getUserIntegerInput("Hvor meget vil du have for "+chosenInput+"?");
								ArrayList<Player> modifiedPlayers = new ArrayList<Player>();
								for(int i = 0; i < playerController.getPlayerList().size(); i++){
									modifiedPlayers.add(playerController.getPlayerList().get(i));
								}
								modifiedPlayers.remove(playerController.getCurrentPlayer());
								Player[] players = new Player[modifiedPlayers.size()];
								players = modifiedPlayers.toArray(players);
								String[] playerNames = new String[players.length];
								for(int i = 0; i < playerNames.length; i++){
									playerNames[i] = players[i].getName();
								}
								String playerChoice = guiController.askDropDownQuestion("Hvad spiller vil du bytte med?", playerNames);
								for(int i = 0; i < playerNames.length; i++){
									if(playerChoice.equals(playerNames[i])){
										if(players[i].getBalance() >= amount){
											if(guiController.askYesNoQuestion("Vil du, "+playerNames[i]+" købe "+chosenField.getName()+" for "+amount+"?")){
												if(chosenField instanceof Street){
													if((((Street)chosenField).getamountOfHotels() + ((Street)chosenField).getamountOfHouses()) > 0){
														((Street)chosenField).sellBuilding(this);
													}
												}
												playerController.getCurrentPlayer().adjustBalance(amount);
												((Ownable)chosenField).setOwner(players[i]);
												players[i].adjustBalance(-amount);
												guiController.showMessage(players[i].getName()+" købte grund "+chosenField.getName()+" for "+amount+".");
											}
											else{
												guiController.showMessage("Spiller "+playerNames[i]+" ville ikke købe "+chosenField.getName()+"!");
											}
										}
										break;
									}
								}
								
							}
						}
					}
					else if("Pantsæt".equals(option)){
						if(guiController.askYesNoQuestion("Vil you gerne pantsætte?")){
							Field[] arrayOfOwnedFields = fieldController.getAllOwnedProperties(playerController.getCurrentPlayer());
							String[] fieldNames = new String[arrayOfOwnedFields.length];
							for(int i = 0; i < fieldNames.length; i++){
								fieldNames[i] = arrayOfOwnedFields[i].getName();
							}
							String choice = guiController.askDropDownQuestion("Hvad grund vil du pantsætte?", fieldNames);
							if(guiController.askYesNoQuestion("Er du sikker på at du vil pantsætte "+choice+"?")){
								for(int i = 0; i < fieldNames.length; i++){
									if(fieldNames[i].equals(arrayOfOwnedFields[i].getName())){
										((Ownable)(arrayOfOwnedFields[i])).pawnProperty(playerController.getCurrentPlayer());
									}
								}
							}
						}
					}
					else if("Byg hus".equals(option)){
						if(boughtHouse){
							guiController.showMessage("Du har allerede købt et hus i denne runde.");
						}
						else{
							Field[] fields = fieldController.getOwnedFullStreets(playerController.getCurrentPlayer(), this);
							String[] strings = new String[fields.length];
							for(int i = 0; i < fields.length; i++){
								strings[i] = fields[i].getName();
							}
							String answer = guiController.askDropDownQuestion("Hvilken grund vil du købe hus på?", strings);
							for(int i = 0; i < strings.length; i++){
								if(answer.equals(strings[i])){
									if(guiController.askYesNoQuestion("Er du sikker du vil købe et hus på "+fields[i].getName()+" for "+((Street)fields[i]).getPrice())){
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
					}
					else{
						stillDoingThings = false;
					}
					options = new ArrayList<String>();
					if(!playerController.getCurrentPlayer().isJailed()){
						if(cup.isSameHit()){
							options.add("Kast terninger igen");
						}
						else{
							options.add("Slut turen");
						}
						if(fieldController.getPropertyValueNotPawned(playerController.getCurrentPlayer()) > 0){
							options.add("Byt");
							options.add("Pantsæt");
						}
						if(fieldController.ownsEntireStreet(playerController.getCurrentPlayer())){
							options.add("Byg hus");
						}
					}
					else{
						options.add("Slut turen");
					}
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
					guiController.updateAllOwnerShip(fieldController.getFields());
					array = new String[options.size()];
					array = options.toArray(array);
				}
			}
			if(!playerRemoved){
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
		guiController.showMessage("Du kunne ikke betale og er nu ude af spillet!");
		guiController.removePlayer(playerController.getCurrentPlayer(), fieldController.getFields());
		playerController.getPlayerList().remove(playerController.getCurrentPlayer());
		if(playerController.getPlayerList().size() > 0){
			turn--;
		}
	}
	
	//ONLY USED FOR CHANCE CARDS
	public void handleRemovePlayer(Player player){
		if(playerController.getTotalValueOfPlayer(player, fieldController) > -player.getBalance()){
			handlePawnPlayer(-player.getBalance(), player);
			return;
		}
		guiController.showMessage("Du kunne ikke betale og er nu ude af spillet!");
		guiController.removePlayer(playerController.getCurrentPlayer(), fieldController.getFields());
		if(turn > playerController.getPlayerList().indexOf(player)){
			turn--;
		}
		playerController.getPlayerList().remove(player);

	}
	
	private void handlePawnPlayer(int toPay, Player player){
		boolean canPay = false;
		while(!canPay){
			Field[] fields = fieldController.getAllOwnedProperties(player);
			String[] fieldNames = new String[fields.length];
			for(int i = 0; i < fields.length; i++){
				fieldNames[i] = fields[i].getName();
			}
			String choice = guiController.askDropDownQuestion(player.getName()+", hvad vil du gerne pantsætte?", fieldNames);
			for(int i = 0; i < fields.length; i++){
				if(choice == fieldNames[i]){
					if(guiController.askYesNoQuestion("Er du sikker på at du vil pantsætte "+fieldNames[i]+"?")){
						((Ownable)fields[i]).pawnProperty(player);
					}
				}
			}
			if(player.getBalance() >= toPay){
				canPay = true;
			}
		}
	}
	
	private void handleTurnChange(){
		if(cup.isSameHit()){}
		else{
			turn++;
			if(turn >= playerController.getPlayerList().size()){
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
			guiController.showMessage("Du har "+(2)+" mere forsøg til at komme ud af fængsel!");
			guiController.showMessage("Kast terningen");
			cup.rollDices();
			guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
			if(cup.isSameHit()){
				playerController.getCurrentPlayer().setJailed(false);
				hittedOut = true;
				guiController.showMessage("Du er kommet ud af fængsel!");
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
				boolean askQuestion = guiController.askYesNoQuestion("Har du lyst til at betale 1000 for at komme ud af fængsel?");
				if(askQuestion){
					playerController.getCurrentPlayer().adjustBalance(-1000);
					playerController.getCurrentPlayer().setJailed(false);
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
				}
			}
			else{
				guiController.showMessage("Du er ude af fængsel!");
			}
		}
	}

	private void handleWinningConditions(){
		if(playerController.getPlayerList().size() == 1){
			gameOver = true;
			guiController.showMessage(playerController.getPlayerList().get(0)+" har vundet spillet, tilykke!");
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
