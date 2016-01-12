package test;

import dices.Dice;

public class TestDiceProcent {
	
	public static void main(String[] args) 
	{
//		kalder dice
		Dice dice = new Dice(6);
		int et = 0;
		int to = 0;
		int tre = 0;
		int fire = 0;
		int fem = 0;
		int seks = 0;
		
		
		double n = 100000;
		for(int i = 0; i < n; i++)
		{
			dice.hitDice();
			if(dice.getSum() == 1) et++;
			if(dice.getSum() == 2) to++;
			if(dice.getSum() == 3) tre++;
			if(dice.getSum() == 4) fire++;
			if(dice.getSum() == 5) fem++;
			if(dice.getSum() == 6) seks++;
		}
		
		System.out.println("One: " + et);
		System.out.println("Two: " + to);
		System.out.println("Tree: " + tre);
		System.out.println("Four: " + fire);
		System.out.println("Five: " + fem);
		System.out.println("Six: " + seks);

	}

}
