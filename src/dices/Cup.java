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
	
	public int getDiceSum(){
		int sum = 0;
		for(int i = 0; i < diceArray.size(); i++){
			sum += diceArray.get(i).getSum();
		}
		return sum;
	}
	
	public int getLastDiceSum(){
		int lastSum = 0;
		for(int i = 0; i < diceArray.size(); i++){
			lastSum += diceArray.get(i).getLastSum();
		}
		return lastSum;
	}
	
	public int getSumOfDice(int index){
		if(index < diceArray.size()){
			return diceArray.get(index).getSum();
		}
		else{
			return 0;
		}
	}
	
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
	
	public boolean isSameHit(){
		boolean isSameHit = true;
		for(int i = 0; i < diceArray.size()-1; i++){
			if(diceArray.get(i) != diceArray.get(i+1)){
				isSameHit = false;
			}
		}
		return true;
	}
}