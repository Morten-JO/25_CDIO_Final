package field;

import controllers.GameController;
import desktop_resources.GUI;
import player.Player;

public class Brewery extends Ownable {
	private int[] rents = { 100, 200 };

	public Brewery(String titel, String sub, String desc, int fieldNo, int price, int pawnPrice, int[] rents) {
		super(titel, sub, desc, fieldNo, price, pawnPrice);
		this.rents = rents;

	}

	@Override
	public boolean landOn(GameController gameController) {
		boolean result = true;
		Player currentPlayer = gameController.getPlayerController().getCurrentPlayer();
		if (this.owner == null) {
			if (gameController.getPlayerController().getCurrentPlayer().getAccount().getBalance() >= price) {
				boolean answer = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Brewery?");
				if (answer == true) {
					owner = currentPlayer;
					result = currentPlayer.adjustBalance(-price);

				} else if (answer == false) {
					result = true;
				}
			}
		}

		if (this.owner != null && this.owner != currentPlayer && this.owner.isJailed() == false) {
			int i = gameController.getFieldController().getOwnerShipOfBreweries(currentPlayer);
			int pay = rents[i] * gameController.getCup().getDiceSum();// REMOVED
																		// -1
																		// CAUSING
																		// ARRAY_OOB.
																		// turn
																		// starts
																		// at 0
			if (currentPlayer.getAccount().getBalance() > pay) {
				currentPlayer.adjustBalance(-pay);
				result = this.owner.adjustBalance(pay);

			} else
				result = false;

		}

		return result;
	}

	public int getRent(GameController gameController){
		int rent = 0;
		int breweries = gameController.getFieldController().getOwnerShipOfBreweries(gameController.getPlayerController().getCurrentPlayer());
		rent = this.rents[breweries-1];
		
		return rent;
		
	}
}
