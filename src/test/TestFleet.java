package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GameController;
//import field.Street;
import field.Fleet;

public class TestFleet {
	private GameController gc;
		
	@Before 
	public void setUp ()throws Exception 
	{
	gc = new GameController();
	gc.getGUIController().isInDebugMode = true ;
	String name [] = {"Tobias","Frantsen"};
	gc.getPlayerController().createPlayers(name);
	
	}

	// tester om en spiller bliver sat til owner, hvis han vælger at købe feltet han lander på
		@Test
		public void test_SayYesToBuyFleet(){
			gc.getGUIController().debugModeReturnTypeBoolean=true;
			gc.getPlayerController().setCurrentPlayer(0);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(gc.getPlayerController().getCurrentPlayer(),((Fleet) gc.getFieldController().getFields()[5]).getOwner());			
		}
		// tester om owner er null efter man har sagt nej til at købe feltet
		@Test
		public void test_SayNoToBuyStreet(){
			gc.getGUIController().debugModeReturnTypeBoolean=false;
			gc.getPlayerController().setCurrentPlayer(0);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(null,((Fleet) gc.getFieldController().getFields()[5]).getOwner());

}
		@Test
		public void test_LandOnWith1Fleet () {
			((Fleet) gc.getFieldController().getFields()[5]).setOwner(gc.getPlayerController().getPlayer(0));
			gc.getPlayerController().setCurrentPlayer(1);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(30000-((Fleet) gc.getFieldController().getFields()[5]).getRent(gc),gc.getPlayerController().getCurrentPlayer().getBalance());
		}
		
		@Test
		public void test_LandOnWith2Fleet () {
			((Fleet) gc.getFieldController().getFields()[5]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[15]).setOwner(gc.getPlayerController().getPlayer(0));
			gc.getPlayerController().setCurrentPlayer(1);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(30000-((Fleet) gc.getFieldController().getFields()[5]).getRent(gc),gc.getPlayerController().getCurrentPlayer().getBalance());
		}
		
		@Test
		public void test_LandOnWith3Fleet () {
			((Fleet) gc.getFieldController().getFields()[5]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[15]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[25]).setOwner(gc.getPlayerController().getPlayer(0));
			gc.getPlayerController().setCurrentPlayer(1);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(30000-((Fleet) gc.getFieldController().getFields()[5]).getRent(gc),gc.getPlayerController().getCurrentPlayer().getBalance());
		}
		
		@Test
		public void test_LandOnWith4Fleet () {
			((Fleet) gc.getFieldController().getFields()[5]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[15]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[25]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[35]).setOwner(gc.getPlayerController().getPlayer(0));
			gc.getPlayerController().setCurrentPlayer(1);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(30000-((Fleet) gc.getFieldController().getFields()[5]).getRent(gc),gc.getPlayerController().getCurrentPlayer().getBalance());
		}
		
		@Test
		public void test_CantPayToOwner () {
			((Fleet) gc.getFieldController().getFields()[5]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[15]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[25]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[35]).setOwner(gc.getPlayerController().getPlayer(0));
			gc.getPlayerController().setCurrentPlayer(1);
			gc.getPlayerController().getCurrentPlayer().adjustBalance(-29000);
			assertEquals(false,((Fleet) gc.getFieldController().getFields()[5]).landOn(gc));
		}
		
		@Test 
		public void test_CantPayOwnerGetLastBalance (){
			gc.getGUIController().debugModeReturnTypeBoolean = false;
			((Fleet) gc.getFieldController().getFields()[5]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[15]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[25]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[35]).setOwner(gc.getPlayerController().getPlayer(0));
			gc.getPlayerController().setCurrentPlayer(1);
			gc.getPlayerController().getPlayer(1).adjustBalance(-29000);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(31000,gc.getPlayerController().getPlayer(0).getBalance());
			
		}
		@Test // tester at man ikke skal betale leje n�r man lander p� et felt som er pantsat
		public void test_PawnFleet(){
			((Fleet) gc.getFieldController().getFields()[5]).setOwner(gc.getPlayerController().getPlayer(0));
			((Fleet) gc.getFieldController().getFields()[5]).pawnProperty(gc, gc.getPlayerController().getPlayer(0));
			gc.getPlayerController().setCurrentPlayer(1);
			((Fleet) gc.getFieldController().getFields()[5]).landOn(gc);
			assertEquals(30000,gc.getPlayerController().getCurrentPlayer().getBalance());
		}
		
		@Test
		public void test_AllGetMethods () {
			assertEquals(gc.getFieldController().getFields()[5].getName(),"Helsingør-Helsingborg");
			assertEquals(gc.getFieldController().getFields()[5].getDescriptionText(),"Rederi");
			assertEquals(gc.getFieldController().getFields()[5].getSubText(),"Kr. 4000");
			assertEquals(gc.getFieldController().getFields()[5].getNumber(),5);
			
		}
}
