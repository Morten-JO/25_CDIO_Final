package chancecards;

import java.awt.Point;

import controllers.FieldController;
import controllers.GameController;
import player.Player;

public class PayOilRaiseCC extends PayMoneyCC {

	public PayOilRaiseCC(String description) {
		super(description, 0);
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		
		//get total amount of houses and hotels owned in a Point(houses, hotels)
		Point buildingsOwned = gc.getFieldController().getBuildingsOwnedByPlayer(currentPlayer);
		
		//amount to pay
		amount = 500*buildingsOwned.x + 2000*buildingsOwned.y;
		
		return currentPlayer.adjustBalance(-amount);
	}

}
