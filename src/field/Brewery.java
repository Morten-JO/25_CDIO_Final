package field;

import controllers.GameController;
import desktop_resources.GUI;

public class Brewery extends Ownable {
	private int[] rents = { 100, 200 };

	public Brewery(String titel, String sub, String desc, int fieldNo, int price, int pawnPrice, int[] rents) {
		super(titel, sub, desc, fieldNo, price, pawnPrice);
		this.rents = rents;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean landOn(GameController gameController) {

		if (this.owner == null) {
			if (gameController.getPlayerController().getPlayer(gameController.getTurn() - 1).getAccount().getBalance() >= price) {
				boolean answer = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Brewery?");
				if (answer == true) {
					owner = gameController.getPlayerController().getPlayer(gameController.getTurn() - 1);
					gameController.getPlayerController().getPlayer(gameController.getTurn() - 1).getAccount().adjustBalance(-price);
					return true;
					
				} 
				else if (answer == false) {
					return true;
				}
			}
		}

		if (this.owner != null && this.owner != gameController.getPlayerController().getPlayer(gameController.getTurn()-1) && this.owner.isJailed()==false) {
			int i = gameController.getFieldController().getOwnerShipOfBreweries(gameController.getPlayerController().getPlayer(gameController.getTurn()));
			int pay = rents[i] * gameController.getCup().getDiceSum();
			if (gameController.getPlayerController().getPlayer(gameController.getTurn()).getAccount()
					.getBalance() > pay) {
				gameController.getPlayerController().getPlayer(gameController.getTurn()).getAccount()
						.adjustBalance(-pay);
				((Ownable)gameController.getFieldController().getFields()[this.getNumber()]).getOwner().getAccount().adjustBalance(pay);
				return true;
				
			} else
				return false;

		}

		return true;
	}

}
