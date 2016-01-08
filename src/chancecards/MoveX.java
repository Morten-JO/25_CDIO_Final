package chancecards;

import controllers.GameController;
import player.Player;

public class MoveX extends ChanceCard {
	
	private int amountToMove;

	public MoveX(String description, int amount) {
		super(description);
		this.amountToMove = amount;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		int positionTo = currentPlayer.getPosition() + amountToMove;
		
		if(positionTo>40){
			positionTo -= 40;
			//check if we PASSED start field
			if(positionTo>0)
				currentPlayer.adjustBalance(4000);
		}
		
		gc.getFieldController().getFields()[positionTo].landOn(gc);
		
		return true;
	}

}
