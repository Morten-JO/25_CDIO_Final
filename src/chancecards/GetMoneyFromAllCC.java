package chancecards;

import controllers.GameController;
import controllers.PlayerController;
import player.Player;

public class GetMoneyFromAllCC extends GetMoneyCC {

	public GetMoneyFromAllCC(String description, int amount) {
		super(description, amount);
		
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		
		PlayerController pc = gc.getPlayerController();
		Player currentPlayer = gc.getPlayerController().getCurrentPlayer();
		
		for(int i = 0; i<pc.getPlayerList().size();i++){
			if(pc.getCurrentPlayerIndex() != i){
				if(!(pc.getPlayer(i).getAccount().adjustBalance(-amount))){
					gc.handleRemovePlayer(pc.getPlayer(i));
					i--;
				}
				currentPlayer.adjustBalance(amount);
			}
			//IMPORTANT!! NEED TO HANDLE IF PLAYER CANT PAY. CANNOT RETURN FALSE OR METHOD WILL EXIT
		}
		return true;
	}

}
