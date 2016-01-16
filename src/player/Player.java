package player;

public class Player {
	
	private String name;
	private Account acc;
	private int position;
	private int jailFreeCards;
	private boolean isJailed;
	private boolean firstRoundCompleted;
	private int jailedRounds;

	public Player(String name) {
		this.name = name;
		acc = new Account();
		position = 0;
		isJailed = false;
	}
	
	public int getBalance(){
		return acc.getBalance();
	}
	
	public boolean adjustBalance(int amount){
		return acc.adjustBalance(amount);
	}
	
	//GETTERS AND SETTERS
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isJailed() {
		return isJailed;
	}

	public void setJailed(boolean isJailed) {
		this.isJailed = isJailed;
	}

	public String getName() {
		return name;
	}

	public Account getAccount() {
		return acc;
	}
	
	public boolean getFirstRoundCompleted(){
		return firstRoundCompleted;
	}
	
	public void setFirstRoundCompleted(boolean newBool){
		firstRoundCompleted = newBool;
	}
	
	public int getJailFreeCards(){
		return jailFreeCards;
	}	
	
	public void setJailFreeCards(int i){
		jailFreeCards = i;
	}
	
	public void setJailedRounds(int jailedRounds){
		this.jailedRounds = jailedRounds;
	}
	
	public int getJailedRounds(){
		return jailedRounds;
	}

}
