package player;

public class Account {
	private int balance;

	public Account() {
		balance = 30000;
	}
	
	
	/**
	 * @param d - the amount to adjust
	 * @return false if player cant play (decide if remove from game)
	 */
	public boolean adjustBalance(int d){
		int newBalance = balance + d;
		if(newBalance<0){
			balance = newBalance;
			return false;
		}
		else{
			balance = newBalance;
			return true;
		}
	}
	
	public int getBalance(){
		return balance;
	}

}
