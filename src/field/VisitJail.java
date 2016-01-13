package field;

import controllers.GameController;
import controllers.Language;

public class VisitJail extends Field {

	public VisitJail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);

	}

	@Override
	public boolean landOn(GameController gameController) {
		gameController.getGUIController().showMessage(Language.Field_VisitJail);
       return true;
	}

}
