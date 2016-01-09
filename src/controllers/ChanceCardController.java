package controllers;

import java.util.*;

import chancecards.CCTexts;
import chancecards.ChanceCard;
import chancecards.GetMoneyCC;
import chancecards.GetMoneyFromAllCC;
import chancecards.JailFreeCC;
import chancecards.MoveTo;
import chancecards.MoveToNearestFleetCC;
import chancecards.MoveX;
import chancecards.PayMoneyCC;
import chancecards.PayTaxRaiseCC;
import chancecards.PlayersGetMoneyCC;
import chancecards.PayOilRaiseCC;
import chancecards.ScholarshipCC;

public class ChanceCardController {
	
	private ChanceCard[] chanceCards;
	private int cardsDrawn;

	public ChanceCardController() {
		
		//card description  texts
		//CCTexts texts = new CCTexts(); STATIC methods so no need this
		
		//initialize pile
		chanceCards = new ChanceCard[47];
		
		chanceCards[0] = new JailFreeCC(CCTexts.jailFreeTxt);
		chanceCards[1] = new JailFreeCC(CCTexts.jailFreeTxt);
		chanceCards[2] = new PayMoneyCC(CCTexts.repCarTxt, 3000);
		chanceCards[3] = new PayMoneyCC(CCTexts.repCarTxt, 3000);
		chanceCards[4] = new PayTaxRaiseCC(CCTexts.taxRaiseTxt);
		chanceCards[5] = new PayMoneyCC(CCTexts.dentistBillTxt, 2000);
		chanceCards[6] = new ScholarshipCC(CCTexts.scholarshipTxt, 40000);
		chanceCards[7] = new MoveTo(CCTexts.moveToStartTxt, 0);
		chanceCards[8] = new MoveTo(CCTexts.moveToStartTxt, 0);
		chanceCards[9] = new MoveToNearestFleetCC(CCTexts.payOrBuyNearestFleetTxt);//NEED TO HANDLE THIS!!!
		chanceCards[10] = new MoveTo(CCTexts.moveToGronningenTxt,24);
		chanceCards[11] = new MoveTo(CCTexts.moveToFredAlleTxt,11);
		chanceCards[12] = new MoveTo(CCTexts.moveToBeachRoadTxt,19);
		chanceCards[13] = new MoveTo(CCTexts.moveToMolsTxt,15);
		chanceCards[14] = new MoveTo(CCTexts.moveToVimmelskaftTxt,32);
		chanceCards[15] = new MoveTo(CCTexts.moveToTownhallTxt,39);
		chanceCards[16] = new GetMoneyFromAllCC(CCTexts.familyPartyTxt,500);
		chanceCards[17] = new GetMoneyFromAllCC(CCTexts.birthdayTxt,200);
		chanceCards[18] = new GetMoneyCC(CCTexts.get200FromBank,200);
		chanceCards[19] = new GetMoneyCC(CCTexts.get1000FromBank1Txt,1000);
		chanceCards[20] = new GetMoneyCC(CCTexts.get1000FromBank2Txt,1000);
		chanceCards[21] = new GetMoneyCC(CCTexts.get1000FromBank2Txt,1000);
		chanceCards[22] = new GetMoneyCC(CCTexts.get1000FromBank3Txt,1000);
		chanceCards[23] = new GetMoneyCC(CCTexts.get1000FromBank3Txt,1000);
		chanceCards[24] = new GetMoneyCC(CCTexts.get1000FromBank3Txt,1000);
		chanceCards[25] = new GetMoneyCC(CCTexts.wonLotteryTxt,500);
		chanceCards[26] = new GetMoneyCC(CCTexts.wonLotteryTxt,500);
		chanceCards[27] = new GetMoneyCC(CCTexts.wonOddsGameTxt,1000);
		chanceCards[28] = new GetMoneyCC(CCTexts.get1000Txt,1000);
		chanceCards[29] = new GetMoneyCC(CCTexts.getTaxReturnTxt,3000);
		chanceCards[30] = new PayMoneyCC(CCTexts.newTiresTxt,1000);
		chanceCards[31] = new PayMoneyCC(CCTexts.fullStopTxt,1000);
		chanceCards[32] = new PayMoneyCC(CCTexts.parkingTicketTxt,200);
		chanceCards[33] = new PayMoneyCC(CCTexts.carInsuranceTxt,1000);
		chanceCards[34] = new PayMoneyCC(CCTexts.cigaretteCustomsTxt,200);
		chanceCards[35] = new PayMoneyCC(CCTexts.newTiresTxt,1000);
		chanceCards[36] = new PayMoneyCC(CCTexts.beerDeliveryTxt,200);
		chanceCards[37] = new PayMoneyCC(CCTexts.carPolish,300);
		chanceCards[38] = new PayOilRaiseCC(CCTexts.oilPricesUpTxt);
		chanceCards[39] = new MoveX(CCTexts.move3,3);
		chanceCards[40] = new MoveX(CCTexts.moveBack3,-3);
		chanceCards[41] = new MoveX(CCTexts.moveBack3,-3);
		chanceCards[42] = new MoveToNearestFleetCC(CCTexts.payOrBuyNearestFleetTxt);
		chanceCards[43] = new ChanceCard(CCTexts.blankTxt);
		chanceCards[44] = new ChanceCard(CCTexts.blankTxt);
		chanceCards[45] = new ChanceCard(CCTexts.blankTxt);
		chanceCards[46] = new ChanceCard(CCTexts.blankTxt);
		
		
		
		
		//shuffle pile
		shuffleCards();
		
		cardsDrawn = 0;
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
		//shuffle pile after all cards have been drawn once
		if(cardsDrawn>=chanceCards.length){
			shuffleCards();
			cardsDrawn = 0;
		}
		System.out.println(chanceCards[0].getCardDescription());
		
		boolean result = false;
		//draw card from end of pile, validate object type, do action
		// could use SWITCH - very low priority
		if(chanceCards[0] instanceof JailFreeCC)
			result = ((JailFreeCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof PayMoneyCC)
			result = ((PayMoneyCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof PayTaxRaiseCC)
			result = ((PayTaxRaiseCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof PayOilRaiseCC)
			result = ((PayOilRaiseCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof ScholarshipCC)
			result = ((ScholarshipCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof MoveTo)
			result = ((MoveTo)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof MoveToNearestFleetCC)
			result = ((MoveToNearestFleetCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof GetMoneyCC)
			result = ((GetMoneyCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof GetMoneyFromAllCC)
			result = ((GetMoneyFromAllCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof MoveX)
			result = ((MoveX)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof PlayersGetMoneyCC)
			result = ((PlayersGetMoneyCC)chanceCards[0]).drawCardAction(gc);
		else if(chanceCards[0] instanceof ChanceCard)
			result = true;//this is blank CC. no action required
		else{ 
			try {
				throw new Exception("Couldn't identify chance card type");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		moveCardFromTopToBottom();
		
		return result;
	}
	
	public void moveCardFromTopToBottom(){
		ChanceCard cardToMove = chanceCards[0];
		
		for (int i = 0; i < chanceCards.length-1; i++) {
			chanceCards[i] = chanceCards[i+1];
		}
		
		chanceCards[chanceCards.length-1] = cardToMove;
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
