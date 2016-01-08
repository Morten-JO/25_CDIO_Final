package playerMO;


public class Account {

	private int balance;
	
	public Account(int balance){
		setBalance(balance);
	}
	
	public boolean setBalance(int balance){
		if(balance < 0){
			this.balance = 0;
			return false;
		}
		else{
			this.balance = balance;
			return true;
		}
	}
	
	public boolean adjustBalance(int dif){
		if(balance + dif < 0){
			return false;
		}
		balance += dif;
		return true;
	}
	
	public int getBalance(){
		return balance;
	}
}