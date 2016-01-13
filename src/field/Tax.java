package field;

import controllers.GameController;
import controllers.Language;


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
		gameController.getGUIController().showMessage(Language.Field_TaxField +(this.tax*-1));
		if (gameController.getPlayerController().getCurrentPlayer().getBalance()>tax){
		return gameController.getPlayerController().getCurrentPlayer().adjustBalance(tax);
		}
		else return false;
	}
}
