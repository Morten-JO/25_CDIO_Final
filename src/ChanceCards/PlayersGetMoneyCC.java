package ChanceCards;

import player.Player;

public class PlayersGetMoneyCC extends ChanceCard {
	
	private int amount;

	public PlayersGetMoneyCC(String description, int amount) {
		super(description);
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameControllerold gc){
		for(Player player : gc.pc.getPlayerList()){
			player.getAccount().adjustBalance(amount);
		}
		return true;
	}

}
