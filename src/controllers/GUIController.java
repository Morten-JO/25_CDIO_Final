package controllers;

import java.awt.Color;
import java.util.ArrayList;

import player.Player;
import desktop_codebehind.Car;
import desktop_resources.GUI;
import field.*;


/**
 * Date: 07/01/2016
 *
 * Project: 25_cdio-final
 *
 * File: GUIController.java
 *
 * Created by: Morten Jï¿½rvad
 *
 * Github: https://github.com/Mortenbaws
 *
 * Email: morten2094@gmail.com
 */

public class GUIController {	
	
	public GUIController(GameController game){
		GUI.create(createList(game.getFieldController().getFields()));
		GUI.showMessage("Velkommen til matador!");
		ArrayList<String> addPlayers = choosePlayers();
		String[] names = new String[addPlayers.size()];
		names = addPlayers.toArray(names);
		game.getPlayerController().createPlayers(names);
	}
	
	//adds players from minimum 3 to maximum 6, also asks the player to personalize their car
	private ArrayList<String> choosePlayers(){
		ArrayList<String> players = new ArrayList<String>();
		boolean stillAdding = true;
		while(stillAdding){
			String input = GUI.getUserString("Tilføj spiller #"+(players.size()+1)+"'s navn.");
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
					GUI.showMessage("Indtast et unikt navn!");
				}
				else{
					players.add(input);
					boolean carQuestion = this.askYesNoQuestion("Vil du gøre din bil personlig?");
					if(carQuestion){
						String[] carChoices = {"Normal", "Racerbil", "Traktor", "Ufo"};
						String[] colorChoices = {"Sort", "Rød", "Gul", "Grøm", "Orange", "Blå"};
						String[] patternChoices = {"Ternet", "Diagonal tve farvet", "Prikket", "Fyldt", "Horizontal tve farvet", "Overgang", "Linjet", "Zebra"};
						String carChoice = GUI.getUserSelection("Vælg bil type:", carChoices);
						String color = GUI.getUserSelection("Vælg primær bil farve:", colorChoices);
						String secondaryColor = GUI.getUserSelection("Vælg sekundær bil farve:", colorChoices);
						String patternType = GUI.getUserSelection("Vælg bil mønster:", patternChoices);
						Color[] carColor = {Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.BLUE};
						
						Car.Builder car = null;
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
						for(int i = 0; i < colorChoices.length; i++){
							if(colorChoices[i].equals(color)){
								car.primaryColor(carColor[i]);
								break;
							}
						}
						for(int i = 0; i < colorChoices.length; i++){
							if(colorChoices[i].equals(secondaryColor)){
								car.secondaryColor(carColor[i]);
								break;
							}
						}
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
						GUI.showMessage(input+" tilføjet!");
					}
					else{
						GUI.addPlayer(input, 30000);
						GUI.showMessage(input+" tilføjet!");
					}
					if(players.size() >= 3){
						boolean answer = this.askYesNoQuestion("Vil du tilføje en spiller mere?");
						if(!answer){
							stillAdding = false;
						}
					}
				}
			}
			else{
				GUI.showMessage("Skriv et valideret navn!");
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
				list[i] = new desktop_fields.Shipping.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Street.class)){
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
				list[i] = builder.build();
			}
			else if(arrayOfFields[i].getClass().equals(Brewery.class)){
				Color color = new Color(0.5f,0.1f,0.2f,1.0f);
				list[i] = new desktop_fields.Brewery.Builder().setBgColor(color).build();
			}
			else{
				try {
					throw new Exception("A Field was not identified");
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
		return GUI.getUserButtonPressed(question, answers);
	}
	
	//Asks a yes/no question
	public Boolean askYesNoQuestion(String message){
		return GUI.getUserLeftButtonPressed(message, "Ja", "Nej");
	}
	
	//updates all the players positions on the board
	public void updatePlayerPositions(ArrayList<Player> players){
		for(int i = 0; i < players.size(); i++){
			GUI.removeAllCars(players.get(i).getName());
			GUI.setCar(players.get(i).getPosition()+1, players.get(i).getName());
		}
	}

	//Update a specific players balance
	public void updatePlayerBalance(Player player){
		GUI.setBalance(player.getName(), player.getAccount().getBalance());
	}
	
	//Updates all players balance
	public void updateAllPlayersBalance(ArrayList<Player> player){
		for(int i = 0; i < player.size(); i++){
			GUI.setBalance(player.get(i).getName(), player.get(i).getAccount().getBalance());
		}
	}

	//Set owner of the field where player is standing
	public void setOwnerWithPlayerPosition(Player player){
		GUI.setOwner(player.getPosition(), player.getName());
	}
	
	//Remove ownership of a specific field
	public void removeOwnerShipField(int position){
		GUI.removeOwner(position);
	}
	
	//Remove all ownership from a specific player
	public void removeOwnerShipFromPlayer(field.Field[] arrayOfFields, Player player){
		for(int i = 0; i < arrayOfFields.length; i++){
			if(arrayOfFields[i] instanceof field.Ownable){
				if(((Ownable)arrayOfFields[i]).getOwner() != null){
					if(((Ownable)arrayOfFields[i]).getOwner().equals(player)){
						GUI.removeOwner(i);
					}
				}
			}
		}
	}
	
	//Updates all ownership of all GUIs ownables based on arrayOfFields
	public void updateAllOwnerShip(field.Field[] arrayOfFields){
		for(int i = 0; i < arrayOfFields.length; i++){
			if(arrayOfFields[i] instanceof field.Ownable){
				if(((Ownable)arrayOfFields[i]).getOwner() != null){
					GUI.setOwner(i, ((Ownable)arrayOfFields[i]).getOwner().getName());
				}
			}
		}
	}
	
	//Updates dices based on diceOne and diceTwo
	public void updateDices(int diceOne, int diceTwo){
		GUI.setDice(diceOne, diceTwo);
	}

	
	public void updateHouses(field.Field[] arrayOfFields){
		for(int i = 0; i < arrayOfFields.length; i++){
			if(arrayOfFields[i] instanceof field.Street){
				if(((Street)arrayOfFields[i]).getamountOfHouses() <= 4){
					GUI.setHouses(i, ((Street)arrayOfFields[i]).getamountOfHouses());
				}
				else{
					GUI.setHotel(i, true);
				}
				
			}
		}
	}
	
	public String askDropDownQuestion(String message, String... options){
		return GUI.getUserSelection(message, options);
	}
	
	public void showMessage(String message){
		GUI.showMessage(message);
	}
	
	public void removePlayer(Player player, field.Field[] arrayOfFields){
		GUI.setBalance(player.getName(), 0);
		this.removeOwnerShipFromPlayer(arrayOfFields, player);
		GUI.removeCar(player.getPosition(), player.getName());
	}
	
	public void updatePlayerPosition(Player player){
		GUI.removeAllCars(player.getName());
		GUI.setCar(player.getPosition()+1, player.getName());
	}
	
	public int getUserIntegerInput(String message){
		return GUI.getUserInteger(message);
	}
}
