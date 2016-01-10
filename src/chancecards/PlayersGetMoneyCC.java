package chancecards;

import controllers.GameController;
import player.Player;

public class PlayersGetMoneyCC extends GetMoneyCC {

	public PlayersGetMoneyCC(String description, int amount) {
		super(description, amount);
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		for(Player player : gc.getPlayerController().getPlayerList()){
			player.getAccount().adjustBalance(amount);
		}
		return true;
	}
	
	public String toString(){
		return cardDescription;
	}

}
