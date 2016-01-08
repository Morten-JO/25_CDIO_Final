package ChanceCards;

public class JailFreeCC extends ChanceCard {

	public JailFreeCC(String description) {
		super(description);
	}
	
	@Override
	public boolean drawCardAction(GameControllerold gc){
		
		return true;
	}
}
