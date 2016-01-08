package field;
import controllers.GameController;
import desktop_resources.GUI;
import player.Player;

public class Brewery extends Ownable {
	private int rents = 100 ;
	public Brewery(String titel, String sub, String desc, int fieldNo, int price, int pawnPrice) {
		super(titel, sub, desc, fieldNo, price, pawnPrice);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean landOn(GameController gameController) {
		
		if (this.owner == null){
			if ( gameController.getPlayerController().getPlayers[gameController.getTurn()-1]).getBalance() >= price){
				if (i=="Ja"){
					owner = gameController.getPlayerController().getPlayer()[gameController.getTurn];
					gameController.getPlayerController().getPlayer()([gameController.getTurn]-1).adjustBalance(-price);
					return true;
				}
				else if (i=="nej"){
					return true;
				}
			}
			}
			
			if  (this.owner != null && this.owner != gameController.getPlayerController().getPlayer()[GameController.getTurn()]){
				int pay = rents * gameController.getCup().getDiceSum();
				if (GameController.getPlayerController().getPlayer()[GameController.getTurn].getBalance>pay){
				GameController.getPlayerController().getPlayer()[GameController.getTurn].adjustBalance(-pay);
				return true;
				}
				else return false;
		
		
	}
	

}
	
}
