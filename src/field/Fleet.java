package field;

import controllers.FieldController;
import controllers.GameController;
import desktop_resources.GUI;
import player.Player;

public class Fleet extends Ownable {
	private int[] rents = { 500, 1000, 2000, 4000 };
	private int rentMultiplier;

	public Fleet(String Titel, String Sub, String Desc, int fieldNo, int price, int pawnPrice, int[] rents) {
		super(Titel, Sub, Desc, fieldNo, price, pawnPrice);
		this.rents = rents;
		rentMultiplier = 1;
	}

	public int getRents(int FleetOwned) {
		return rents[FleetOwned - 1];
	}

	public void setRentMultiplier(int i) {
		rentMultiplier = i;
	}

	@Override
	public boolean landOn(GameController gameController) {
		boolean result = true;
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		// check if player has enough balance to buy field, and that field has
		// no owner
		if (this.owner == null && currentPlayer.getBalance() >= this.price) {

			boolean question = gameController.getGUIController()
					.askYesNoQuestion("Vil du købe " + this.getName() + " for kr. " + this.getPrice());
			// if want to buy field = true
			if (question == true) {
				result = currentPlayer.adjustBalance(-this.price);
				this.owner = currentPlayer;
				this.setSubtext(this.owner.getName());

			} else {
				result = true;
			}
			rentMultiplier = 1;
			return result;
		}

		// check owner not NULL, currentPlayer not owner, owner not jailed. PAY
		// ACCORDINGLY
		if (this.owner != null && this.owner != currentPlayer && this.owner.isJailed() == false && this.isPawn == false) {
			Player fleetowner;
			int fleets = gameController.getFieldController().getOwnerShipOfFleets(this.owner);

			gameController.getGUIController().showMessage(currentPlayer.getName() + " er landet på " + this.getName() + ". " + this.owner.getName()
							+ " ejer dette felt og De skal betale " + getRent(gameController) + "kr. i leje");
			
			//Are there any pawned fields among the one owned by the owner of this one? if so, adjust rent
			int toPay = this.rents[fleets - 1-(gameController.getFieldController().getAmountofPawnedFleets(gameController, this.owner))];

			// check if user has enough balance to pay
			if (currentPlayer.getBalance() > toPay) {
				result = currentPlayer.adjustBalance(-toPay);
				this.owner.getAccount().adjustBalance(toPay);
				
			} else {
				int lastBalance = currentPlayer.getBalance();
				this.owner.adjustBalance(lastBalance);
				result = false;
			}
		}
		return result;
	}

	@Override
	public int getRent(GameController gameController) {
		int rent = 0;
		int fleets = gameController.getFieldController().getOwnerShipOfFleets(this.owner)-gameController.getFieldController().getAmountofPawnedFleets(gameController, this.owner);
		rent = this.rents[fleets - 1];

		return rent;

	}
}