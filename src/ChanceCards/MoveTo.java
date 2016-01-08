package ChanceCards;

import controllers.GameController;

public class MoveTo extends ChanceCard {
	
	private int fieldID;

	public MoveTo(String description, int ID) {
		super(description);
		this.fieldID = ID;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		return gc.getFieldController().getFields()[fieldID].landOn(gc);
	}

}
