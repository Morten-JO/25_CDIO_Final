package chancecards;

import controllers.GameController;
import player.Player;

public class GetMoneyCC extends ChanceCard {
	
	protected int amount;

	public GetMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}

	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		return currentPlayer.adjustBalance(amount);
	}

}
