package chancecards;

import controllers.GameController;
import player.Player;

public class PayMoneyCC extends ChanceCard {
	
	protected int amount;

	public PayMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer =  gc.getPlayerController().getCurrentPlayer();
		return currentPlayer.adjustBalance(-amount);
	}

}
