package player;

public class Player {
	
	private int balance;
	private String name;
	private int position;
	private Account account;
	
	public int getBalance(){
		return balance;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPosition(){
		return position;
	}
	
	public boolean Jail(boolean b) {
		return b;
		
	}

	public boolean JailNoMoney(boolean b) {
		return b;
		
	}
	
	public Account getAccount(){
		return account;
	}

}
