package dices;

public class Dice {

	private int sum;
	private int lastsum;
	private int sides;
	
	public Dice(int sides){
		this.sides = sides;
	}
	
	private int diceRoll(){
		return (int)(Math.random() * sides)+1;
	}
	
	public void setDiceForTest(int i){
		lastsum = sum;
		sum = i;
	}
	
	public void hitDice(){
		lastsum = sum;
		sum = diceRoll();
	}
	
	public int getSum(){
		return sum;
	}
	
	public int getLastSum(){
		return lastsum;
	}
	
	public int getSides(){
		return sides;
	}
}