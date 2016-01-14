package chancecards;

import controllers.GameController;

public class JailFreeCC extends ChanceCard {

	public JailFreeCC(String description) {
		super(description);
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		//adds jail free cc to current player
		gc.getPlayerController().addJailFreeCard();
		return true;
	}
}
