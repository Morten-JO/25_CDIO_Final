package dices;

import java.util.ArrayList;

public class Cup {

	//Array of dices
	private ArrayList<Dice> diceArray = new ArrayList<Dice>();
	
	//Adds amount of dices with specific side amount.
	public Cup(int dices, int diceSides){
		addDices(dices, diceSides);
	}
	
	public void addDices(int dices, int diceSides){
		for(int i = 0; i < dices; i++){
			diceArray.add(new Dice(diceSides));
		}
	}
	
	public void rollDices(){
		for(int i = 0; i < diceArray.size(); i++){
			diceArray.get(i).hitDice();
		}
	}
	
	/**
	 * used for manually setting amount to move. used for testing purposes.
	 * Note: if user enters 12, and only 12, it shall be perceived as "2 of same"
	 * @param i - amount to move/set
	 */
	public void rollDicesCustom(int i){
			if(i==12){
				diceArray.get(0).setDiceForTest(6);
				diceArray.get(1).setDiceForTest(6);
			}else if(i>39)
				diceArray.get(0).setDiceForTest(39);
			else
				diceArray.get(0).setDiceForTest(i);
	}
	
	public int getDiceSum(){
		int sum = 0;
		for(int i = 0; i < diceArray.size(); i++){
			sum += diceArray.get(i).getSum();
		}
		return sum;
	}
	
	public int getSumOfDice(int index){
		if(index < diceArray.size()){
			return diceArray.get(index).getSum();
		}
		else{
			return 0;
		}
	}
	
	public int getLastDiceSum(){
		int lastSum = 0;
			for(int i = 0; i < diceArray.size(); i++){
				lastSum += diceArray.get(i).getLastSum();
			}
			return lastSum;
	}
	
	
	/* methods not used. keep code for future flexibility
	public int getLastSumOfDice(int index){
		if(index < diceArray.size()){
			return diceArray.get(index).getLastSum();
		}
		else{
			return 0;
		}
	}
	
	public int getAmountOfDices(){
		return diceArray.size();
	}
	*/
	
	public boolean isSameHit(){
		boolean isSameHit = true;
		for(int i = 0; i < diceArray.size()-1; i++){
			if(diceArray.get(i).getSum() != diceArray.get(i+1).getSum()){
				isSameHit = false;
			}
		}
		return isSameHit;
	}
}