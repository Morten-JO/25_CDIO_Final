package field;

import controllers.GameController;


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
		gameController.getGUIController().showMessage("Du er landet på en skattefelt og skal betale til banken kr. " +this.tax);
		if (gameController.getPlayerController().getCurrentPlayer().getBalance()>tax){
		return gameController.getPlayerController().getCurrentPlayer().adjustBalance(tax);
		}
		else return false;
	}
}
