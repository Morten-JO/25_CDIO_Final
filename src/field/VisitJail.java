package field;

import controllers.GameController;
import controllers.Messages;

public class VisitJail extends Field {

	public VisitJail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);

	}

	@Override
	public boolean landOn(GameController gameController) {
		gameController.getGUIController().showMessage(Messages.getString("VisitJail.0")); //$NON-NLS-1$
       return true;
	}

}
