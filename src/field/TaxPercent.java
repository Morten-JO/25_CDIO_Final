package field;

import controllers.GameController;


public class TaxPercent extends Tax {
	private int taxProcent;
	
	public TaxPercent(String Titel, String Sub, String Desc, int fieldNo, int tax,int taxPro) {
		super(Titel, Sub, Desc, fieldNo, tax);
		
		this.taxProcent = taxPro;
	}
	@Override
	public boolean landOn(GameController gameController){
		
		String answer  = gameController.getGUIController().askQuestion("Do you want to pay", "4000","10%");
		if (answer == "4000"){
			return gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance(- tax);
		}
		else {
			int taxpay =gameController.getTotalValueOfPlayer(gameController.getPlayerController().getPlayer(gameController.getTurn()-1));
			return gameController.getPlayerController().getPlayer(gameController.getTurn()-1).getAccount().adjustBalance((int)-(taxpay*(0.01*taxProcent)));
		}
	}
}
