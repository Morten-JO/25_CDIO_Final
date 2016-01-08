package ChanceCards;

public class PayMoneyCC extends ChanceCard {
	
	protected int amount;

	public PayMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameControllerold gc){
		int currentPlayerIndex = gc.pc.getCurrentPlayer();
		return gc.pc.getPlayer(currentPlayerIndex).adjustBalance(-amount);
	}

}
