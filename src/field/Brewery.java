package field;
import controllers.GameController;
import desktop_resources.GUI;

public class Brewery extends Ownable {
	private int rents = 100 ;
	public Brewery(String Titel, String Sub, String Desc, int fieldNo, int price, int pawnPrice) {
		super(Titel, Sub, Desc, fieldNo, price, pawnPrice);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean landOn(GameController gameController) {
		
		if (this.owner == null){
			if (GameController.getPlayerController().getPlayer()[GameController.getTurn].getBalance() >= price){
				if (i=="Ja"){
					owner = GameController.getPlayerController().getPlayer()[GameController.getTurn];
					GameController.getPlayerController().getPlayer()[GameController.getTurn].adjustBalance(-price);
					return true;
				}
				else if (i=="nej"){
					return true;
				}
			}
			}
			
			if  (this.owner != null && this.owner != GameController.getPlayerController().getPlayer()[GameController.getTurn()]){
				int pay = rents * Gamecontroller.getDiceSum();
				if (GameController.getPlayerController().getPlayer()[GameController.getTurn].getBalance>pay){
				GameController.getPlayerController().getPlayer()[GameController.getTurn].adjustBalance(-pay);
				return true;
				}
				else return false;
		
		
	}
	

}
	
}
