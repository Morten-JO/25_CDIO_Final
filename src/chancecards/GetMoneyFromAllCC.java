package chancecards;

import controllers.GameController;
import player.Player;

public class GetMoneyFromAllCC extends GetMoneyCC {

	public GetMoneyFromAllCC(String description, int amount) {
		super(description, amount);
		
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		
		for(Player player : gc.getPlayerController().getPlayerList()){
			if(gc.getPlayerController().getCurrentPlayer() != player){
				player.getAccount().adjustBalance(-amount);
				currentPlayer.adjustBalance(amount);
			}
			//IMPORTANT!! NEED TO HANDLE IF PLAYER CANT PAY. CANNOT RETURN FALSE OR METHOD WILL EXIT
		}
		return true;
	}

}
