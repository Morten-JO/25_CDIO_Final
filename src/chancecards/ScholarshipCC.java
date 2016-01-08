package chancecards;

import controllers.GameController;

public class ScholarshipCC extends PayMoneyCC {

	public ScholarshipCC(String description, int amount) {
		super(description, amount);
		
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameController gc){
		
		//if player total val < 15000 GUARD
		//add 40k to player acc
		
		return true;
	}

}
