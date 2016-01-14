package field;

import controllers.GameController;
import controllers.Language;;

public class Bonus extends Field {
		
	protected int bonus; 
	
	public Bonus(String titel, String sub, String desc, int fieldNo, int Bonus) {
		super(titel, sub, desc, fieldNo);
		this.bonus = Bonus;
	}
	
	public int getBonus(){
		return bonus;
	}
	
	public void setBonus(int b){
		this.bonus = b;
	}

	
	@Override
	public boolean landOn(GameController gameController) {
		gameController.getGUIController().showMessage(Language.Field_Bonus +this.bonus + "!");
		return gameController.getPlayerController().getPlayer(gameController.getTurn()).getAccount().adjustBalance(bonus);
		
		
	}
}
