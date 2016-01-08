package ChanceCards;

public class ChanceCard {
	
	protected String cardDescription;
	
	public ChanceCard(String description){
		this.cardDescription = description;
	}
	
	/**
	 * Method should to be overridden in subclasses
	 * @param gc needed to coordinate with PlayerController
	 * @return 
	 */
	public boolean drawCardAction(GameControllerold gc){
		//OVERRIDE
		return true;
	}

}
