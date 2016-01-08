package field;

import controllers.GameController;

public class Jail extends Field {

	public Jail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public boolean landOn(GameController gameController) {
		gameController.getPlayerController().getPlayer(gameController.getTurn()-1).setJailed(true);
		return true;
		
	}

}
