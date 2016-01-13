package field;

import controllers.GameController;
import controllers.Language;


public class Jail extends Field {

	public Jail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public boolean landOn(GameController gameController) {
		gameController.getGUIController().showMessage(Language.FieldController_GoToJail);
		gameController.getPlayerController().getCurrentPlayer().setJailed(true);
		return true;
		
	}

}
