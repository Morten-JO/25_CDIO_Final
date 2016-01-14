package controllers;

import java.awt.Color;
import java.util.ArrayList;

import player.Player;
import desktop_codebehind.Car;
import desktop_resources.GUI;
import field.*;


public class GUIController {	
	
	public static boolean isInDebugMode = false;
	public static boolean debugModeReturnTypeBoolean = false;
	public static String debugModeReturnTypeString = "";
	public static int debugModeReturnTypeInt = 0;
	
	public GUIController(){
		
	}
	
	public void startGUI(GameController game){
		GUI.create(createList(game.getFieldController().getFields()));
		GUI.showMessage(Language.GUIController_WelcomeToMatador);
		ArrayList<String> addPlayers = choosePlayers();
		String[] names = new String[addPlayers.size()];
		names = addPlayers.toArray(names);
		game.getPlayerController().createPlayers(names);
	}
	
	//adds players from minimum 3 to maximum 6, also asks the player to personalize their car
	private ArrayList<String> choosePlayers(){
		ArrayList<String> players = new ArrayList<String>();
		boolean stillAdding = true;
		//while still adding players
		Color[] carColor = {Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.MAGENTA, Color.LIGHT_GRAY, Color.PINK, Color.WHITE};
		while(stillAdding){
			String input = GUI.getUserString(Language.GUIController_AddPlayer+(players.size()+1)+Language.GUIController_sName);
			if(!input.equals("")){
				boolean isSame = false;
				if(players.size() > 0){
					for(int i = 0; i < players.size(); i++){
						if(input.equals(players.get(i))){
							isSame = true;
						}
					}
				}
				if(isSame){
					GUI.showMessage(Language.GUIController_TypeUniqueName);
				}
				else{
					players.add(input);
					boolean carQuestion = this.askYesNoQuestion(Language.GUIController_CarPersonal);
					if(carQuestion){
						//get input for what options are availble
						String[] carChoices = {Language.GUIController_Normal, Language.GUIController_RacerCar, Language.GUIController_Tractor, Language.GUIController_Ufo};
						String[] colorChoices = {Language.GUIController_Black, Language.GUIController_Red, Language.GUIController_Yellow, Language.GUIController_Green, Language.GUIController_Orange, Language.GUIController_Blue, Language.GUIController_CYAN, Language.GUIController_DARK_GRAY, Language.GUIController_GRAY, Language.GUIController_MAGENTA, Language.GUIController_LIGHT_GRAY, Language.GUIController_PINK, Language.GUIController_WHITE};
						String[] patternChoices = {Language.GUIController_Checkered, Language.GUIController_DiagonalDualColor, Language.GUIController_Dotted, Language.GUIController_Fill, Language.GUIController_HorizontalDualColor, Language.GUIController_Gradient, Language.GUIController_Line, Language.GUIController_Zebra};
						String carChoice = GUI.getUserSelection(Language.GUIController_ChooseCarType, carChoices);
						String color = GUI.getUserSelection(Language.GUIController_ChoosePrimaryColor, colorChoices);
						String secondaryColor = GUI.getUserSelection(Language.GUIController_ChooseSecondaryColor, colorChoices);
						String patternType = GUI.getUserSelection(Language.GUIController_ChooseCarPattern, patternChoices);
						//Color[] carColor = {Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.BLUE};
						
						Car.Builder car = null;
						//handle input for car type choice
						for(int i = 0; i < carChoices.length; i++){
							if(carChoices[i].equals(carChoice)){
								switch(i){
								case 0:
									car = new Car.Builder().typeCar();
									break;
								case 1:
									car = new Car.Builder().typeRacecar();
									break;
								case 2:
									car = new Car.Builder().typeTractor();
									break;
								case 3:
									car = new Car.Builder().typeUfo();
									break;
								}
								break;
							}
						}
						//handle input for primary color choice
						for(int i = 0; i < colorChoices.length; i++){
							if(colorChoices[i].equals(color)){
								car.primaryColor(carColor[i]);
								break;
							}
						}
						//handle input for secondary color choice
						for(int i = 0; i < colorChoices.length; i++){
							if(colorChoices[i].equals(secondaryColor)){
								car.secondaryColor(carColor[i]);
								break;
							}
						}
						//handle input for pattern choice
						for(int i = 0; i < patternChoices.length; i++){
							if(patternChoices[i].equals(patternType)){
								switch(i){
								case 0:
									car.patternCheckered();
									break;
								case 1:
									car.patternDiagonalDualColor();
									break;
								case 2:
									car.patternDotted();
									break;
								case 3:
									car.patternFill();
									break;
								case 4:
									car.patternHorizontalDualColor();
									break;
								case 5:
									car.patternHorizontalGradiant();
									break;
								case 6:
									car.patternHorizontalLine();
									break;
								case 7:
									car.patternZebra();
									break;
								}
							}
						}
						Car buildedCar = car.build();

						GUI.addPlayer(input, 30000, buildedCar);
						GUI.showMessage(input+" "+Language.GUIController_Added);
					}
					else{
						 Car.Builder car = new Car.Builder().primaryColor(carColor[players.size()-1]);
						GUI.addPlayer(input, 3000, car.build());
						GUI.showMessage(input+" "+Language.GUIController_Added);
					}
					if(players.size() >= 6){
						stillAdding = false;
					}
					else if(players.size() >= 3){
						boolean answer = this.askYesNoQuestion(Language.GUIController_AddAnotherPlayer);
						if(!answer){
							stillAdding = false;
						}
					}
					
				}
			}
			else{
				GUI.showMessage(Language.GUIController_ValidName);
			}
		}
		return players;
	}
	
