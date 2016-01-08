package ChanceCards;

public class MoveTo extends ChanceCard {
	
	private int fieldID;

	public MoveTo(String description, int ID) {
		super(description);
		this.fieldID = ID;
	}
	
	@Override
	public boolean drawCardAction(GameControllerold gc){
		return gc.landOn(gc.pc.getCurrentPlayer(), fieldID);
	}

}
