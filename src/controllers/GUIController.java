package controllers;

import java.awt.Color;
import java.util.ArrayList;






import player.Player;
import desktop_codebehind.Car;
import desktop_fields.Tax;
import desktop_resources.GUI;
import field.*;


/**
 * Date: 07/01/2016
 *
 * Project: 25_cdio-final
 *
 * File: GUIController.java
 *
 * Created by: Morten Jørvad
 *
 * Github: https://github.com/Mortenbaws
 *
 * Email: morten2094@gmail.com
 */

public class GUIController {	
	
	public GUIController(){
		GameController game = new GameController();
		GUI.create(createList(game.getFieldController().getFields()));
		GUI.showMessage("Welcome to matador!");
		game.setPlayers(addPlayers());
		game.startGame();
		
	}
	
	//adds players from minimum 3 to maximum 6, also asks the player to personalize their car
	private ArrayList<String> addPlayers(){
		ArrayList<String> players = new ArrayList<String>();
		boolean stillAdding = true;
		while(stillAdding){
			String input = GUI.getUserString("Add Player #"+(players.size()+1)+" name.");
			if(!input.equals("")){
				boolean isSame = false;
				if(players.size() > 0){
					for(int i = 0; i < players.size(); i++){
						if(input.equals(players.get(i))){
							isSame = true;
						}
					}
				}
				System.out.println("isSame: "+isSame);
				if(isSame){
					GUI.showMessage("Type a unique name");
				}
				else{
					players.add(input);
					boolean carQuestion = this.askYesNoQuestion("Would you like to personalize your car?");
					if(carQuestion){
						String[] carChoices = {"Standard", "Racecar", "Tractor", "Ufo"};
						String[] colorChoices = {"Black", "Red", "Yellow", "Green", "Orange", "Blue"};
						String[] patternChoices = {"Checkered", "Diagonal Dual Color", "Dotted", "Fill", "Horizontal Dual Color", "Gradient", "Line", "Zebra"};
						String carChoice = GUI.getUserSelection("Choose car type:", carChoices);
						String color = GUI.getUserSelection("Choose Primary car color:", colorChoices);
						String secondaryColor = GUI.getUserSelection("Choose Secondary car color:", colorChoices);
						String patternType = GUI.getUserSelection("Choose car pattern:", patternChoices);
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
						GUI.showMessage(input+" added!");
					}
					else{
						GUI.addPlayer(input, 30000);
						GUI.showMessage(input+" added!");
					}
					if(players.size() >= 3){
						boolean answer = this.askYesNoQuestion("Would you like to add another player?");
						if(!answer){
							stillAdding = false;
						}
					}
				}
			}
			else{
				GUI.showMessage("You must type a valid name!");
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
				list[i] = new desktop_fields.Chance.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Jail.class)){
				list[i] = new desktop_fields.Jail.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Tax.class)){
				list[i] = new desktop_fields.Tax.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(TaxProcent.class)){
				list[i] = new desktop_fields.Tax.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Bonus.class)){
				list[i] = new desktop_fields.Empty.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(VisitJail.class)){
				list[i] = new desktop_fields.Jail.Builder().build();
				System.out.println("created jail");
			}
			else if(arrayOfFields[i].getClass().equals(Fleet.class)){
				list[i] = new desktop_fields.Shipping.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Street.class)){
				list[i] = new desktop_fields.Street.Builder().build();
			}
			else if(arrayOfFields[i].getClass().equals(Brewery.class)){
				list[i] = new desktop_fields.Brewery.Builder().build();
			}
			else{
				System.out.println("A bug occured, shutting down program");
				try {
					throw new Exception("A Field was not identified");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			list[i].setDescription(arrayOfFields[i].getDescriptionText());
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
		return GUI.getUserLeftButtonPressed(message, "Yes", "No");
	}
	
	//updates all the players positions on the board
	public void updatePlayerPositions(ArrayList<Player> players){
		for(int i = 0; i < players.size(); i++){
			GUI.removeAllCars(players.get(i).getName());
			GUI.setCar(players.get(i).getPosition(), players.get(i).getName());
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
				if(((Ownable)arrayOfFields[i]).getOwner().equals(player)){
					GUI.removeOwner(i);
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

}
