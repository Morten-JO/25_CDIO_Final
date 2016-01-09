 package chancecards;

import controllers.GameController;
import player.Player;

public class MoveTo extends ChanceCard {
	
	private int fieldID;

	public MoveTo(String description, int ID) {
		super(description);
		this.fieldID = ID;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		int pos = currentPlayer.getPosition();
		
		//add 4k if player pass start
		if(fieldID<pos){
			if(fieldID!=39)//townhallCC no reward for passing start
				currentPlayer.adjustBalance(4000);
		}
		System.out.println("Landing on FieldID: "+fieldID);
		//set new player pos
		currentPlayer.setPosition(fieldID);
		
		//update car position on GUI (to bypass position not being updated before questions asked due to app-stall from landOn)
		gc.getGUIController().updatePlayerPosition(currentPlayer);
		
		//not calling landOn because player is only moved to field; NO ACTION TAKEN
		return true;
	}

}
