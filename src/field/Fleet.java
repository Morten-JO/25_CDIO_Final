package field;

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
		if (this.owner == null && GameController.getPlayerController().getPlayer()[GameController.getTurn].adjustBalance>= this.price){
			
			String i = GUI.getUserButtonPressed("Do you want to buy this Fleet ", "Yes","No");
			if (i == "Yes"){
		GameController.getPlayerController().getPlayer()[GameController.getTurn()].adjustBalance(-price);
		owner = GameController.getPlayerController().getPlayer()[GameController.getTurn()];
		return true;
		
			}
			if (i == "No"){
			return true;}
	}
		// MANGLER DENN HER !!!!!!!!! skal finde hvor mange fleets en ejer har .
		if (this.owner != null && this.owner != GameController.getPlayerController().getPlayer()[GameController.getTurn()]){
			int totalPay = field.FieldController. 
					rents[FieldController.this.owner.getOwnershipOfFleets-1]
}
}