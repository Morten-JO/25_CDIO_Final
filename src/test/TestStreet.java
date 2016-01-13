package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.FieldController;
import controllers.GameController;
import desktop_resources.GUI;
import field.Street;
import player.Player;

public class TestStreet {
	private Street vej ; 
	private GameController gc;
	
		
	// opretter en setUp som køre før hver test.
	@Before 
	public void setUp ()throws Exception 
	{
	gc = new GameController ();
	vej = new Street ( "Hvidovrevej","Ejer","Koster",4,1000,500,new int []{50,100,250,750,2250,4000,6000},1000,0);
	gc.getGUIController().isInDebugMode = true ;
	String name [] = {"Tobias","Frantsen"};
	gc.getPlayerController().createPlayers(name);	
	}
	
	
	// tester alle vores get metoder, på det objekt der er oprettet det hedder vej.
	@Test
	public void test_getMethods() {
		int [] testArray = new int[]{50,100,250,750,2250,4000,6000};
		assertEquals("Hvidovrevej",vej.getName());
		assertEquals("Ejer",vej.getDescriptionText());
		assertEquals("Koster: 1000",vej.getSubText());
		assertEquals(4,vej.getNumber());
		assertEquals(1000,vej.getPrice());
		assertEquals(500,vej.getPawnPrice());
		assertArrayEquals(testArray, vej.getRents());
		assertEquals(1000,vej.getBuildingPrice());
		assertEquals(0,vej.getStreetCategory());
		
	}
	// tester om en spiller bliver sat til owner, hvis han vælger at købe feltet han lander på
	@Test
	public void test_SayYesToBuyStreet(){
		gc.getGUIController().debugModeReturnTypeBoolean=true;
		gc.getPlayerController().setCurrentPlayer(0);
		vej.landOn(gc);
		assertEquals(gc.getPlayerController().getCurrentPlayer(),vej.getOwner());
				
	}
	// tester om owner er null efter man har sagt nej til at købe feltet
	@Test
	public void test_SayNoToBuyStreet(){
		gc.getGUIController().debugModeReturnTypeBoolean=false;
		gc.getPlayerController().setCurrentPlayer(0);
		vej.landOn(gc);
		assertEquals(null,vej.getOwner());
	}
		
	@Test
	// Tester om den tager den rigigte rente, hvis man kun ejer et felt i en bestemt kategori
	public void test_LandOnPayRentWithOneStreet(){
			
	((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		gc.getPlayerController().getPlayer(1).setPosition(1);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[1].landOn(gc);
		assertEquals(29950,gc.getPlayerController().getPlayer(1).getBalance());
				
	}
	
	@Test
	// tester om den tager den rigtige leje hvis man ejer alle felterne i en bestem kategori
	public void test_LandOnPayRentWithAllStreets(){
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		gc.getPlayerController().getPlayer(1).setPosition(1);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[1].landOn(gc);
		int rent = ((Street) gc.getFieldController().getFields()[3]).getRent(gc);
		assertEquals(30000-rent,gc.getPlayerController().getPlayer(1).getBalance());
				
	}
	
	@Test
	// tester om den tager den rigtige leje hvis man ejer 1 hus på et felt
	public void test_LandOnPayRentWithOneHouse(){
		
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[1]).setAmountOfHouses(1);
		gc.getPlayerController().getPlayer(1).setPosition(1);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[1].landOn(gc);
		int rent = ((Street) gc.getFieldController().getFields()[1]).getRent(gc);
		assertEquals(30000-rent,gc.getPlayerController().getPlayer(1).getBalance());
				
	}
	
	@Test
	// tester om den tager den rigtige leje hvis man ejer 2 hus på et felt
	public void test_LandOnPayRentWithTwoHouse(){
		
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[1]).setAmountOfHouses(2);
		gc.getPlayerController().getPlayer(1).setPosition(1);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[1].landOn(gc);
		int rent = ((Street) gc.getFieldController().getFields()[1]).getRent(gc);
		assertEquals(30000-rent,gc.getPlayerController().getPlayer(1).getBalance());
		
			}
	
