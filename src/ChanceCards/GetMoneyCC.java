package ChanceCards;

public class GetMoneyCC extends ChanceCard {
	
	private int amount;

	public GetMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}

	@Override
	public boolean drawCardAction(GameControllerold gc){
		int currentPlayerIndex = gc.pc.getCurrentPlayer();
		return gc.pc.getPlayer(currentPlayerIndex).adjustBalance(amount);
	}

}
