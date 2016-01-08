package field;

import controllers.GameController;

public class Start extends Field {

	public Start(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean landOn(GameController GameController) {
		// TODO Auto-generated method stub
		return true;
	}

}
