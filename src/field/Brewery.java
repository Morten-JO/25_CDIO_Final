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
			if (gameController.getPlayerController().getCurrentPlayer().getAccount().getBalance() >= price) {
				//boolean answer = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Brewery?");
				String answer = GUI.getUserButtonPressed("Do you want to buy this Brewery?","Yes","No");
				if (answer.equals("Yes")) {
					owner = gameController.getPlayerController().getCurrentPlayer();
					result = gameController.getPlayerController().getCurrentPlayer().getAccount().adjustBalance(-price);
					
					
				} 
				else if (answer.equals("No")) {
					result = true;
				}
			}
		}

		if (this.owner != null && this.owner != gameController.getPlayerController().getPlayer(gameController.getTurn()-1) && this.owner.isJailed()==false) {
			int i = gameController.getFieldController().getOwnerShipOfBreweries(gameController.getPlayerController().getPlayer(gameController.getTurn()));
			int pay = rents[i] * gameController.getCup().getDiceSum();//REMOVED -1 CAUSING ARRAY_OOB. turn starts at 0
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
