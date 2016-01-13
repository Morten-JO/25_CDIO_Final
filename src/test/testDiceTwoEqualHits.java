package test;

import dices.Dice;

public class testDiceTwoEqualHits {
	
	public static void main(String[] args)
	{
		Dice dice = new Dice(6);
		Dice dice1= new Dice(6);
		
		int one = 0;
		int two = 0;
		int tree = 0;
		int four = 0;
		int five = 0;
		int six = 0;
		
		int TwoEqual = 0;
		
		double hit = 100;
			for(int i = 0; i < TwoEqual; i++)
			{
				dice.hitDice();
				dice1.hitDice();
				
				if(dice.getSum() == dice1.getSum())
				{
					TwoEqual++;
				}
			}
			
			System.out.println("Two equal hits: " + TwoEqual);
		
		

		
		
	}

}
