package field;

import controllers.GameController;


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
			
			boolean question = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Fleet");
			if (question == true){
		gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance(-price);
		this.owner = gameController.getPlayerController().getPlayer(gameController.getTurn()-1);
		return true;
		
			}
			if (question == false){
			return true;}
	}
		
		if (this.owner != null && this.owner != gameController.getPlayerController().getPlayer(gameController.getTurn()-1) && this.owner.isJailed()==false){
			int fleetsowned = gameController.getFieldController().getOwnerShipOfFleets(this.owner);
			int toPay = rents [fleetsowned-1];
			if(gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().getBalance()>toPay){
			gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance(-toPay);
			((Ownable)gameController.getFieldController().getFields()[this.getNumber()]).getOwner().getAccount().adjustBalance(toPay);
			return true;
}		
			else return false;
	
}
		return true;
}
}