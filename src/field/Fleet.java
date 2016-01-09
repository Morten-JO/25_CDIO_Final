package field;

import controllers.GameController;


public class Fleet extends Ownable {
	private int[] rents = {500,1000,2000,4000};
	private int rentMultiplier;
	
       
	public Fleet(String Titel, String Sub, String Desc, int fieldNo, int price, int pawnPrice,int [] rents) {
		super(Titel, Sub, Desc, fieldNo,price, pawnPrice);
		this.rents = rents;
		rentMultiplier = 1;
	}
	
	public int getRents(int FleetOwned){
		return rents[FleetOwned-1];
	}
	
	public void setRentMultiplier(int i){
		rentMultiplier = i;
	}
	
	@Override
	public boolean landOn(GameController gameController) {
		boolean result = true;
		//check if player has enough balance to buy field, and that field has no owner
		if (this.owner == null && gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().getBalance() >= this.price){
			
			boolean question = gameController.getGUIController().askYesNoQuestion("Do you want to buy this Fleet");
			
			//if want to buy field = true
			if (question == true){
				gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance(-price);
				this.owner = gameController.getPlayerController().getPlayer(gameController.getTurn()-1);
				result = true;
			}else{
				result = true;
			}
			rentMultiplier = 1;
			return result;
		}
		
		//check owner not NULL, currentPlayer not owner, owner not jailed. PAY ACCORDINGLY
		if (this.owner != null && this.owner != gameController.getPlayerController().getPlayer(gameController.getTurn()-1) && this.owner.isJailed()==false){
			int fleetsowned = gameController.getFieldController().getOwnerShipOfFleets(this.owner);
			int toPay = rents [fleetsowned-1];
			
			//check if user has enough balance to pay
			if(gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().getBalance()>toPay){
				gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance(-toPay);
				((Ownable)gameController.getFieldController().getFields()[this.getNumber()]).getOwner().getAccount().adjustBalance(toPay);
				result = true;
			}else{
				result = false;}
		}
		return result;
}
}