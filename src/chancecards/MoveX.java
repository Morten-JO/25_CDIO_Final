package chancecards;

import java.util.ArrayList;
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
		}else if(positionTo<0){
			positionTo += 40;
			//does player get 4k for passing start backwards?
		}
		System.out.println("Landing on FieldID: "+positionTo);
		
		//update car position on GUI (to bypass position not being updated before questions asked due to app-stall from landOn)
		gc.getGUIController().updatePlayerPosition(currentPlayer);
		
		gc.getFieldController().getFields()[positionTo].landOn(gc);
		
		return true;
	}

}