	@Test
	// tester om den tager den rigtige leje hvis man ejer 3 hus på et felt
	public void test_LandOnPayRentWithThreeHouse(){
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[1]).setAmountOfHouses(3);
		gc.getPlayerController().getPlayer(1).setPosition(1);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[1].landOn(gc);
		int rent = ((Street) gc.getFieldController().getFields()[1]).getRent(gc);
		assertEquals(30000-rent,gc.getPlayerController().getPlayer(1).getBalance());
				
	}
		
	@Test
	// tester om den tager den rigtige leje hvis man ejer 4 hus på et felt
	public void test_LandOnPayRentWithFourHouse(){
		
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[1]).setAmountOfHouses(4);
		gc.getPlayerController().getPlayer(1).setPosition(1);
		gc.getPlayerController().setCurrentPlayer(1);
		gc.getFieldController().getFields()[1].landOn(gc);
		int rent = ((Street) gc.getFieldController().getFields()[1]).getRent(gc);
		assertEquals(30000-rent,gc.getPlayerController().getPlayer(1).getBalance());
		
		
	}
	@Test
	// tester om den tager den rigtige leje hvis man ejer 1 hotel på et felt
	public void test_LandOnPayRentWithOneHotel(){
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street) gc.getFieldController().getFields()[1]).setAmountOfHotels(1);
		gc.getPlayerController().getPlayer(1).setPosition(1);
		gc.getPlayerController().setCurrentPlayer(1);
		((Street)gc.getFieldController().getFields()[1]).landOn(gc);
		int rent = ((Street) gc.getFieldController().getFields()[1]).getRent(gc);
		
		assertEquals(30000-rent,gc.getPlayerController().getPlayer(1).getBalance());
		
		
	}
	
	@Test 
	// ser om Landon sender en false tilbage, hvis spilleren ikke kan betale, så er spilleren nemlig gået bankerot
	public void test_CantPay(){
		((Street) gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		gc.getPlayerController().getPlayer(1).adjustBalance(-29999);
		gc.getPlayerController().setCurrentPlayer(1);
		assertEquals(false,((Street)gc.getFieldController().getFields()[1]).landOn(gc));
	}
	
	@Test
	// tester om man kan købe 2 huse på samme street, hvis man ikke har nogle andre huse på samme kategori
	public void test_Buy2HouseOnSameStreet() {
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		assertEquals(1,((Street)gc.getFieldController().getFields()[1]).getAmountOfHouses());
		
	}
	
	@Test 
	// tester metoden buyHouse, som tester om man købe et hus, hvis man ejer alle streets i en kategori
	public void test_BuyHouse () {
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		assertEquals(1,((Street)gc.getFieldController().getFields()[1]).getAmountOfHouses());
	}
	
	@Test 
	// tester om man kan købe 2 huse på en street.
	public void test_Buy2House () {
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		assertEquals(2,((Street)gc.getFieldController().getFields()[1]).getAmountOfHouses());
		
	}
	@Test
	// tester om man kan købe 3 huse på en street.
	public void test_Buy3House () {
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		assertEquals(3,((Street)gc.getFieldController().getFields()[1]).getAmountOfHouses());
	}
	
	@Test
	// tester om man kan købe 4 huse på en street.
	public void test_Buy4House () {
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		assertEquals(4,((Street)gc.getFieldController().getFields()[1]).getAmountOfHouses());
	}
	@Test
	// tester om når man køber det 5 hus, om så man får automatisk et hotel 
	public void test_Buy5HouseGet1Hotel () {
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		assertEquals(1,((Street)gc.getFieldController().getFields()[1]).getAmountOfHotels());
	}
	
	
	@Test
	// tester metoden sellAllBuildingsinCat, som skal slette alle huse på ens streets i sin kategori
	public void test_sellAllBuildingsinCat () {
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[3]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		((Street)gc.getFieldController().getFields()[1]).sellAllBuildingsinCat(((Street)gc.getFieldController().getFields()[1]).getStreetCategory(),gc);
		assertEquals(0,((Street)gc.getFieldController().getFields()[1]).getAmountOfHouses());
		
		
	}
	@Test
	// ser om man kan købe et hus, selvom en af streets er pantsatte
	public void test_BuyHouseIfOneStreetIsPawn(){
		gc.getGUIController().debugModeReturnTypeBoolean = true;
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[3]).pawnProperty(gc, gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).buyBuilding(gc);
		assertEquals(0,((Street)gc.getFieldController().getFields()[1]).getAmountOfHouses());
	}
	
	@Test
	// tester om den sætter en spiller owner til null efter metoden RemoveAllOwnership er kaldt.
	public void test_RemoveAllOwnership(){
		((Street)gc.getFieldController().getFields()[1]).setOwner(gc.getPlayerController().getPlayer(0));
		((Street)gc.getFieldController().getFields()[1]).removeAllOwnership();
		assertEquals(null,((Street)gc.getFieldController().getFields()[1]).getOwner());
	}
}
