package Player;

public class Player {
	
	private String name;
	private Account acc;
	private int position;
	private boolean isJailed;

	public Player(String name) {
		this.name = name;
		acc = new Account();
		position = 0;
		isJailed = false;
	}
	
	public int getBalance(int i){
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

	public Account getAcc() {
		return acc;
	}

}
