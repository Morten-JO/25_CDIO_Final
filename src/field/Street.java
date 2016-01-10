package field;

import java.util.Arrays;

import controllers.GameController;
import desktop_resources.GUI;
import player.Player;



public class Street extends Ownable {
	private int rents[] = new int[7];
	private int buildingPrice;
	private int amountOfHouses;
	private int maxAmountofHouses;
	private int streetCategory;
	private int hotels;
	private boolean isPawn;

	public Street(String Titel, String Sub, String Desc, int fieldNo, int price, int pawnPrice, int rents[],
			int buildingPrice, int streetCategory) {
		super(Titel, Sub, Desc, fieldNo, price, pawnPrice);
		this.rents = rents;
		this.buildingPrice = buildingPrice;
		this.streetCategory = streetCategory;
	}

	
	
	@Override
	public boolean landOn(GameController gameController) {
		boolean result = true;
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		//is this street owned ?? 
		if ( this.owner == null){
			//If no, would you buy it
			if(currentPlayer.getBalance()> price ){
				boolean answer = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Street?");
				if (answer == true){
					this.owner = currentPlayer;
					System.out.println("du har købt dette felt");
				result =	owner.adjustBalance(-price);
				} else if (answer == false) result = true;
			}
		}
		
			if (this.owner != null && this.owner != currentPlayer ){
			if (this.owner.isJailed()== false){
				int streets = this.getAmountOfStreetsInCategory(this.getStreetCategory(),gameController);
				System.out.println("så mange gader har jeg " +streets);
			switch (streets){
			case 1 : if ( currentPlayer.getBalance()> this.rents[0]){
					currentPlayer.adjustBalance(- this.rents[0]);
					result =this.owner.adjustBalance(this.rents[0]);
					
			} else return false;
					break;
			case 2 : if ( this.getStreetCategory() == 0 ||this.getStreetCategory() == 7){
				int payToOwner = this.getHousesInSection(this.getStreetCategory(), gameController);
				if ( payToOwner == 0){
					if (currentPlayer.getBalance()>this.rents[1]){
				currentPlayer.adjustBalance(- this.rents[1]);
				result = this.owner.adjustBalance(this.rents[1]);
					} else return false ;
					}
				// you need to add 1, because in our array it is [1 * rent, 2*rent, 1 house,2 house,ect.....]
				else if (payToOwner >= 1){if (currentPlayer.getBalance()>this.rents[1+payToOwner]){
					currentPlayer.adjustBalance(- this.rents[1+payToOwner]);
				result =	this.owner.adjustBalance(this.rents[1+ payToOwner]);
				}else return false;	
				}
			}
				break;
			case 3 : 
				int payToOwner = this.getHousesInSection(this.getStreetCategory(), gameController);
				if ( payToOwner == 0){
					if ( currentPlayer.getBalance()> this.rents[1]){
					currentPlayer.adjustBalance(- this.rents[1]);
					result = this.owner.adjustBalance(this.rents[1]);
					} else return false;
					
					}
				// you need to add , because in our array it is [1 * rent, 2*rent, 1 house,2 house,ect.....]
				else if (payToOwner >= 1){
					if(currentPlayer.getBalance()>rents[1+payToOwner]){
					currentPlayer.adjustBalance(- rents[1+payToOwner]);
					result = this.owner.adjustBalance(rents[1+payToOwner]);
					}else return false;
				}
			break;
			}
			}
			}
		
		
		return result;
	}
	public boolean buyBuilding(GameController gameController) {
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		
		int streets = this.getAmountOfStreetsInCategory(this.getStreetCategory(), gameController);
		if (currentPlayer.getBalance() >= buildingPrice) {
					boolean i = gameController.getGUIController().askYesNoQuestion("Do you want to buy a building");
			if (i == true && this.isPawn == false
					&& this.getamountOfHouses() <= this.getHousesInSection(this.getStreetCategory(), gameController)/ streets) {
				if (this.amountOfHouses < maxAmountofHouses) {
					this.amountOfHouses += 1; // Ved ikke helt med denne, vi
												// skal have noget der holder
												// styr på dette.
					return true;
				}

				else if (this.amountOfHouses == maxAmountofHouses) {
					this.amountOfHouses = this.amountOfHouses - maxAmountofHouses;
					this.hotels++;
				}

				else if (this.hotels > 0) {
					return false;
				}

				else if (i == false) {
					return false;
				}
			} else {
				return true;
			}
		}

		else {
			return false;
		}

		return true;

	}

		public boolean sellBuilding(GameController gameController) {
			Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		if (this.owner.equals(currentPlayer)) {
			boolean answer = gameController.getGUIController().askYesNoQuestion("Vil du frasælge en bygning?");
			if (answer = true) {

				if (this.getamountOfHouses() > 0) {
					this.amountOfHouses--;
					return currentPlayer.adjustBalance(buildingPrice);
					} else if (this.getamountOfHotels() > 0) {
					this.amountOfHouses += 4;
					this.hotels--;
					return currentPlayer.adjustBalance(buildingPrice);
					
				}
			}
			 else if (answer == false) {
					return false;
				}
		}
		else {
			return false;
		}
		return false;
	}

	public int getamountOfHouses() {
		return this.amountOfHouses;

	}

	public int getamountOfHotels() {
		return this.hotels;
	}

	public int getHousesInSection(int i, GameController gameController) {
		int houses = 0;
		for (int j = 0; j < 40; j++) {

			if (Street.class == gameController.getFieldController().getFields()[j].getClass()) {

				if (((Street) gameController.getFieldController().getFields()[j]).getStreetCategory() == i) {
					houses += ((Street) gameController.getFieldController().getFields()[j]).getamountOfHouses();

				}

			}
		}
		return houses;
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

	public int getStreetCategory() {
		return streetCategory;
	}

	public int getAmountOfStreetsInCategory(int i, GameController gameController) {
		int count = 0;
		for (int j = 0; j < 40; j++) {
			if (Street.class == gameController.getFieldController().getFields()[j].getClass()) {

				if (((Street) gameController.getFieldController().getFields()[j]).getStreetCategory() == i) {
					count++;
				}
			}

		}
		return count;
	}
	
	public int getBuildingPrice(){
		return buildingPrice;
	}
}