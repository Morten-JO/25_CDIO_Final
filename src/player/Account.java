package player;

public class Account {
	private int balance;

	public Account() {
		balance = 30000;
	}
	
	
	/**
	 * 
	 * @param i - the amount to adjust
	 * @return false if player cant play (decide if remove from game)
	 */
	public boolean adjustBalance(int i){
		int newBalance = balance + i;
		if(newBalance<0){
			//do stuff to handle not able to pay
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
