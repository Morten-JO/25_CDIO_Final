package field;

import desktop_resources.GUI;

public class TaxProcent extends Tax {
	private int taxProcent;
	
	public TaxProcent(String Titel, String Sub, String Desc, int fieldNo, int tax,int taxPro) {
		super(Titel, Sub, Desc, fieldNo, tax);
		
		this.taxProcent = taxPro;
	}
	@Override
	public boolean landOn(GameController gameController){
		
		String i = GUI.getUserButtonPressed("Do you want to Pay?", "4000","10 %");
		if (i == "4000"){
			GameController.getPlayerController().getPlayer()[GameController.getTurn].adjustBalance(tax);
		}
		else if (i == "10 %"){
			int taxpay =GameController.getFieldController().getTotalValueOfPlayer(GameController.getPlayerController().getPlayer()[GameController.getTurn]);
			GameController.getPlayerController().getPlayer()[GameController.getTurn].adjustBalance(-(taxpay*(0.01*taxProcent)));
		}
		
		
		return false;
		
	}
}
