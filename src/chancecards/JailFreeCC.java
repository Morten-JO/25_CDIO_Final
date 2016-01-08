package chancecards;

import controllers.GameController;

public class JailFreeCC extends ChanceCard {

	public JailFreeCC(String description) {
		super(description);
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		//add jail option to player?
		return true;
	}
}
