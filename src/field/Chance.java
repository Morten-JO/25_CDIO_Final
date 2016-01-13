 package field;

import controllers.GameController;
import controllers.Language;;


public class Chance extends Field {

	public Chance(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);
	
	}

	@Override
	public boolean landOn(GameController gameController) {
		
		gameController.getGUIController().showMessage(Language.Field_ChanceField);
		return gameController.getChanceCardController().drawCard(gameController);
		
		
	}

}
