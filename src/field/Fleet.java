package field;

import controllers.FieldController;
import controllers.GameController;
import desktop_resources.GUI;

public class Fleet extends Ownable {
	private int[] rents = {500,1000,2000,4000};
	
       
	public Fleet(String Titel, String Sub, String Desc, int fieldNo, int price, int pawnPrice,int [] rents) {
		super(Titel, Sub, Desc, fieldNo,price, pawnPrice);
		this.rents = rents;
	}
	
	public int getRents(int FleetOwned){
		return rents[FleetOwned-1];
	}
	@Override
	public boolean landOn(GameController gameController) {
		if (this.owner == null && gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().getBalance() >= this.price){
			
			boolean i = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Fleet");
			if (i == true){
		gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance(-price);
		owner = gameController.getPlayerController().getPlayer(gameController.getTurn()-1);
		return true;
		
			}
			if (i == false){
			return true;}
	}
		
		if (this.owner != null && this.owner != gameController.getPlayerController().getPlayer(gameController.getTurn()-1)){
			int totalPay = gameController.getFieldController().getOwnerShipOfFleets(this.owner);
			totalPay = rents [totalPay-1];
			if(gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().getBalance()>totalPay){
			gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance(-totalPay);
}		
			else return false;
	
}
		return true;
}
}