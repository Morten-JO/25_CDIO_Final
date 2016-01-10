package field;

import controllers.GameController;

/* 
 * Mangler taxprocent, den skal laves 
 */
public class Tax extends Field {
	
	protected int tax ;
	
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
	public boolean landOn(GameController gameController) {
		if (gameController.getPlayerController().getCurrentPlayer().getBalance()>tax){
		return gameController.getPlayerController().getCurrentPlayer().adjustBalance(tax);
		}
		else return false;
	}
}
