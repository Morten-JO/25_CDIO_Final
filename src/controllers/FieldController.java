package controllers;

import field.Brewery;
import field.Field;
import field.Fleet;
import field.Ownable;
import playerMO.Player;

public class FieldController {
	private int[] noob = {1,2,3,4,5};
	private Field[] gameFields = new Field[40];
	
	//gameFields[1] = new Street("n��b", "Magnus", "noob", 1, 200, 400,noob,4000);
	
	
	public int getOwnerShipOfFleets(Player p) {
		int count = 0;
		for(int i = 0 ; i<=40; i++){
			if (gameFields[i] instanceof Fleet  && ((Ownable)gameFields[i]).getOwner() == p){
				count +=1;
			}
			
		}
		return count;
	}
	
	public int getOwnerShipOfBreweries(Player p) {
		int count = 0;
		for(int i = 0 ; i<=40; i++){
			if (gameFields[i] instanceof Brewery  && ((Ownable)gameFields[i]).getOwner() == p){
				count +=1;
			}
			
		}
		return count;
	}

	public Field[] getFields() {
		// TODO Auto-generated method stub
		return null;
	}
}

