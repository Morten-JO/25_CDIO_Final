package field;

import java.util.Arrays;

import controllers.GameController;
import controllers.Language;
import desktop_resources.GUI;
import player.Player;


public class Street extends Ownable {
	private int rents[] = new int[7];
	private int buildingPrice;
	private int amountOfHouses;
	private int maxAmountofHouses = 4;
	private int streetCategory;
	private int hotels;

	public Street(String Titel, String Sub, String Desc, int fieldNo, int price, int pawnPrice, int rents[],
			int buildingPrice, int streetCategory) {
		super(Titel, Sub, Desc, fieldNo, price, pawnPrice);
		this.rents = rents;
		this.buildingPrice = buildingPrice;
		this.streetCategory = streetCategory;
		// category refering to the color of the street
	}

	
	
	
	
	
	/* (non-Javadoc)
	 * @see field.Ownable#landOn(controllers.GameController)
	 */
	@Override
	public boolean landOn(GameController gameController) {
		boolean result = true;
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		
		// is this street owned ??
		//if (currentPlayer.getBalance() >= this.price && (this.owner == null || this.isPawn == true)) {
			// If no, would you buy it
			
			if(currentPlayer == this.owner && isPawn){
				int priceToPay = this.price/2;
				
				//Unpawning the street
				if (currentPlayer.getBalance() >= priceToPay) {
					boolean answer = gameController.getGUIController().askYesNoQuestion(Language.Field_HasPawned+ this.getName() + Language.Field_BuyBack+ priceToPay + Language.Field_Currency);
					if (answer == true) { // Ja, jeg vil gerne upantsætte
						isPawn = false;
						this.owner = currentPlayer;
						this.setSubtext(this.owner.getName());
						result = owner.adjustBalance(-priceToPay);
					}else if (answer == false)
						result = true;
				}
				
			}else{ 
				if (currentPlayer.getBalance() >= price && (this.owner==null || this.isPawn == true && this.owner != currentPlayer) ) {
				boolean answer = gameController.getGUIController().askYesNoQuestion(Language.Field_DoYouWantToBuy + this.getName() + Language.Field_For + this.price+ "?");
				if (answer == true) { // Ja, jeg vil gerne købe
					isPawn = false;
					this.owner = currentPlayer;
					this.setSubtext(this.owner.getName());
					result = owner.adjustBalance(-price);
				}else if (answer == false)
					result = true;
				}
			}
		
		//Er feltet ejet af en anden spiller? Beregn leje og juster spilleres konti
		if (this.owner != null && this.owner != currentPlayer && this.isPawn == false) {
			// Er feltets ejer i fængsel?
			if (this.owner.isJailed() == false) {
				//Amount of fields in same category that owner own
				int streets = gameController.getFieldController().getOwnershipOfStreetsInCat(this.owner,
						this.getStreetCategory());
				gameController.getGUIController()
						.showMessage(currentPlayer.getName() + Language.Field_HasLandedOn + this.getName() + ". " + this.owner.getName()
								+ Language.Field_OwnerOfTheStreet + getRent(gameController) + Language.Field_AmountToPay);
				
				int balance = currentPlayer.getBalance();
				
				switch (streets) {
				
				//owner owns 1 field in cat
				case 1:
					result = currentPlayer.adjustBalance(-this.rents[0]);

					if (balance < this.rents[0]) {
						this.owner.adjustBalance(balance);
					} else {
						this.owner.adjustBalance(this.rents[0]);
					}
					break;
					
				//2 fields. Important checking if the category is one of the 2 cats that only has 2 fields
				case 2:
					if (this.getStreetCategory() == 0 || this.getStreetCategory() == 7) {
							if(this.getAmountOfHotels() > 0){
								if(balance > this.rents[6]) {
									this.owner.adjustBalance(this.rents[6]);
								}else{
									this.owner.adjustBalance(balance);
								}
								
								result = currentPlayer.adjustBalance(-this.rents[6]);
							}else{
								if(balance > this.rents[this.amountOfHouses+1]) {
									this.owner.adjustBalance(this.rents[this.amountOfHouses+1]);
								}else{
									this.owner.adjustBalance(balance);
								}
								result = currentPlayer.adjustBalance(-this.rents[this.amountOfHouses+1]);
							}
					}else{//2 field owned in a 3-field category. Only option will be paying normal rent [0]
						if (balance > this.rents[0]) {
							this.owner.adjustBalance(this.rents[0]);
						}else{
							this.owner.adjustBalance(balance);
						}
						result = currentPlayer.adjustBalance(-this.rents[0]);
					}	
					break;
				case 3:
					//3 fields owned in a 3-field category. Paying rent: rents[this.amountOfHouses+1]
					if(this.getAmountOfHotels() > 0){
						if(balance > this.rents[6]) {
							this.owner.adjustBalance(this.rents[6]);
						}else{
							this.owner.adjustBalance(balance);
						}
						result = currentPlayer.adjustBalance(-this.rents[6]);
					}else{
						if(balance > this.rents[this.amountOfHouses+1]) {
							this.owner.adjustBalance(this.rents[this.amountOfHouses+1]);
						}else{
							this.owner.adjustBalance(balance);
						}
						result = currentPlayer.adjustBalance(-this.rents[this.amountOfHouses+1]);
					}
					break;
				}
			}
		}

		return result;
	}

