package field;

import java.util.Arrays;

import controllers.GameController;
import desktop_resources.GUI;




public class Street extends Ownable {
	private int rents [] = new int [7];
	private int buildingPrice;
	private int amountOfHouses;
	private int streetCategory;
	private boolean isPawn;
	public Street(String Titel, String Sub, String Desc, int fieldNo, int price, int pawnPrice,int rents[],int buildingPrice, int streetCategory) {
		super(Titel, Sub, Desc, fieldNo, price, pawnPrice);
		this.rents = rents;
		this.buildingPrice = buildingPrice;
		this.streetCategory = streetCategory;
	}

	
	public boolean buyBuilding (GameController gameController){
		if (gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().getBalance() >= buildingPrice){
		String i = GUI.getUserButtonPressed("Do you want to buy a building?", "Yes","No");
		if (i == "Yes"){
			this.amountOfHouses +=1; // Ved ikke helt med denne, vi skal have noget der holder styr på dette.
			return true;
		}
		else {
			return true;
		}
		}
		return true;	
			
	}
	public int getamountOfHouses(){
		return this.amountOfHouses;
		
	}

	public int[] getRents() {
		return rents;
	}


	public void setRents(int[] rents) {
		this.rents = rents;
	}


	@Override
	public String toString() {
		return "Street [rents=" + Arrays.toString(rents) + "]";
	}
	
}
