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
					int orgBal = pc.getPlayer(i).getBalance() + amount;
					currentPlayer.adjustBalance(orgBal);
					gc.handleRemovePlayer(pc.getPlayer(i));
					i--;
				}else{
					currentPlayer.adjustBalance(amount);
				}
			}
			//IMPORTANT!! NEED TO HANDLE IF PLAYER CANT PAY. CANNOT RETURN FALSE OR METHOD WILL EXIT**FIXED WITH HANDLEREMOVEPLAYER() IN GC 
		}
		return true;
	}

}