	/**
	 * buyBuilding method
	 * It adds a building to a street of the players choice. It calculates the amount of houses
	 * on the other streets in the category and insures, that no street can contain more than one
	 * house compared to the other streets in the category.
	 * If there are already 4 houses it removes the houses and adds a hotel.
	 * Also, if the street is pawned, or any other street in the section is pawned, building will not happen.
	 * @param gameController
	 * @return boolean - 
	 */
	public boolean buyBuilding(GameController gameController) {
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();

		// How many streets in this category?
		int pawned = gameController.getFieldController().getAmountofPawnedStreetsInCategory(gameController, this.streetCategory);
		int streets = this.getAmountOfStreetsInCategory(this.getStreetCategory(), gameController);
		if (currentPlayer.getBalance() >= buildingPrice && pawned == 0) {
			boolean answer = gameController.getGUIController()
					.askYesNoQuestion(Language.Field_BuyBuilding);

			// Gets answer, pawnstatus and the average amount of houses per
			// street in this category
			if (answer == true && this.isPawn == false && this.getAmountOfHouses() <= this.getHousesInSection(this.getStreetCategory(), gameController) / streets && this.hotels < 1) {
				if(hotels < 1 && this.amountOfHouses == maxAmountofHouses){
					this.amountOfHouses = 0;
					this.hotels++;
					this.owner.adjustBalance(-this.buildingPrice);
					return true;
				}
				else if(this.amountOfHouses < maxAmountofHouses && hotels == 0) {
					this.amountOfHouses++;
					this.owner.adjustBalance(-this.buildingPrice);
					return true;
				}
			}
			else {
				return false;
			}
		}


		return false;

	}

	/**
	 * sellBuilding. Method used by an owner of a Field to remove one building from this Field.
	 * Method investigates amount of houses and hotels, and adjusts the amount accordingly.
	 * It calculates whether or not the amount of houses on the other streets in the category
	 * will exceed the amount on this street, should a building be sold. If so it returns false... not sure if needed
	 * @param gameController
	 * @return
	 */
	public boolean sellBuilding(GameController gameController) {
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		int streets = this.getAmountOfStreetsInCategory(this.getStreetCategory(), gameController);
		if (this.owner.equals(currentPlayer) && (double)this.getAmountOfHouses() >= ((double)this.getHousesInSection(this.getStreetCategory(), gameController) / streets)) {
			boolean answer = gameController.getGUIController().askYesNoQuestion(Language.Field_SellBuilding);
			if (answer = true) {

				if (this.getAmountOfHouses() > 0) {
					this.amountOfHouses--;
					return currentPlayer.adjustBalance(buildingPrice);
				} else if (this.getAmountOfHotels() > 0) {
					this.amountOfHouses += 4;
					this.hotels--;
					return currentPlayer.adjustBalance(buildingPrice);

				}
			} else if (answer == false) {
				return false;
			}
		} else {
			return false;
		}
		return false;
	}

	public int getAmountOfHouses() {
		if (this.hotels > 0) {
		return 0;//this.amountOfHouses += maxAmountofHouses + 1;
		}else{
			return this.amountOfHouses;
		}
	

	}

	public int getAmountOfHotels() {
		return this.hotels;
	}

	public int getHousesInSection(int i, GameController gameController) {
		int houses = 0;
		for (int j = 0; j < 40; j++) {

			if (Street.class == gameController.getFieldController().getFields()[j].getClass()) {

				if (((Street) gameController.getFieldController().getFields()[j]).getStreetCategory() == i) {
					houses += ((Street) gameController.getFieldController().getFields()[j]).getAmountOfHouses();
					if (((Street) gameController.getFieldController().getFields()[j]).getAmountOfHotels() > 0) {
						houses += maxAmountofHouses + 1;
					}
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

	public int getBuildingPrice() {
		return buildingPrice;
	}

	@Override
	public int getRent(GameController gameController) {
		int rent = 0;
		int houses = this.getAmountOfHouses();
		int hotels = this.getAmountOfHotels();
		int streetsowned = gameController.getFieldController().getOwnershipOfStreetsInCat(this.getOwner(),
				this.getStreetCategory());
		if (houses > 0) {
			rent = this.rents[houses + 1];
		} else if (hotels > 0) {
			rent = this.rents[maxAmountofHouses + 2];
		} else if (streetsowned == this.getAmountOfStreetsInCategory(this.getStreetCategory(), gameController)) {
			rent = this.rents[1];
		} else if (streetsowned < this.getAmountOfStreetsInCategory(this.getStreetCategory(), gameController)
				|| streetsowned > 0) {
			rent = this.rents[0];
		} else {
			rent = 0;
		}

		return rent;

	}

	public void removeAllOwnership() {
		this.owner = null;
		this.amountOfHouses = 0;
		this.hotels = 0;

	}

	@Override
	public boolean pawnProperty(GameController gameController, Player player) {
		if (!this.isPawn) {
			this.isPawn = true;
			this.getStreetCategory();
		
			player.adjustBalance(this.pawnPrice + sellAllBuildingsinCat(this.getStreetCategory(), gameController));
			
		}
		return true;
	}

	public int sellAllBuildingsinCat(int category, GameController gameController) {
		int amount = 0;
		Field[] fields = gameController.getFieldController().getFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof Street) {
				if (((Street)fields[i]).getStreetCategory() == category) {
					amount += this.buildingPrice * (((Street) gameController.getFieldController().getFields()[i]).getAmountOfHouses() + (((Street) gameController.getFieldController().getFields()[i]).getAmountOfHotels() * 5));
					((Street) gameController.getFieldController().getFields()[i]).setAmountOfHouses(0);
					((Street) gameController.getFieldController().getFields()[i]).setAmountOfHotels(0);
				}
			}
		}
		
		return amount;
	}

	public void setAmountOfHouses(int i) {
		this.amountOfHouses = i;
	}
	
	public void setAmountOfHotels(int i) {
		this.hotels = i;
	}
}