	//Creates the graphical "Board" from the GameFields
	public desktop_fields.Field[] createList(field.Field[] arrayOfFields){
		desktop_fields.Field[] list = new desktop_fields.Field[arrayOfFields.length];
		for(int i = 0; i < list.length; i++){
			if(arrayOfFields[i].getClass().equals(Start.class)){
				list[i] = new desktop_fields.Start.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Chance.class)){
				list[i] = new desktop_fields.Chance.Builder().setBgColor(Color.BLACK).setFgColor(Color.GREEN).build();
			}
			else if(arrayOfFields[i].getClass().equals(Jail.class)){
				list[i] = new desktop_fields.Jail.Builder().build();
			}
			else if(arrayOfFields[i] instanceof field.Tax){
				list[i] = new desktop_fields.Tax.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Bonus.class)){
				list[i] = new desktop_fields.Refuge.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(VisitJail.class)){
				list[i] = new desktop_fields.Jail.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Fleet.class)){
				list[i] = new desktop_fields.Shipping.Builder().setRent("").build();
			}
			else if(arrayOfFields[i].getClass().equals(Street.class)){
				//set colours based on category of the streets
				desktop_fields.Street.Builder builder = new desktop_fields.Street.Builder();
				switch(((field.Street)arrayOfFields[i]).getStreetCategory()){
					case 0:
						builder.setBgColor(Color.BLUE);
						break;
					case 1:
						builder.setBgColor(Color.ORANGE);
						break;
					case 2:
						builder.setBgColor(Color.GREEN);
						break;
					case 3:
						builder.setBgColor(Color.GRAY);
						break;
					case 4:
						builder.setBgColor(Color.RED);
						break;
					case 5:
						builder.setBgColor(Color.WHITE);
						break;
					case 6:
						builder.setBgColor(Color.YELLOW);
						break;
					case 7:
						builder.setBgColor(Color.MAGENTA);
						break;
				}
				builder.setRent("");
				list[i] = builder.build();
			}
			else if(arrayOfFields[i].getClass().equals(Brewery.class)){
				Color color = new Color(0.5f,0.1f,0.2f,1.0f);
				list[i] = new desktop_fields.Brewery.Builder().setRent("").setBgColor(color).build();
			}
			else{
				try {
					throw new Exception(Language.GUIController_AFieldWasNotIndentified);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			list[i].setDescription(arrayOfFields[i].getName());
			list[i].setSubText(arrayOfFields[i].getSubText());
			list[i].setTitle(arrayOfFields[i].getName());
		}
		return list;
	}
	
	//Ask questions from a list of questions
	public String askQuestion(String question, String... answers){
		if(isInDebugMode){
			return debugModeReturnTypeString;
		}
		return GUI.getUserButtonPressed(question, answers);
	}
	
	//Asks a yes/no question
	public Boolean askYesNoQuestion(String message){
		if(isInDebugMode){
			return debugModeReturnTypeBoolean;
		}
		return GUI.getUserLeftButtonPressed(message, Language.GUIController_Yes, Language.GUIController_No);
	}
	
	//updates all the players positions on the board
	public void updatePlayerPositions(ArrayList<Player> players){
		if(isInDebugMode){
			return;
		}
		for(int i = 0; i < players.size(); i++){
			GUI.removeAllCars(players.get(i).getName());
			GUI.setCar(players.get(i).getPosition()+1, players.get(i).getName());
		}
	}

	//Update a specific players balance
	public void updatePlayerBalance(Player player){
		if(isInDebugMode){
			return;
		}
		GUI.setBalance(player.getName(), player.getAccount().getBalance());
	}
	
	//Updates all players balance
	public void updateAllPlayersBalance(ArrayList<Player> player){
		if(isInDebugMode){
			return;
		}
		for(int i = 0; i < player.size(); i++){
			GUI.setBalance(player.get(i).getName(), player.get(i).getAccount().getBalance());
		}
	}

	//Set owner of the field where player is standing
	public void setOwnerWithPlayerPosition(Player player){
		if(isInDebugMode){
			return;
		}
		GUI.setOwner(player.getPosition(), player.getName());
	}
	
	//Remove ownership of a specific field
	public void removeOwnerShipField(int position){
		if(isInDebugMode){
			return;
		}
		GUI.removeOwner(position);
	}
	
	//Remove all ownership from a specific player
	public void removeOwnerShipFromPlayer(field.Field[] arrayOfFields, Player player){
		if(isInDebugMode){
			return;
		}
		for(int i = 0; i < arrayOfFields.length; i++){
			if(arrayOfFields[i] instanceof field.Ownable){
				if(((Ownable)arrayOfFields[i]).getOwner() != null){
					if(((Ownable)arrayOfFields[i]).getOwner().equals(player)){
						GUI.removeOwner(i+1);
					}
				}
			}
		}
	}
	
	//Updates all ownership of all GUIs ownables based on arrayOfFields
	public void updateAllOwnerShip(field.Field[] arrayOfFields){
		if(isInDebugMode){
			return;
		}
		for(int i = 0; i < arrayOfFields.length; i++){
			if(arrayOfFields[i] instanceof field.Ownable){
				if(((Ownable)arrayOfFields[i]).getOwner() != null){
					GUI.setOwner(i+1, ((Ownable)arrayOfFields[i]).getOwner().getName());
				}
			}
		}
	}
	
	//Updates dices based on diceOne and diceTwo
	public void updateDices(int diceOne, int diceTwo){
		if(isInDebugMode){
			return;
		}
		GUI.setDice(diceOne, diceTwo);
	}

	//update houses based on gameFields and players
	public void updateHouses(field.Field[] arrayOfFields){
		if(isInDebugMode){
			return;
		}
		for(int i = 0; i < arrayOfFields.length; i++){
			if(arrayOfFields[i] instanceof field.Street){
				if(((Street)arrayOfFields[i]).getAmountOfHotels() >= 1){
					GUI.setHotel(i+1, true);
				}
				else{
					GUI.setHouses(i+1, ((Street)arrayOfFields[i]).getAmountOfHouses());
				}
				
			}
		}
	}
	
	//ask the player dropdownQuestion
	public String askDropDownQuestion(String message, String... options){
		if(isInDebugMode){
			return debugModeReturnTypeString;
		}
		return GUI.getUserSelection(message, options);
	}
	
	//show a message to the user
	public void showMessage(String message){
		if(isInDebugMode){
			return;
		}
		GUI.showMessage(message);
	}
	
	//remove player from the board
	public void removePlayer(Player player, field.Field[] arrayOfFields){
		if(isInDebugMode){
			return;
		}
		GUI.setBalance(player.getName(), 0);
		this.removeOwnerShipFromPlayer(arrayOfFields, player);
		GUI.removeCar(player.getPosition()+1, player.getName());
	}
	
	//update player position on the board
	public void updatePlayerPosition(Player player){
		if(isInDebugMode){
			return;
		}
		GUI.removeAllCars(player.getName());
		GUI.setCar(player.getPosition()+1, player.getName());
	}
	
	//get a user int
	public int getUserIntegerInput(String message){
		if(isInDebugMode){
			return debugModeReturnTypeInt;
		}
		return GUI.getUserInteger(message);
	}
	
	//update subtext on a field
	public void updateSubText(int id, String message){
		if(isInDebugMode){
			return;
		}
		GUI.setSubText(id, message);
	}
	
	//update description on a field
	public void updateDescriptionText(int id, String message){
		if(isInDebugMode){
			return;
		}
		GUI.setDescriptionText(id, message);
	}
	
	//update title on a field
	public void updateTitle(int id, String message){
		if(isInDebugMode){
			return;
		}
		GUI.setTitleText(id, message);
	}
	
	
	//close the gui
	public void closeGUI(){
		if(isInDebugMode){
			return;
		}
		GUI.close();
	}
	
	
	public void updateIfOwnedText(field.Field[] arrayOfFields){
		if(isInDebugMode){
			return;
		}
		for(int i = 0; i < arrayOfFields.length; i++){
			
		}
	}
	
	
	public void showChanceCard(String message){
		if(isInDebugMode){
			return;
		}
		GUI.displayChanceCard(message);
	}
}
