package chancecards;

import controllers.GameController;
import player.Player;

public class PlayersGetMoneyCC extends ChanceCard {
	
	private int amount;

	public PlayersGetMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		for(Player player : gc.getPlayerController().getPlayerList()){
			player.getAccount().adjustBalance(amount);
		}
		return true;
	}

}
