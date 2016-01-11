package field;

import controllers.GameController;

public class VisitJail extends Field {

	public VisitJail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);

	}

	@Override
	public boolean landOn(GameController gameController) {
		gameController.getGUIController().showMessage("Du er landet p� bes�g i f�ngsel. Du har helle her");
       return true;
	}

}
