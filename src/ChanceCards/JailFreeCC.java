package ChanceCards;

import controllers.GameController;

public class JailFreeCC extends ChanceCard {

	public JailFreeCC(String description) {
		super(description);
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		
		return true;
	}
}
