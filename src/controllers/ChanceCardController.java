package controllers;

import java.util.*;

import chancecards.CCTexts;
import chancecards.ChanceCard;
import chancecards.GetMoneyCC;
import chancecards.JailFreeCC;
import chancecards.MoveTo;
import chancecards.MoveToNearestFleetCC;
import chancecards.PayMoneyCC;
import chancecards.PayTaxRiseCC;
import chancecards.ScholarshipCC;

public class ChanceCardController {
	
	protected ChanceCard[] chanceCards;

	public ChanceCardController() {
		
		//card description  texts
		//CCTexts texts = new CCTexts(); STATIC methods so no need this
		
		//initialize pile
		chanceCards = new ChanceCard[11];
		
		chanceCards[0] = new JailFreeCC(CCTexts.jailFreeTxt);
		chanceCards[1] = new JailFreeCC(CCTexts.jailFreeTxt);
		chanceCards[2] = new PayMoneyCC(CCTexts.repCarTxt, 3000);
		chanceCards[3] = new PayMoneyCC(CCTexts.repCarTxt, 3000);
		chanceCards[4] = new PayTaxRiseCC(CCTexts.taxRaiseTxt);
		chanceCards[5] = new PayMoneyCC(CCTexts.dentistBillTxt, 2000);
		chanceCards[6] = new ScholarshipCC(CCTexts.repCarTxt, 3000);
		chanceCards[7] = new MoveTo(CCTexts.moveToStartTxt, 0);
		chanceCards[8] = new MoveTo(CCTexts.moveToStartTxt, 0);
		chanceCards[9] = new MoveToNearestFleetCC(CCTexts.moveToStartTxt);
		chanceCards[10] = new GetMoneyCC("test", 2500);
		
		//shuffle pile
		shuffleCards();
	}
	
	
	public void shuffleCards(){
		Random rnd = new Random();
		
		for(int i = 0; i<chanceCards.length;i++){
			int randomPos = rnd.nextInt(chanceCards.length-1);
			ChanceCard tempFrom = chanceCards[i];
			ChanceCard tempTo = chanceCards[randomPos];
			chanceCards[randomPos] = tempFrom;
			chanceCards[i] = tempTo;
			
			
		}
		
	}
	
	public boolean drawCard(GameController gc){
		
		
		//draw card from end of pile, validate object type, do action
		if(chanceCards[0] instanceof JailFreeCC)
			((JailFreeCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof PayMoneyCC)
			((PayMoneyCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof PayTaxRiseCC)
			((PayTaxRiseCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof ScholarshipCC)
			((ScholarshipCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof MoveTo)
			((MoveTo)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof MoveToNearestFleetCC)
			((MoveToNearestFleetCC)chanceCards[0]).drawCardAction(gc);
		//test
		if(chanceCards[10] instanceof GetMoneyCC)
			((GetMoneyCC)chanceCards[10]).drawCardAction(gc);
		//.......and the list goes on
		
		

		
		return true;
	}
	
	public String toString(){
		String myString = new String();
		for(int i = 0;i<chanceCards.length;i++){
			myString += "chanceCards["+i+"] = " + chanceCards[i].getCardDescription()+"\n";
			
		}
		return myString;
	}


	public ChanceCard[] getChanceCards() {
		return chanceCards;
	}


}
