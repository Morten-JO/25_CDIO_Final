package ChanceCards;

public class ScholarshipCC extends PayMoneyCC {

	public ScholarshipCC(String description, int amount) {
		super(description, amount);
		
		this.amount = amount;
	}
	
	@Override
	public boolean drawCardAction(GameControllerold gc){
		
		//if player total val < xx GUARD
		//add 40k to player acc
		
		return true;
	}

}
