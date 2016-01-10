package chancecards;

import java.awt.Point;

import controllers.FieldController;
import controllers.GameController;
import player.Player;

public class PayTaxRaiseCC extends PayMoneyCC {

	public PayTaxRaiseCC(String description) {
		super(description, 0);
	}

	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		
		//get total amount of houses and hotels owned in a Point(houses, hotels)
		Point buildingsOwned = FieldController.getBuildingsOwnedByPlayer(currentPlayer);
		
		//amount to pay
		amount = 800*buildingsOwned.x + 2300*buildingsOwned.y;
		
		return currentPlayer.adjustBalance(-amount);
	}
}
