package field;

import controllers.GameController;
import player.Player;

public class Ownable extends Field {
 protected Player owner ;
 protected int price ; 
 protected int pawnPrice;
 protected boolean isPawn;
 
 
	public Ownable(String Titel, String Sub, String Desc, int fieldNo, int price,int pawnPrice) {
		super(Titel, Sub, Desc+": "+price, fieldNo);
		this.price = price;
		this.pawnPrice = pawnPrice;
		this.owner = null;
		
		}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPawnPrice() {
		return pawnPrice;
	}
	public void setPawnPrice(int pawnPrice) {
		this.pawnPrice = pawnPrice;
	}
	
	public int getRent(GameController gameController){
		return 0;
	}
	@Override
	public boolean landOn(GameController gameController) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean pawnProperty(GameController gameController, Player player){
		if (!this.isPawn){
			this.isPawn = true;
			player.adjustBalance(this.pawnPrice);
			System.out.println("Pantsætningsværdien er: " +this.getPawnPrice());
			return true;
		}
		
		return false;
	}
	
	public boolean getIsPawn(){
		return isPawn;
	}
	
	
	//Unpantsæt igen
	public boolean unPawnProperty(GameController gameController, Player player){
		for (int i = 0; i<gameController.getFieldController().getFields().length; i++){
			if (gameController.getFieldController().getFields()[i] instanceof Ownable){
				if (((Ownable)gameController.getFieldController().getFields()[i]).getOwner() != null){
					if (((Ownable)gameController.getFieldController().getFields()[i]).getOwner().equals(player) && player.getAccount().getBalance() >= this.pawnPrice && this.isPawn == true){
					this.isPawn = false;
					player.adjustBalance(this.pawnPrice);
					return true;
				}
				
			}
			
		}
		}
		return false;
	}
}