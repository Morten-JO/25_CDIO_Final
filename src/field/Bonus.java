package field;

import controllers.GameController;

public class Bonus extends Field {
		
	protected int Bonus; 
	
	public Bonus(String Titel, String Sub, String Desc, int fieldNo, int Bonus) {
		super(Titel, Sub, Desc, fieldNo);
		this.Bonus = Bonus;
	}
	
	public int getBonus(){
		return Bonus;
	}
	
	public void setBonus(int b){
		this.Bonus = b;
	}

	@Override
	public boolean landOn(GameController gameController) {
		GameController.getPlayerController().getPlayer()[GameController.getTurn()].adjustBalance(Bonus);
		return true;
		
	}
}
