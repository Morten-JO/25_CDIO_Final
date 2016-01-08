package player;
public class Player {

	private String name = "";
	private Account account;
	private int position;
	private boolean isJailed;
	
	public Player(){
		position = 0;
		account = new Account(30000);
	}
	
	public boolean adjustPoints(int dif){
		return account.adjustBalance(dif);
	}
	
	public boolean setBalance(int balance){
		return account.setBalance(balance);
	}
	
	public int getBalance(){
		return account.getBalance();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getPosition(){
		return position;
	}
	
	public void setPosition(int newPos){
		this.position = newPos;
	}

	/**
	 * @return  
	 * return true if a player is jailed, or false if player is not jailed.
	 */
	public boolean getJailed() {
		if (isJailed==true){
		return true;}
		else {
			return false;
		}
	}

	/**
	 * @param Puts a player in jail
	 */
	public void setInJail() {
		this.isJailed = true;
	}
	
}