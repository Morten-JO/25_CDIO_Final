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
		boolean result = true;
		if (this.owner == null) {
			if (gameController.getPlayerController().getPlayer(gameController.getTurn() - 1).getAccount().getBalance() >= price) {
				//boolean answer = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Brewery?");
				String answer = GUI.getUserButtonPressed("Do you want to buy this Brewery?","Yes","No");
				if (answer.equals("Yes")) {
					owner = gameController.getPlayerController().getPlayer(gameController.getTurn() - 1);
					result = gameController.getPlayerController().getPlayer(gameController.getTurn() - 1).getAccount().adjustBalance(-price);
					
					
				} 
				else if (answer.equals("No")) {
					result = true;
				}
			}
		}

		if (this.owner != null && this.owner != gameController.getPlayerController().getPlayer(gameController.getTurn()-1) && this.owner.isJailed()==false) {
			int i = gameController.getFieldController().getOwnerShipOfBreweries(gameController.getPlayerController().getPlayer(gameController.getTurn()));
			int pay = rents[i] * gameController.getCup().getDiceSum();
			if (gameController.getPlayerController().getPlayer(gameController.getTurn()).getAccount()
					.getBalance() > pay) {
				 gameController.getPlayerController().getPlayer(gameController.getTurn()).getAccount().adjustBalance(-pay);
				 result = this.owner.adjustBalance(pay);
				
				
			} else
				result = false;

		}

		return result; 
	}

}
