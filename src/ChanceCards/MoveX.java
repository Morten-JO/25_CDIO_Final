package ChanceCards;

public class MoveX extends ChanceCard {
	
	private int amountToMove;

	public MoveX(String description, int amount) {
		super(description);
		this.amountToMove = amount;
	}
	
	@Override
	public boolean drawCardAction(GameControllerold gc){
		int currentPlayerIndex = gc.pc.getCurrentPlayer();
		int positionTo = gc.pc.getPlayer(currentPlayerIndex).getPosition() + amountToMove;
		
		if(positionTo>40)
			positionTo -= 40;
		
		gc.landOn(currentPlayerIndex, positionTo);
		
		return true;
	}

}
