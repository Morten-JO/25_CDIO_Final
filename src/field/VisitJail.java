package field;

public class VisitJail extends Field {

	public VisitJail(String Titel, String Sub, String Desc, int fieldNo) {
		super(Titel, Sub, Desc, fieldNo);
		
	}

	@Override
	public boolean landOn(GameController gameController) {
		// TODO Auto-generated method stub
		return true;
	}

}
