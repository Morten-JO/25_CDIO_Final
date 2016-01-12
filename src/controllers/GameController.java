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
	
	public GameController(){
		fieldController = new FieldController();
		chanceCardController = new ChanceCardController();
		playerController = new PlayerController();
		guiController = new GUIController();
		cup = new Cup(2, 6);
	}
	
	public void startGame(){
		while(!gameOver){
			boolean playerRemoved = false;
			//if 2x same dices hit 3 turns in a row, JAIL EM
			if(countDicesTheSame >= 3){
				playerController.getCurrentPlayer().setJailed(true);
				playerController.getCurrentPlayer().setPosition(10); 
				guiController.updatePlayerPositions(playerController.getPlayerList());
				guiController.showMessage(Language.GameController_Jailed3Alike);
				countDicesTheSame = 0;
			}
			else{
				//change player, and make him hit the dice!
				playerController.setCurrentPlayer(turn);
				guiController.showMessage(playerController.getCurrentPlayer().getName()+Language.GameController_TurnsToHit);
				cup.rollDices();
				guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
				if(playerController.getCurrentPlayer().isJailed()){
					handleIfPlayerJailed();
				}
				else{
					//handle change position, and handle giving player 4000 bonus for getting over start
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
								guiController.showMessage(Language.GameController_StartBonus);
						}
					}
					playerController.getCurrentPlayer().setFirstRoundCompleted(true);
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
					//check if player has enough money to pay rent on whats he suppoused to land on(this has to be here and not in field
					//so its possible to pawn.
					if(fieldController.getFields()[playerController.getCurrentPlayer().getPosition()] instanceof Ownable){
						if(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getOwner() != null){
							if(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getOwner() != playerController.getCurrentPlayer()){
								if(!((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getIsPawn()){
									if(!(playerController.getCurrentPlayer().getBalance() >= ((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this))){
										if(playerController.getTotalPawnValueOfPlayer(playerController.getCurrentPlayer(), fieldController) > ((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this)){
											guiController.showMessage(Language.GameController_CantPayForLanding+fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].getName()+" "+Language.GameController_WillHaveToPawn);
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
			//if player not removed, give player a list of options that can be done
			if(!playerRemoved){
				ArrayList<String> options = new ArrayList<String>();
				if(!playerController.getCurrentPlayer().isJailed()){
					if(cup.isSameHit()){
						options.add(Language.GameController_CastDiceAgain);
					}
					else{
						options.add(Language.GameController_EndTurn);
					}
					if(fieldController.getPropertyValueNotPawned(playerController.getCurrentPlayer()) > 0){
						options.add(Language.GameController_Trade);
						options.add(Language.GameController_Pawn);
					}
					if(fieldController.ownsEntireStreet(playerController.getCurrentPlayer())){
						options.add(Language.GameController_BuildHouse);
					}
					if(fieldController.ownsAnyPawnedProperties(playerController.getCurrentPlayer())){
						options.add(Language.GameController_UnPawn);
					}
				}
				else{
					options.add(Language.GameController_EndTurn);
				}
				boolean stillDoingThings = true;
				String[] array = new String[options.size()];
				array = options.toArray(array);
				boolean boughtHouse = false;
				while(stillDoingThings){
					guiController.updateAllOwnerShip(fieldController.getFields());
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
					guiController.updateHouses(fieldController.getFields());
					String option = guiController.askDropDownQuestion(Language.GameController_WhatDoYouWantToDo, array);
					//if trade option is chosen, get list of your properties, and ask what to trade, and to whom, and how much
					if(Language.GameController_Trade.equals(option)){
						Field[] arrayOfTradeFields = fieldController.getAllOwnedPropertiesNotPawned(playerController.getCurrentPlayer());
						String[] fieldsTrade = new String[arrayOfTradeFields.length+1];
						for(int i = 0; i < fieldsTrade.length-1; i++){
							fieldsTrade[i] = arrayOfTradeFields[i].getName();
						}
						fieldsTrade[arrayOfTradeFields.length] = Language.GameController_Cancel;
						String chosenInput = guiController.askDropDownQuestion(Language.GameController_GroundToTrade, fieldsTrade);
						if(chosenInput != Language.GameController_Cancel){
							Field chosenField = null;
							for(int i = 0; i < fieldsTrade.length; i++){
								if(chosenInput.equals(arrayOfTradeFields[i].getName())){
									chosenField = arrayOfTradeFields[i];
									break;
								}
							}
							if(chosenField != null){
								if(guiController.askYesNoQuestion(Language.GameController_ConfirmWantToTrade+chosenInput+"?")){
									int amount = guiController.getUserIntegerInput(Language.GameController_AmountForTrade+chosenInput+"?");
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
									String playerChoice = guiController.askDropDownQuestion(Language.GameController_WhatPlayerToTrade, playerNames);
									for(int i = 0; i < playerNames.length; i++){
										if(playerChoice.equals(playerNames[i])){
											if(players[i].getBalance() >= amount){
												if(guiController.askYesNoQuestion(Language.GameController_DoYou+playerNames[i]+" "+Language.GameController_Buy+" "+chosenField.getName()+" "+Language.GameController_For+" "+amount+"?")){
													if(chosenField instanceof Street){
														if((((Street)chosenField).getAmountOfHotels() + ((Street)chosenField).getAmountOfHouses()) > 0){
															((Street)chosenField).sellBuilding(this);
														}
													}
													playerController.getCurrentPlayer().adjustBalance(amount);
													((Ownable)chosenField).setOwner(players[i]);
													players[i].adjustBalance(-amount);
													guiController.showMessage(players[i].getName()+" "+Language.GameController_BuyGround+" "+chosenField.getName()+" "+Language.GameController_For+" "+amount+".");
												}
												else{
													guiController.showMessage(Language.GameController_Player+" "+playerNames[i]+" "+Language.GameController_DidntWantToBuy+" "+chosenField.getName()+"!");
												}
											}
											break;
										}
									}
									
								}
							}
						}
					}
					//if pawn option is chosen, give player a list of property to pawn
					else if(Language.GameController_Pawn.equals(option)){
						Field[] arrayOfOwnedFields = fieldController.getAllOwnedPropertiesNotPawned(playerController.getCurrentPlayer());
						String[] fieldNames = new String[arrayOfOwnedFields.length+1];
						for(int i = 0; i < fieldNames.length-1; i++){
							fieldNames[i] = arrayOfOwnedFields[i].getName();
						}
						fieldNames[arrayOfOwnedFields.length] = Language.GameController_Cancel;
						String choice = guiController.askDropDownQuestion(Language.GameController_WhatGroundToPawn, fieldNames);
						if(choice != Language.GameController_Cancel){
							if(guiController.askYesNoQuestion(Language.GameController_ConfirmWantToPawn+" "+choice+"?")){
								for(int i = 0; i < fieldNames.length; i++){
									if(choice == fieldNames[i]){
										((Ownable)(arrayOfOwnedFields[i])).pawnProperty(this, playerController.getCurrentPlayer());
									}
								}
							}	
						}
					}
					//if buildHouse option is chosen and not already bought a house this round.
					else if(Language.GameController_BuildHouse.equals(option)){
						if(boughtHouse){
							guiController.showMessage(Language.GameController_AlreadyBoughtHouse+".");
						}
						else{
							Field[] fields = fieldController.getOwnedFullStreets(playerController.getCurrentPlayer(), this);
							String[] strings = new String[fields.length+1];
							for(int i = 0; i < strings.length-1; i++){
								strings[i] = fields[i].getName();
							}
							strings[fields.length] = Language.GameController_Cancel;
							String answer = guiController.askDropDownQuestion(Language.GameController_WhatGroundBuyHouse, strings);
							if(answer != Language.GameController_Cancel){
								for(int i = 0; i < strings.length; i++){
									if(answer.equals(strings[i])){
										if(guiController.askYesNoQuestion(Language.GameController_ConfirmBuyHouse+" "+fields[i].getName()+" "+Language.GameController_For+" "+((Street)fields[i]).getBuildingPrice())){
											if(((Street)fieldController.getFields()[fields[i].getNumber()]).buyBuilding(this)){
												boughtHouse = true;
												guiController.updateHouses(fieldController.getFields());
											}
											break;
										}
										else{
											break; 
										}
									}
								}
							}
						}
					}
					else if(Language.GameController_UnPawn.equals(option)){
						Field[] fields = fieldController.getListOfUnPawnableProperties(playerController.getCurrentPlayer());
						String[] strings = new String[fields.length+1];
						for(int i = 0; i < strings.length-1; i++){
							strings[i] = fields[i].getName();
						}
						strings[fields.length] = Language.GameController_Cancel;
						String answer = guiController.askDropDownQuestion(Language.GameController_WantToUnpawn, strings);
						if(answer != Language.GameController_Cancel){
							for(int i = 0; i < strings.length; i++){
								if(answer.equals(strings[i])){
									if(guiController.askYesNoQuestion(Language.GameController_ConfirmUnpawn+" "+fields[i].getName()+" "+Language.GameController_For+" "+((Ownable)fields[i]).getPawnPrice())){
										//UNPAWN BUILDING HERE
									}
								}
							}
						}
					}
					else{
						stillDoingThings = false;
					}
					options = new ArrayList<String>();
					//if not jailed, update options
					if(!playerController.getCurrentPlayer().isJailed()){
						if(cup.isSameHit()){
							options.add(Language.GameController_CastDiceAgain);
						}
						else{
							options.add(Language.GameController_EndTurn);
						}
						if(fieldController.getPropertyValueNotPawned(playerController.getCurrentPlayer()) > 0){
							options.add(Language.GameController_Trade);
							options.add(Language.GameController_Pawn);
						}
						if(fieldController.ownsEntireStreet(playerController.getCurrentPlayer())){
							options.add(Language.GameController_BuildHouse);
						}
						if(fieldController.ownsAnyPawnedProperties(playerController.getCurrentPlayer())){
							options.add(Language.GameController_UnPawn);
						}
					}
					else{
						options.add(Language.GameController_EndTurn);
					}
					//update
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

	
	//Removes player from the game
	private void handleRemovePlayer(){
		guiController.showMessage(Language.GameController_CouldntPayOutOfGame);
		guiController.removePlayer(playerController.getCurrentPlayer(), fieldController.getFields());
		guiController.removeOwnerShipFromPlayer(fieldController.getFields(), playerController.getCurrentPlayer());
		fieldController.removeAllOwnershipOfPlayer(playerController.getCurrentPlayer());
		playerController.getPlayerList().remove(playerController.getCurrentPlayer());
		if(playerController.getPlayerList().size() > 0){
			turn--;
		}
	}
	
	
	//Check if getTotalValue of player(with property) is over how much he is owed(this is only used by chance cards)
	//then make him pawn, and if he cant then he loses
	public void handleRemovePlayer(Player player){
		if(playerController.getTotalPawnValueOfPlayer(player, fieldController) > -player.getBalance()){
			handlePawnPlayer(-player.getBalance(), player);
			return;
		}
		guiController.showMessage(Language.GameController_CouldntPayOutOfGame);
		guiController.removePlayer(playerController.getCurrentPlayer(), fieldController.getFields());
		guiController.removeOwnerShipFromPlayer(fieldController.getFields(), player);
		fieldController.removeAllOwnershipOfPlayer(player);
		if(turn > playerController.getPlayerList().indexOf(player)){
			turn--;
		}
		playerController.getPlayerList().remove(player);

	}
	
	//while he doesnt have enough topay, keep pawning
	//select all his property
	private void handlePawnPlayer(int toPay, Player player){
		boolean canPay = false;
		while(!canPay){
			Field[] fields = fieldController.getAllOwnedProperties(player);
			String[] fieldNames = new String[fields.length];
			for(int i = 0; i < fields.length; i++){
				fieldNames[i] = fields[i].getName();
			}
			String choice = guiController.askDropDownQuestion(player.getName()+Language.GameController_WhatDoYouWantToPawnTwo, fieldNames);
			for(int i = 0; i < fields.length; i++){
				if(choice == fieldNames[i]){
					if(guiController.askYesNoQuestion(Language.GameController_ConfirmWantToPawn+" "+fieldNames[i]+"?")){
						((Ownable)fields[i]).pawnProperty(this, player);
					}
				}
			}
			if(player.getBalance() >= toPay){
				canPay = true;
			}
		}
	}
	
	//if not same hit, switch turn
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
	
	//handle gameflow if player is jailed, will check if he hits the same, and if he doesnt, he will get chance to pay 1000 to get out.
	private void handleIfPlayerJailed(){
		boolean hittedOut = false;
		int hits = 1;
		if(cup.isSameHit()){
			playerController.getCurrentPlayer().setJailed(false);
			playerController.getCurrentPlayer().setPosition(10);
			hittedOut = true;
		}
		while(!hittedOut){
			guiController.showMessage(Language.GameController_YouHave+" "+(3-hits)+" "+Language.GameController_AttemptsOutOfJail);
			guiController.showMessage(Language.GameController_RollTheDice);
			cup.rollDices();
			guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
			if(cup.isSameHit()){
				playerController.getCurrentPlayer().setJailed(false);
				playerController.getCurrentPlayer().setPosition(10);
				hittedOut = true;
				guiController.showMessage(Language.GameController_YouAreOutOfJail);
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
				boolean askQuestion = guiController.askYesNoQuestion(Language.GameController_PayToGetOutOfJail);
				if(askQuestion){
					playerController.getCurrentPlayer().adjustBalance(-1000);
					playerController.getCurrentPlayer().setJailed(false);
					playerController.getCurrentPlayer().setPosition(10);
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
				}
			}
			else{
				guiController.showMessage(Language.GameController_YouAreOutOfJail);
			}
		}
			
		guiController.updatePlayerPosition(playerController.getCurrentPlayer());
	}

	private void handleWinningConditions(){
		if(playerController.getPlayerList().size() == 1){
			gameOver = true;
			guiController.showMessage(playerController.getPlayerList().get(0)+" "+Language.GameController_WonGameCongratulations);
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
