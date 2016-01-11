package controllers;

import java.util.*;

import controllers.LanguageController;
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
		//LanguageController texts = new LanguageController(); STATIC methods so no need this
		
		//initialize pile
		chanceCards = new ChanceCard[47];
		
		chanceCards[0] = new JailFreeCC(LanguageController.ChanceCardController_jailFreeTxt);
		chanceCards[1] = new JailFreeCC(LanguageController.ChanceCardController_jailFreeTxt);
		chanceCards[2] = new PayMoneyCC(LanguageController.ChanceCardController_repCarTxt, 3000);
		chanceCards[3] = new PayMoneyCC(LanguageController.ChanceCardController_repCarTxt, 3000);
		chanceCards[4] = new PayTaxRaiseCC(LanguageController.ChanceCardController_taxRaiseTxt);
		chanceCards[5] = new PayMoneyCC(LanguageController.ChanceCardController_dentistBillTxt, 2000);
		chanceCards[6] = new ScholarshipCC(LanguageController.ChanceCardController_scholarshipTxt, 40000);
		chanceCards[7] = new MoveTo(LanguageController.ChanceCardController_moveToStartTxt, 0);
		chanceCards[8] = new MoveTo(LanguageController.ChanceCardController_moveToStartTxt, 0);
		chanceCards[9] = new MoveToNearestFleetCC(LanguageController.ChanceCardController_payOrBuyNearestFleetTxt);//NEED TO HANDLE THIS!!!
		chanceCards[10] = new MoveTo(LanguageController.ChanceCardController_moveToGronningenTxt,24);
		chanceCards[11] = new MoveTo(LanguageController.ChanceCardController_moveToFredAlleTxt,11);
		chanceCards[12] = new MoveTo(LanguageController.ChanceCardController_moveToBeachRoadTxt,19);
		chanceCards[13] = new MoveTo(LanguageController.ChanceCardController_moveToMolsTxt,15);
		chanceCards[14] = new MoveTo(LanguageController.ChanceCardController_moveToVimmelskaftTxt,32);
		chanceCards[15] = new MoveTo(LanguageController.ChanceCardController_moveToTownhallTxt,39);
		chanceCards[16] = new GetMoneyFromAllCC(LanguageController.ChanceCardController_familyPartyTxt,500);
		chanceCards[17] = new GetMoneyFromAllCC(LanguageController.ChanceCardController_birthdayTxt,200);
		chanceCards[18] = new GetMoneyCC(LanguageController.ChanceCardController_get200FromBank,200);
		chanceCards[19] = new GetMoneyCC(LanguageController.ChanceCardController_get1000FromBank1Txt,1000);
		chanceCards[20] = new GetMoneyCC(LanguageController.ChanceCardController_get1000FromBank2Txt,1000);
		chanceCards[21] = new GetMoneyCC(LanguageController.ChanceCardController_get1000FromBank2Txt,1000);
		chanceCards[22] = new GetMoneyCC(LanguageController.ChanceCardController_get1000FromBank3Txt,1000);
		chanceCards[23] = new GetMoneyCC(LanguageController.ChanceCardController_get1000FromBank3Txt,1000);
		chanceCards[24] = new GetMoneyCC(LanguageController.ChanceCardController_get1000FromBank3Txt,1000);
		chanceCards[25] = new GetMoneyCC(LanguageController.ChanceCardController_wonLotteryTxt,500);
		chanceCards[26] = new GetMoneyCC(LanguageController.ChanceCardController_wonLotteryTxt,500);
		chanceCards[27] = new GetMoneyCC(LanguageController.ChanceCardController_wonOddsGameTxt,1000);
		chanceCards[28] = new GetMoneyCC(LanguageController.ChanceCardController_get1000Txt,1000);
		chanceCards[29] = new GetMoneyCC(LanguageController.ChanceCardController_getTaxReturnTxt,3000);
		chanceCards[30] = new PayMoneyCC(LanguageController.ChanceCardController_newTiresTxt,1000);
		chanceCards[31] = new PayMoneyCC(LanguageController.ChanceCardController_fullStopTxt,1000);
		chanceCards[32] = new PayMoneyCC(LanguageController.ChanceCardController_parkingTicketTxt,200);
		chanceCards[33] = new PayMoneyCC(LanguageController.ChanceCardController_carInsuranceTxt,1000);
		chanceCards[34] = new PayMoneyCC(LanguageController.ChanceCardController_cigaretteCustomsTxt,200);
		chanceCards[35] = new PayMoneyCC(LanguageController.ChanceCardController_newTiresTxt,1000);
		chanceCards[36] = new PayMoneyCC(LanguageController.ChanceCardController_beerDeliveryTxt,200);
		chanceCards[37] = new PayMoneyCC(LanguageController.ChanceCardController_carPolish,300);
		chanceCards[38] = new PayOilRaiseCC(LanguageController.ChanceCardController_oilPricesUpTxt);
		chanceCards[39] = new MoveX(LanguageController.ChanceCardController_move3,3);
		chanceCards[40] = new MoveX(LanguageController.ChanceCardController_moveBack3,-3);
		chanceCards[41] = new MoveX(LanguageController.ChanceCardController_moveBack3,-3);
		chanceCards[42] = new MoveToNearestFleetCC(LanguageController.ChanceCardController_payOrBuyNearestFleetTxt);
		chanceCards[43] = new ChanceCard(LanguageController.ChanceCardController_blankTxt);
		chanceCards[44] = new ChanceCard(LanguageController.ChanceCardController_blankTxt);
		chanceCards[45] = new ChanceCard(LanguageController.ChanceCardController_blankTxt);
		chanceCards[46] = new ChanceCard(LanguageController.ChanceCardController_blankTxt);
		
		
		
		
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
		
		gc.getGUIController().askQuestion(chanceCards[0].getCardDescription(), "OK");
		
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
