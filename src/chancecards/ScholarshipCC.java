package chancecards;

import controllers.GameController;

public class ScholarshipCC extends PayMoneyCC {

	public ScholarshipCC(String description, int amount) {
		super(description, amount);
		
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		
		int totalPlayerValue = gc.getPlayerController().getTotalValueOfPlayer(gc.getPlayerController().getCurrentPlayer(), gc.getFieldController());
		
		if(totalPlayerValue<15000)
			gc.getPlayerController().getCurrentPlayer().adjustBalance(40000);
		
		return true;
	}

}
