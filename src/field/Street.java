package field;

import java.util.Arrays;

import controllers.GameController;
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

	// landOn method for Streets,
	@Override
	public boolean landOn(GameController gameController) {
		boolean result = true;
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		// is this street owned ??
		if (this.owner == null) {
			// If no, would you buy it
			if (currentPlayer.getBalance() > price) {
				boolean answer = gameController.getGUIController()
						.askYesNoQuestion("Vil du købe " + this.getName() + " for kr." + this.price);
				if (answer == true) { // Ja, jeg vil gerne købe
					this.owner = currentPlayer;
					this.setSubtext(this.owner.getName());
					System.out.println("du har købt dette felt");
					result = owner.adjustBalance(-price);
				} else if (answer == false)
					result = true;
			}
		}

		if (this.owner != null && this.owner != currentPlayer) {
			// Er feltets ejer i fængsel?
			if (this.owner.isJailed() == false) {
				// Hvor mange felter i denne kategori er ejet af ham, der ejer
				// dette felt?
				int streets = gameController.getFieldController().getOwnershipOfStreetsInCat(this.owner,
						this.getStreetCategory());
				gameController.getGUIController()
						.showMessage(currentPlayer.getName() + " er landet på " + this.getName() + ". " + this.owner.getName()
								+ " ejer dette felt og De skal betale " + getRent(gameController) + "kr. i leje");
				switch (streets) {
				// Kun et felt
				case 1:
					int balance = currentPlayer.getBalance();
					result = currentPlayer.adjustBalance(-this.rents[0]);

					if (balance < this.rents[0]) {
						this.owner.adjustBalance(balance);
					} else {
						this.owner.adjustBalance(this.rents[0]);
					}
					break;
				// To felter, og vi undersøger først, om det kategorien svarer
				// til de to, hvori der kun er 2 felter at eje.
				case 2:
					if (this.getStreetCategory() == 0 || this.getStreetCategory() == 7) {

						if (this.amountOfHouses == 0) {
							if (currentPlayer.getBalance() > this.rents[1]) {
								currentPlayer.adjustBalance(-this.rents[1]);
								result = this.owner.adjustBalance(this.rents[1]);
							} else {
								int lastBalance = currentPlayer.getBalance();
								this.owner.adjustBalance(lastBalance);
								return false;
							}
						}
						// you need to add 1, because in our array it is [1 *
						// rent, 2*rent, 1 house,2 house,ect.....]
						else if (this.amountOfHouses >= 1) {
							if (currentPlayer.getBalance() > this.rents[1 + this.amountOfHouses]) {
								currentPlayer.adjustBalance(-this.rents[1 + this.amountOfHouses]);
								result = this.owner.adjustBalance(this.rents[1 + this.amountOfHouses]);
							} else {
								int lastBalance = currentPlayer.getBalance();
								this.owner.adjustBalance(lastBalance);
								return false;
							}
						}
					}
					break;
				case 3:
					// tre felter = alle felter i kategorien og dobbelt leje
					int payToOwner = this.getHousesInSection(this.getStreetCategory(), gameController);
					if (payToOwner == 0) {
						if (currentPlayer.getBalance() > this.rents[1]) {
							currentPlayer.adjustBalance(-this.rents[1]);
							result = this.owner.adjustBalance(this.rents[1]);
						} else {
							int lastBalance = currentPlayer.getBalance();
							this.owner.adjustBalance(lastBalance);
							return false;
						}

					}
					// you need to add , because in our array it is [1 * rent,
					// 2*rent, 1 house,2 house,ect.....]
					else if (payToOwner >= 1) {
						if (currentPlayer.getBalance() > rents[1 + payToOwner]) {
							currentPlayer.adjustBalance(-rents[1 + payToOwner]);
							result = this.owner.adjustBalance(rents[1 + payToOwner]);
						} else {
							int lastBalance = currentPlayer.getBalance();
							this.owner.adjustBalance(lastBalance);
							return false;
						}
					}
					break;
				}
			}
		}

		return result;
	}

	// Buy property
	public boolean buyBuilding(GameController gameController) {
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();

		// How many streets in this category?
		int streets = this.getAmountOfStreetsInCategory(this.getStreetCategory(), gameController);
		if (currentPlayer.getBalance() >= buildingPrice) {
			boolean answer = gameController.getGUIController()
					.askYesNoQuestion("Vil du tilføje en bygning til dette felt?");

			// Gets answer, pawnstatus and the average amount of houses per
			// street in this category
			if (answer == true && this.isPawn == false && this.getAmountOfHouses() <= this.getHousesInSection(this.getStreetCategory(), gameController) / streets && this.hotels < 1) {
				if (this.amountOfHouses < maxAmountofHouses) {
					this.amountOfHouses += 1; // Ved ikke helt med denne, vi
												// skal have noget der holder
												// styr på dette.
					this.owner.adjustBalance(-this.buildingPrice);
					return true;
				}

				else if (this.amountOfHouses == maxAmountofHouses) {
					this.amountOfHouses = this.amountOfHouses - maxAmountofHouses;
					this.hotels++;
				}

			
			}
			else {
				return false;
			}
		}


		return false;

	}

	public boolean sellBuilding(GameController gameController) {
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		if (this.owner.equals(currentPlayer)) {
			boolean answer = gameController.getGUIController().askYesNoQuestion("Vil du frasælge en bygning?");
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
			this.amountOfHouses += maxAmountofHouses + 1;
		}
		return this.amountOfHouses;

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
					if (this.hotels > 0) {
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
		for (int i = 0; i < gameController.getFieldController().getFields().length; i++) {
			if (gameController.getFieldController().getFields()[i] instanceof Street) {
				if (this.getStreetCategory() == category) {
					amount += this.buildingPrice * (((Street) gameController.getFieldController().getFields()[i])
							.getAmountOfHouses()
							+ (((Street) gameController.getFieldController().getFields()[i]).getAmountOfHotels() * 5));
				}
			}
		}
		return amount;
	}

	public void setAmountofHouses(int i) {
		this.amountOfHouses = i;
	}
}
