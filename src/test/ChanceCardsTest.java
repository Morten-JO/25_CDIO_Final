package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chancecards.GetMoneyCC;
import chancecards.GetMoneyFromAllCC;
import chancecards.MoveTo;
import chancecards.MoveToNearestFleetCC;
import chancecards.MoveX;
import chancecards.PayMoneyCC;
import chancecards.ScholarshipCC;
import controllers.FieldController;
import controllers.GameController;
import controllers.Language;
import controllers.PlayerController;
import player.Player;

public class ChanceCardsTest {
	private GameController gc;
	private PlayerController pc;
	private FieldController fc;

	@Before
	public void setUp() throws Exception {
		gc = new GameController();
		pc = gc.getPlayerController();
		fc = gc.getFieldController();
		
		//set guictrl in debug mode
		gc.getGUIController().isInDebugMode=true;
		
		//players are initialized (30k balance)
		pc.createPlayers(new String[]{"Arne", "Børge", "Cornelius"});
		
		//set currentPlayer to index 0
		pc.setCurrentPlayer(0);
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	
	@Test
	public void testPayMoneyCC() {
		//test pay 3000
		PayMoneyCC payMoneyCC = new PayMoneyCC(Language.ChanceCardController_repCarTxt, 3000);
		payMoneyCC.drawCardAction(gc);
		
		assertEquals(27000,pc.getCurrentPlayer().getBalance());
	}
	
	@Test
	public void testGetMoneyCC(){
		GetMoneyCC getMoneyCC = new GetMoneyCC(Language.ChanceCardController_get1000FromBank1Txt,1000);
		getMoneyCC.drawCardAction(gc);
		
		assertEquals(31000, pc.getCurrentPlayer().getBalance());	
	}
	
	//test 1 player get 500 from all other players
	@Test
	public void testGetMoneyFromAllCC(){
		GetMoneyFromAllCC getMoneyFromAllCC = new GetMoneyFromAllCC(Language.ChanceCardController_familyPartyTxt,500);
		getMoneyFromAllCC.drawCardAction(gc);
		
		ArrayList<Player> playerList = gc.getPlayerController().getPlayerList();
		
		int playersCount = playerList.size();
		int expectedReceiverBal = 30000+500*(playersCount-1);
		
		//check that receiver has the right balance
		assertEquals(expectedReceiverBal,pc.getCurrentPlayer().getBalance());
		
		for(Player player : playerList){
			if(player != pc.getCurrentPlayer()){
				assertEquals(29500, player.getBalance());
			}
			
		}
	}
	
	@Test
	public void testMoveToCC(){
		//moving current player to field 19
		MoveTo moveTo = new MoveTo(Language.ChanceCardController_moveToBeachRoadTxt,19);
		moveTo.drawCardAction(gc);
		
		//check that current player position is now 19. Balances adjusted from landOn have to be checked from Fields
		int pos = pc.getCurrentPlayer().getPosition();
		assertEquals(19, pos);
		
		
		
	}
	
	@Test
	public void testMoveToNearestFleetCC(){
		MoveToNearestFleetCC moveToNearestFleetCC = new MoveToNearestFleetCC(Language.ChanceCardController_payOrBuyNearestFleetTxt);
		
		//NOTE: fleets are located at fieldIDs 5, 15, 25, 35
		//set current player position to 3
		pc.getCurrentPlayer().setPosition(3);
		
		moveToNearestFleetCC.drawCardAction(gc);
		assertEquals(5, pc.getCurrentPlayer().getPosition());
		
		moveToNearestFleetCC.drawCardAction(gc);
		assertEquals(15, pc.getCurrentPlayer().getPosition());
		
		moveToNearestFleetCC.drawCardAction(gc);
		assertEquals(25, pc.getCurrentPlayer().getPosition());
		
		moveToNearestFleetCC.drawCardAction(gc);
		assertEquals(35, pc.getCurrentPlayer().getPosition());
		
		moveToNearestFleetCC.drawCardAction(gc);
		assertEquals(5, pc.getCurrentPlayer().getPosition());
		
		
	}
	
	@Test
	public void testMoveX(){
		MoveX moveXplus3 = new MoveX(Language.ChanceCardController_moveBack3,3);
		MoveX moveXminus3 = new MoveX(Language.ChanceCardController_moveBack3,-3);
		
		//focus on testing boundary 0/39 (fieldarray size is currently 39)*
		//we deliberately avoid landing on IDs of chance fields, it might cause our test to fail, and it irrelevant for the test itself
		//player balances and landOn methods will be check in Field Test
		
		//set pos to 1
		pc.getCurrentPlayer().setPosition(1);
		
		//do -3 two times
		moveXminus3.drawCardAction(gc);
		assertEquals(38, pc.getCurrentPlayer().getPosition());
		moveXminus3.drawCardAction(gc);
		assertEquals(35, pc.getCurrentPlayer().getPosition());
		
		//set pos to 39
		pc.getCurrentPlayer().setPosition(38);
		
		//do +3 two times
		moveXplus3.drawCardAction(gc);
		assertEquals(1, pc.getCurrentPlayer().getPosition());
		moveXplus3.drawCardAction(gc);
		assertEquals(4, pc.getCurrentPlayer().getPosition());
				
		
	}
	
	@Test//no such card 
	public void testPlayersGetmoneyCC(){
		
	}
	
	@Test
	public void testPayOilRaise(){
		//can we set houses just like that?
	
		
	}
	
	@Test
	public void testPayTaxRaise(){
		//can we set houses just like that?
	}
	
	@Test
	public void testScholarship(){
		ScholarshipCC scholarshipCC = new ScholarshipCC(Language.ChanceCardController_scholarshipTxt, 40000);
		
		//at 30k - nothing happens
		scholarshipCC.drawCardAction(gc);
		assertEquals(30000, pc.getCurrentPlayer().getBalance());
		
		//redraw 15k-> at 15k - nothing happens
		pc.getCurrentPlayer().adjustBalance(-15000);
		assertEquals(15000, pc.getCurrentPlayer().getBalance());
		scholarshipCC.drawCardAction(gc);
		assertEquals(15000, pc.getCurrentPlayer().getBalance());
		
		//redraw 1 to go under 15000 balance. Should now receive scholarshipp of 40 and end at 54999
		pc.getCurrentPlayer().adjustBalance(-1);
		assertEquals(14999, pc.getCurrentPlayer().getBalance());
		scholarshipCC.drawCardAction(gc);
		assertEquals(54999, pc.getCurrentPlayer().getBalance());
		
		
	}
	
	

}
