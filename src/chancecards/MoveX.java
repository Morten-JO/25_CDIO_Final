package chancecards;

import controllers.GameController;

public class MoveX extends ChanceCard {
	
	private int amountToMove;

	public MoveX(String description, int amount) {
		super(description);
		this.amountToMove = amount;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		int currentPlayerIndex = gc.getPlayerController().getCurrentPlayer();
		int positionTo = gc.getPlayerController().getPlayer(currentPlayerIndex).getPosition() + amountToMove;
		
		if(positionTo>40){
			positionTo -= 40;
			gc.getPlayerController().getPlayer(currentPlayerIndex);
		}
		
		gc.getFieldController().getFields()[positionTo].landOn(gc);
		
		return true;
	}

}
