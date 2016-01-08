package field;

import controllers.GameController;

/* 
 * Mangler taxprocent, den skal laves 
 */
public class Tax extends Field {
	
	private int tax ;
	
	public Tax (String Titel, String Sub, String Desc, int fieldNo, int tax){
		super(Titel,Sub,Desc,fieldNo);
		this.tax = tax;
		
	}
		
	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	@Override
	public boolean landOn(GameController GameController) {
		if (GameController.getPlayerController().getPlayer()[GameController.getTurn].getBalance()>tax){
		GameController.getPlayerController().getPlayer()[GameController.getTurn].adjustBalance(tax);
		return true;
		}
		else return false;
	}
}
