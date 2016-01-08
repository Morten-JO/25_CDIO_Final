package field;

public class Jail extends Field {

	public Jail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public boolean landOn(GameController gameController) {
		return GameController.getPlayerController().getPlayer()[GameController.getTurn].isJailed(true);
		
	}

}
