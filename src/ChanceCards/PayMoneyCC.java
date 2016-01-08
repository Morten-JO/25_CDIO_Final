package ChanceCards;

import controllers.GameController;

public class PayMoneyCC extends ChanceCard {
	
	protected int amount;

	public PayMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		int currentPlayerIndex = gc.getPlayerController().getCurrentPlayer();
		return gc.getPlayerController().getPlayer(currentPlayerIndex).adjustBalance(-amount);
	}

}
