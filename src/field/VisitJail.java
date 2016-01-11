package field;

import controllers.GameController;

public class VisitJail extends Field {

	public VisitJail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);

	}

	@Override
	public boolean landOn(GameController gameController) {
		gameController.getGUIController().showMessage("Du lander på besøg fængsel og der sker ikke noget"); //$NON-NLS-1$
       return true;
	}

}
