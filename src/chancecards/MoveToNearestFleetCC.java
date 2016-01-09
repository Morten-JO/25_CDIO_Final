package chancecards;

import controllers.GameController;
import field.Field;
import field.Fleet;
import player.Player;

public class MoveToNearestFleetCC extends ChanceCard {

	public MoveToNearestFleetCC(String description) {
		super(description);

	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		int currentPos = currentPlayer.getPosition();
		Field[] fieldArray = gc.getFieldController().getFields();
		
		//find out what fleet is the nearest, set player position and call landOn field.
		//fleets are placed at 5,15,25,35. start is 0
		if(currentPos<5){
			currentPlayer.setPosition(5);
			((Fleet)fieldArray[5]).setRentMultiplier(2);
			((Fleet)fieldArray[5]).landOn(gc);
		}else if(currentPos<15){
			currentPlayer.setPosition(15);
			((Fleet)fieldArray[15]).setRentMultiplier(2);
			((Fleet)fieldArray[15]).landOn(gc);
		}else if(currentPos<25){
			currentPlayer.setPosition(25);
			((Fleet)fieldArray[25]).setRentMultiplier(2);
			((Fleet)fieldArray[25]).landOn(gc);
		}else if(currentPos<35){
			currentPlayer.setPosition(35);
			((Fleet)fieldArray[35]).setRentMultiplier(2);
			((Fleet)fieldArray[35]).landOn(gc);
		}
		
		//also add 4k to player acc IF we are passing start (range:35-39->0)
		if(currentPos>=35){
			currentPlayer.adjustBalance(4000);
			currentPlayer.setPosition(5);
			((Fleet)fieldArray[5]).landOn(gc);
		}
		
		//update car position on GUI (to bypass position not being updated before questions asked due to app-stall from landOn)
		gc.getGUIController().updatePlayerPosition(currentPlayer);
			
		
		return true;
	}

}
