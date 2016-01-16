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
	private boolean isInTestMode=false; //is working but beware that brewery rent wont work due to dices not being rolled
	
	public GameController(){
		fieldController = new FieldController();
		chanceCardController = new ChanceCardController();
		playerController = new PlayerController();
		guiController = new GUIController();
		cup = new Cup(2, 6);
	}
	
	/**
	 * startGame handles main gameflow, and controls all the parts, and calls the approiate methods so they work properly together
	 */
	public void startGame(){
	
		rollDiceToWhoStarts();
		
		while(!gameOver){
			boolean playerRemoved = false;
			//if same dices hit 3 turns in a row, jail that person
			boolean jailed3Alike = false;
			playerController.setCurrentPlayer(turn);
			if(countDicesTheSame >= 3){
				playerController.getCurrentPlayer().setJailed(true);
				playerController.getCurrentPlayer().setPosition(10); 
				guiController.updatePlayerPositions(playerController.getPlayerList());
				guiController.showMessage(Language.GameController_Jailed3Alike);
				countDicesTheSame = 0;
				jailed3Alike = true;
			}
			else{
				//if he didnt hit same dices 3 turns in a row
				guiController.showMessage(playerController.getCurrentPlayer().getName()+Language.GameController_TurnsToHit);
				//********____________________
				if(isInTestMode){
					int i = guiController.getUserIntegerInput("Enter number of fields you want to move");
					//cup.rollDices();
					//guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
					
					//check if player jailed
					if(playerController.getCurrentPlayer().isJailed()){
						handleIfPlayerJailed();
					}
					else{
						//handle change position, and handle giving player 4000 bonus for getting over start
						boolean startBonus = false;
							int newPosition = playerController.getCurrentPlayer().getPosition() + i;
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
						
						//check if he can afford if that field hes landed on is owned, and if he can afford it
						checkIfPlayerCanAffordToLandOnRented();
						
						if(!fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].landOn(this)){
								//remove player if he couldnt afford it
								if(!handleRemovePlayer(fieldController.getFields()[playerController.getCurrentPlayer().getPosition()] instanceof Ownable)){
									playerRemoved = true;
								}
								
							}
						}
						if(cup.isSameHit()){
							countDicesTheSame++;
						}
						
				}else{
				//*****---------------------------
				cup.rollDices();
				guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
				
				//check if player jailed
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
					
					//check if he can afford if that field hes landed on is owned, and if he can afford it
					checkIfPlayerCanAffordToLandOnRented();
					
					if(!fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].landOn(this)){
							//remove player if he couldnt afford it
							if(!handleRemovePlayer(fieldController.getFields()[playerController.getCurrentPlayer().getPosition()] instanceof Ownable)){
								playerRemoved = true;
							}
							
						}
					}
					if(cup.isSameHit()){
						countDicesTheSame++;
					}
					
					
					
				}//ELSE END FOR TESTMODE!!****
					
					
				}
			//if player not removed, give player a list of options that can be done
			if(!playerRemoved && playerController.getPlayerList().size() > 1){
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
				//convert into an array, so it can be used with the gui
				String[] array = new String[options.size()];
				array = options.toArray(array);
				boolean boughtHouse = false;
				while(stillDoingThings){
					guiController.updateAllOwnerShip(fieldController.getFields());
					guiController.updateAllPlayersBalance(playerController.getPlayerList());
					guiController.updateHouses(fieldController.getFields());
					String option = guiController.askDropDownQuestion(playerController.getCurrentPlayer().getName()+", "+Language.GameController_WhatDoYouWantToDo, array);
					//if trade option is chosen, get list of your properties, and ask what to trade, and to whom, and how much
					if(Language.GameController_Trade.equals(option)){
						handlePlayerGameFlowSellProperty();
					}
					//if pawn option is chosen, give player a list of property to pawn
					else if(Language.GameController_Pawn.equals(option)){
						handlePlayerGameFlowPawnProperty();
					}
					//if buildHouse option is chosen and not already bought a house this round.
					else if(Language.GameController_BuildHouse.equals(option)){
						boughtHouse = handlePlayerGameFlowBuildHouse(boughtHouse);
					}
					else if(Language.GameController_UnPawn.equals(option)){
						handlePlayerGameFlowUnPawnProperty();
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
			//if player wasnt removed, handle turnchange
			if(!playerRemoved && !jailed3Alike){
				handleTurnChange();
			}
			else if(jailed3Alike){
				turnChanceInstant();
			}
			//update players position and balance, and check if winning conditions
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
	private boolean handleRemovePlayer(boolean isStreet){
		if(!isStreet){
			if(playerController.getTotalPawnValueOfPlayer(playerController.getCurrentPlayer(), fieldController) > -playerController.getCurrentPlayer().getBalance()){
				handlePawnPlayer(-playerController.getCurrentPlayer().getBalance(), playerController.getCurrentPlayer());
				return true;
			}
		}
		guiController.showMessage(Language.GameController_CouldntPayOutOfGame);
		guiController.removePlayer(playerController.getCurrentPlayer(), fieldController.getFields());
		guiController.removeOwnerShipFromPlayer(fieldController.getFields(), playerController.getCurrentPlayer());
		fieldController.removeAllOwnershipOfPlayer(playerController.getCurrentPlayer());
		playerController.getPlayerList().remove(playerController.getCurrentPlayer());
		if(turn == playerController.getPlayerList().size()){
			turn = 0;
		}
		guiController.updateAllOwnerShip(fieldController.getFields());
		guiController.updateHouses(fieldController.getFields());
		return false;
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
			playerController.setCurrentPlayer(turn);
		}
		playerController.getPlayerList().remove(player);
		guiController.updateAllOwnerShip(fieldController.getFields());
		guiController.updateHouses(fieldController.getFields());

	}
	
	/**
	 * this method is used for handling when the player cant pay, and must pawn until he has enough
	 * @param toPay
	 * @param player
	 */
	private void handlePawnPlayer(int toPay, Player player){
		boolean canPay = false;
		while(!canPay){
			Field[] fields = fieldController.getAllOwnedProperties(player);
			String[] fieldNames = new String[fields.length];
			for(int i = 0; i < fields.length; i++){
				fieldNames[i] = fields[i].getName();
			}
			String choice = guiController.askDropDownQuestion(player.getName()+Language.GameController_WhatDoYouWantToPawnTwo+", "+Language.GameController_Missing+" "+(toPay), fieldNames);
			for(int i = 0; i < fields.length; i++){
				if(choice == fieldNames[i]){
					if(guiController.askYesNoQuestion(Language.GameController_ConfirmWantToPawn+" "+fieldNames[i]+"?")){
						((Ownable)fields[i]).pawnProperty(this, player);
					}
				}
			}
			guiController.updatePlayerBalance(player);
			if(player.getBalance() >= toPay){
				canPay = true;
			}
		}
	}
	
	//if not same hit, switch turn
	public void handleTurnChange(){
		if(cup.isSameHit()){}
		else{
			turn++;
			if(turn >= playerController.getPlayerList().size()){
				turn = 0;
			}
			countDicesTheSame = 0;
		}
	}
	
	
	public void turnChanceInstant(){
		turn++;
		if(turn >= playerController.getPlayerList().size()){
			turn = 0;
		}
		countDicesTheSame = 0;
	}
	
	/**
	 * handle jailed gameflow, if player hits 2 of the same in 3 tries, he gets out, if not then he have option to buy out for 1000
	 */
	private void handleIfPlayerJailed(){
		if(playerController.getCurrentPlayer().getJailFreeCards() > 0){
			if(guiController.askYesNoQuestion(Language.GameController_WishToUseCard)){
				playerController.removeJailFreeCard();
				playerController.getCurrentPlayer().setJailed(false);
				playerController.getCurrentPlayer().setPosition(10);
				playerController.getCurrentPlayer().setJailedRounds(0);
				guiController.updatePlayerPosition(playerController.getCurrentPlayer());
				guiController.showMessage(Language.GameController_YouAreOutOfJail);
				return;
			}
		}
		if(cup.isSameHit()){
			playerController.getCurrentPlayer().setJailed(false);
			playerController.getCurrentPlayer().setPosition(10);
			playerController.getCurrentPlayer().setJailedRounds(0);
			guiController.showMessage(Language.GameController_YouAreOutOfJail);
		}
		playerController.getCurrentPlayer().setJailedRounds(playerController.getCurrentPlayer().getJailedRounds()+1);
		if(playerController.getCurrentPlayer().isJailed()){
			if(playerController.getCurrentPlayer().getJailedRounds() >= 3){
				if(playerController.getCurrentPlayer().getBalance() >= 1000){
					boolean askQuestion = guiController.askYesNoQuestion(Language.GameController_PayToGetOutOfJail);
					if(askQuestion){
						playerController.getCurrentPlayer().adjustBalance(-1000);
						playerController.getCurrentPlayer().setJailed(false);
						playerController.getCurrentPlayer().setPosition(10);
						playerController.getCurrentPlayer().setJailedRounds(0);
						guiController.updateAllPlayersBalance(playerController.getPlayerList());
						guiController.showMessage(Language.GameController_YouAreOutOfJail);
					}
				}
			}
		}
			
		guiController.updatePlayerPosition(playerController.getCurrentPlayer());
	}

	public boolean checkIfGameIsWon(){
		return gameOver;
	}
	
	private void handleWinningConditions(){
		if(playerController.getPlayerList().size() == 1){
			gameOver = true;
			guiController.showMessage(playerController.getPlayerList().get(0).getName()+" "+Language.GameController_WonGameCongratulations);
			guiController.closeGUI();
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
	
	/**
	 * handles players gameflow for unpawning a property
	 * gets input from player, and list over what he can unpawn
	 */
	private void handlePlayerGameFlowUnPawnProperty(){
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
						((Ownable)fields[i]).unPawnProperty(this, playerController.getCurrentPlayer());
					}
				}
			}
		}
	}
	
	/**
	 * handles players gameflow for building a house on his property
	 * gets input from player, and option over where he build house, then confirms
	 * @param boughtHouse
	 * @return
	 */
	private boolean handlePlayerGameFlowBuildHouse(boolean boughtHouse){
		boolean returnType = boughtHouse;
		//checks weather or not he bought a house this round
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
								returnType = true;
								guiController.updateHouses(fieldController.getFields());
								guiController.updatePlayerBalance(playerController.getCurrentPlayer());
								guiController.showMessage(Language.GameController_CouldBuyHouse+fields[i].getName());
							}
							break;
						}
						else{
							guiController.showMessage(Language.GameController_CouldNotBuyHouse+fields[i].getName());
							break; 
						}
					}
				}
			}
		}
		return returnType;
	}
	
	/**
	 * handles the players gameflow for property, 
	 * gives player list over what he can pawn and takes input
	 */
	private void handlePlayerGameFlowPawnProperty(){
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
	
	
	/**
	 * handles gameflow for a player who is selling a property
	 * shows list over what he can sell, and for how much, and then a
	 * list of the players he can sell to, and that player gets a confirm message
	 * if he wants to accept that offer
	 */
	private void handlePlayerGameFlowSellProperty(){
		Field[] arrayOfTradeFields = fieldController.getAllOwnedPropertiesNotPawned(playerController.getCurrentPlayer());
		String[] fieldsTrade = new String[arrayOfTradeFields.length+1];
		for(int i = 0; i < fieldsTrade.length-1; i++){
			fieldsTrade[i] = arrayOfTradeFields[i].getName();
		}
		fieldsTrade[arrayOfTradeFields.length] = Language.GameController_Cancel;
		String chosenInput = guiController.askDropDownQuestion(Language.GameController_GroundToTrade, fieldsTrade);
		//if not pressed cancel, proceed with selling process
		if(chosenInput != Language.GameController_Cancel){
			Field chosenField = null;
			for(int i = 0; i < fieldsTrade.length; i++){
				if(chosenInput.equals(arrayOfTradeFields[i].getName())){
					chosenField = arrayOfTradeFields[i];
					break;
				}
			}
			if(chosenField != null){
				//confirm that u do intend to sell this property
				if(guiController.askYesNoQuestion(Language.GameController_ConfirmWantToTrade+chosenInput+"?")){
					//ask user how much to sell for
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
					//choose a player, and proceed with the process assuming he had enough money and wants to buy it
					String playerChoice = guiController.askDropDownQuestion(Language.GameController_WhatPlayerToTrade, playerNames);
					for(int i = 0; i < playerNames.length; i++){
						if(playerChoice.equals(playerNames[i])){
							if(players[i].getBalance() >= amount){
								if(guiController.askYesNoQuestion(Language.GameController_DoYou+playerNames[i]+" "+Language.GameController_Buy+" "+chosenField.getName()+" "+Language.GameController_For+" "+amount+"?")){
									if(chosenField instanceof Street){
										if((((Street)chosenField).getAmountOfHotels() + ((Street)chosenField).getAmountOfHouses()) > 0){
											playerController.getCurrentPlayer().adjustBalance(((Street)chosenField).sellAllBuildingsinCat(((Street)chosenField).getStreetCategory(), this));
										}
									}
									playerController.getCurrentPlayer().adjustBalance(amount);
									((Ownable)chosenField).setOwner(players[i]);
									players[i].adjustBalance(-amount);
									guiController.showMessage(players[i].getName()+" "+Language.GameController_BuyGround+" "+chosenField.getName()+" "+Language.GameController_For+" "+amount+".");
									break;
								}
								else{
									guiController.showMessage(Language.GameController_Player+" "+playerNames[i]+" "+Language.GameController_DidntWantToBuy+" "+chosenField.getName()+"!");
									break;
								}
							}
							break;
						}
					}
					
				}
			}
		}
	}
	
	/**
	 * Check if a player can afford to pay money if he lands on a field he has to pay rent for, if not then 
	 * have him pawn what he owns.
	 * This is used so landOn functionally in fields works, and so that it doesnt take his money
	 * and then make him pawn, and make him pay the full amount again
	 */
	private void checkIfPlayerCanAffordToLandOnRented(){
		//check if player has enough money to pay rent on whats he suppoused to land on(this has to be here and not in field
		//so its possible to pawn.
		if(fieldController.getFields()[playerController.getCurrentPlayer().getPosition()] instanceof Ownable){
			if(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getOwner() != null){
				if(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getOwner() != playerController.getCurrentPlayer()){
					if(!((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getIsPawn()){
						if(!(playerController.getCurrentPlayer().getBalance() >= ((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this))){
							if(playerController.getTotalPawnValueOfPlayer(playerController.getCurrentPlayer(), fieldController) > ((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this)){
								guiController.showMessage(Language.GameController_CantPayForLanding+fieldController.getFields()[playerController.getCurrentPlayer().getPosition()].getName()+" "+Language.GameController_WillHaveToPawn);
								handlePawnPlayer(((Ownable)fieldController.getFields()[playerController.getCurrentPlayer().getPosition()]).getRent(this), playerController.getCurrentPlayer());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * handles gameflow for deciding who starts the game
	 */
	private void rollDiceToWhoStarts(){
		guiController.showMessage(Language.GameController_HitWhoStarts);
		boolean foundStarter = false;
		int[] hits = new int[playerController.getPlayerList().size()];
		while(!foundStarter){
			for(int i = 0; i < playerController.getPlayerList().size(); i++){
				if(hits[i] != -1){
					guiController.showMessage(playerController.getPlayerList().get(i).getName()+Language.GameController_TurnToStartRoll);
					cup.rollDices();
					guiController.updateDices(cup.getSumOfDice(0), cup.getSumOfDice(1));
					guiController.showMessage(playerController.getPlayerList().get(i).getName()+" "+Language.GameController_Hit+" "+cup.getDiceSum()+"!");
					hits[i] = cup.getDiceSum();
				}
			}
			int highest = 0;
			for(int i = 0; i < hits.length; i++){
				if(hits[i] != -1){
					if(highest <= hits[i]){
						highest = hits[i];
						for(int x = 0; x < hits.length; x++){
							if(highest > hits[x]){
								hits[x] = -1;
							}
						}
					}
					else{
						hits[i] = -1;
					}
				}
			}
			int countSame = 0;
			for(int i = 0; i < hits.length; i++){
				if(hits[i] != -1){
					if(highest == hits[i]){
						countSame++;
					}
				}
			}
			if(countSame == 1){
				int index = 0;
				for(int i = 0; i < hits.length; i++){
					if(hits[i] != -1){
						if(highest == hits[i]){
							index = i;
						}
					}
				}
				guiController.showMessage(playerController.getPlayer(index).getName()+" "+Language.GameController_HitTheHighest);
				turn = index;
				foundStarter = true;
			}
			else{
				guiController.showMessage(Language.GameController_HitTheSameAndRoll);
			}
			
		}
	}
}
