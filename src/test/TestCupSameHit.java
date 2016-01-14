package test;

import dices.Cup;

public class TestCupSameHit {

	public static void main(String[] args) {
		
		Cup cup = new Cup(2,6);
		Cup cup1 = new Cup(2,6);
		
		
		int testSize = 1000;
		int sameHit=0;
		
		for (int i = 0 ; i < testSize; i++){
			cup.rollDices();
			cup1.rollDices();
			if (cup.isSameHit()){
				sameHit++;
			}
		}
		
		System.out.println("Same result over " + testSize + " rolls: " +sameHit);
	}

}
