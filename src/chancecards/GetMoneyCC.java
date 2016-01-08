package chancecards;

import controllers.GameController;

public class GetMoneyCC extends ChanceCard {
	
	private int amount;

	public GetMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}

	@Override
	public boolean drawCardAction(GameController gc){
		int currentPlayerIndex = gc.getPlayerController().getCurrentPlayer();
		return gc.getPlayerController().getPlayer(currentPlayerIndex).adjustBalance(amount);
	}

}
