package player;
public class Player {

	private String name = "";
	private Account account;
	private int position;
	private boolean isJailed;
	
	public Player(String name){
		this.name = name;
		position = 0;
		account = new Account(30000);
	}
	
	
	public Account getAccount(){
		return account;
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