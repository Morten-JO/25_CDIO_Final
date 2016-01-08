package field;

import player.Player;
import controllers.GameController;

public class Ownable extends Field {
 protected Player owner ;
 protected int price ; 
 protected int pawnPrice;
	public Ownable(String Titel, String Sub, String Desc, int fieldNo, int price,int pawnPrice) {
		super(Titel, Sub, Desc, fieldNo);
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
	
	public int getRent(){
		return 0;
		}
	@Override
	public boolean landOn(GameController GameController) {
		// TODO Auto-generated method stub
		return false;
	}
	}
