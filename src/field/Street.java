package field;

import java.util.Arrays;

import controllers.GameController;
import desktop_resources.GUI;

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

	public boolean buyBuilding(GameController gameController) {

		int streets = this.getAmountOfStreetsInCategory(this.getStreetCategory(), gameController);
		if (gameController.getPlayerController().getPlayer(gameController.getTurn() - 1).getAccount()
				.getBalance() >= buildingPrice) {
			// String i = GUI.getUserButtonPressed("Do you want to buy a
			// building?", "Yes","No"); = Tobias' lortekode
			boolean i = gameController.getGUIController().askYesNoQuestion("Do you want to buy a building");
			if (i == true && this.isPawn == false
					&& this.getamountOfHouses() <= this.getHousesInSection(this.getStreetCategory(), gameController)
							/ streets) {
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
		if (this.owner.equals(gameController.getPlayerController().getCurrentPlayer())) {
			boolean i = gameController.getGUIController().askYesNoQuestion("Vil du frasælge en bygning?");
			if (i = true) {

				if (this.getamountOfHouses() > 0) {
					this.amountOfHouses--;
					gameController.getPlayerController().getCurrentPlayer().adjustBalance(buildingPrice);
					return true;
				} else if (this.getamountOfHotels() > 0) {
					this.amountOfHouses += 4;
					this.hotels--;
					gameController.getPlayerController().getCurrentPlayer().adjustBalance(buildingPrice);
					return true;
				}
			}
			 else if (i == false) {
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
}