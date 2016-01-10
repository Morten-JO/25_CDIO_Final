package controllers;

import java.awt.Point;
import java.util.ArrayList;

import field.*;
import player.Player;

public class FieldController {

	private static Field[] gameFields = new Field[40];
	
	
	public FieldController(){
		addFields();
	}
	
	private void addFields(){
		gameFields[0] = new Start("Start", "Passer og modtag kr. 4000", "", 0);
		gameFields[1] = new Street("R�dovrevej", "blue", "koster 1200", 1, 1200, 600, new int[] {50, 100, 250, 750, 2250, 4000, 6000},1000, 0);
		gameFields[2] = new Chance("Pr�v Lykken", "Tr�k et kort", "", 2);
		gameFields[3] = new Street("Hvidovrevej", "blue", "koster 1200", 3, 1200, 600, new int[] {50, 100, 250, 750, 2250, 4000, 6000},1000, 0);
		gameFields[4] = new TaxPercent("Statsskat", "Betal kr. 4000 eller 10% af din formue", "", 4, 4000, 10);
		gameFields[5] = new Fleet("Helsing�r-Helsingborg", "Rederi", "Koster kr. 4000", 5, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[6] = new Street("Roskildevej", "orange", "koster 2000", 6, 2000, 1000, new int [] {100, 200, 600,1800,5400,8000,11000}, 1000, 1);
		gameFields[7] = new Chance("Pr�v Lykken", "Tr�k et kort", "", 7);
		gameFields[8] = new Street("Valby Langgade", "orange", "koster 2000", 8, 2000, 1000, new int [] {100, 200, 600,1800,5400,8000,11000}, 1000, 1);
		gameFields[9] = new Street("All�gade", "orange", "koster 2400", 9, 2400, 1200, new int [] {150, 300, 800,2000,6000,9000,12000}, 1000, 1);
		gameFields[10] = new VisitJail("P� bes�g i f�ngslet", "Du har helle her", "", 10);
		gameFields[11] = new Street("Frederiksberg All�", "green", "koster 2800", 11, 2800, 1400, new int [] {200, 400, 1000,3000,9000,12500,15000}, 2000, 2);
		gameFields[12] = new Brewery("Squash", "Tag en Squash", "", 12, 3000, 1500,  new int[] {100,200});
		gameFields[13] = new Street("B�lowsvej", "green", "koster 2800", 13, 2800, 1400, new int [] {200, 400, 1000,3000,9000,12500,15000}, 2000, 2);
		gameFields[14] = new Street("Gl. Kongevej", "green", "koster 3200", 14, 3200, 1600, new int [] {250, 500, 1250, 3750, 10000, 14000, 18000}, 2000, 2);
		gameFields[15] = new Fleet("Mols-Linien", "Rederi", "Koster kr. 4000", 15, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[16] = new Street("Bernstorffsvej", "gray", "koster 3600", 16, 3600, 1800, new int [] {300, 600, 1400, 4000, 11000, 15000, 18000}, 2000, 3);
		gameFields[17] = new Chance("Pr�v Lykken", "Tr�k et kort", "", 17);
		gameFields[18] = new Street("Hellerupvej", "gray", "koster 3600", 18, 3600, 1800, new int [] {300, 600, 1400, 4000, 11000, 15000, 18000}, 2000, 3);
		gameFields[19] = new Street("Strandvejen", "gray", "koster 4000", 19, 4000, 2000, new int [] {350, 700, 1600, 4400, 12000, 16000, 20000}, 2000, 3);
		gameFields[20] = new Bonus("Bonusfelt", "Modtag kr. 5000", "", 20, 5000);
		gameFields[21] = new Street("Trianglen", "red", "koster 4400", 21, 4400, 2200, new int [] {350, 700, 1800, 5000, 14000, 17500, 21000}, 3000, 4);
		gameFields[22] = new Chance("Pr�v Lykken", "Tr�k et kort", "", 22);
		gameFields[23] = new Street("�sterbrogade", "red", "koster 4400", 23, 4400, 2200, new int [] {350, 700, 1800, 5000, 14000, 17500, 21000}, 3000, 4);
		gameFields[24] = new Street("Gr�nningen", "red", "koster 4800", 24, 4800, 2400, new int [] {400, 800, 2000, 6000, 15000, 18500, 22000}, 3000, 4);
		gameFields[25] = new Fleet("Gedser-Rostock", "Rederi", "Koster kr. 4000", 25, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[26] = new Street("Bredgade", "white", "koster 5200", 26, 5200, 2600, new int [] {450, 900, 2200, 6600, 16000, 19500, 23000}, 3000, 5);
		gameFields[27] = new Street("Kgs. Nytorv", "white", "koster 5200", 27, 5200, 2600, new int [] {450, 900, 2200, 6600, 16000, 19500, 23000}, 3000, 5);
		gameFields[28] = new Brewery("Coca-Cola", "Tag en Cola", "", 28, 3000, 1500,  new int[] {100,200});
		gameFields[29] = new Street("�stergade", "white", "koster 5600", 29, 5600, 2800, new int [] {500, 1000, 2400, 7200, 17000, 20500, 24000}, 3000, 5);
		gameFields[30] = new Jail("G� i F�ngsel", "Du ryger i f�ngsel", "", 30);
		gameFields[31] = new Street("Amagertorv", "yellow", "koster 6000", 31, 6000, 3000, new int [] {550, 1100, 2600, 7800, 18000, 22000, 25000},4000, 6);
		gameFields[32] = new Street("Vimmelskaftet", "yellow", "koster 6000", 32, 6000, 3000, new int [] {550, 1100, 2600, 7800, 18000, 22000, 25000},4000, 6);
		gameFields[33] = new Chance("Pr�v Lykken", "Tr�k et kort", "", 33);
		gameFields[34] = new Street("Nygade", "yellow", "koster 6400", 34, 6400, 3200, new int [] {600, 1200, 3000, 9000, 20000, 24000, 28000},4000, 6);
		gameFields[35] = new Fleet("R�dby-Puttgarden", "Rederi", "Koster 4000", 35, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[36] = new Chance("Pr�v Lykken", "Tr�k et kort", "", 36);
		gameFields[37] = new Street("Frederiksberggade", "pink", "koster 7000", 37, 7000, 3500, new int [] {700, 1400, 3500, 10000, 22000, 26000, 30000},4000, 7);
		gameFields[38] = new Tax("Statsskat", "Betal kr. 2000", "", 38, 2000);
		gameFields[39] = new Street("R�dhuspladsen", "pink", "koster 8000", 39, 8000, 4000, new int [] {1000, 2000, 4000, 12000, 28000, 34000, 40000},4000, 7);
		}
	
	
	
	/**
	 *Get amount of houses and hotels owned by a player. Used for special oilraise ChanceCard
	 * @param player - current player by turn
	 * @return point obj containing (houses, hotels)
	 */
	public static Point getBuildingsOwnedByPlayer(Player player){
		int houses = 0, hotels = 0;
		Player owner;
		for(int i = 0; i<gameFields.length;i++){
			//check if field is Street type
			if(gameFields[i] instanceof Street){
				owner = ((Street)gameFields[i]).getOwner();
				if(owner == player){
					//check if street has a hotel (houses = 5)
					if(((Street)gameFields[i]).getamountOfHouses()==5){
						hotels++;
						houses+= 4;
					}else{
						houses+= ((Street)gameFields[i]).getamountOfHouses();
					}
				
				}
			}
		}
		
		Point point = new Point(houses, hotels);
		return point;
	}
	
	public Field[] getAllOwnedProperties(Player player){
		ArrayList<Field> listOfProperties = new ArrayList<Field>();
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(((Ownable)gameFields[i]).getOwner().equals(player)){
					listOfProperties.add(gameFields[i]);
				}
			}
		}
		Field[] array = new Field[listOfProperties.size()];
		array = listOfProperties.toArray(array);
		return array;
	}
	
	
	public int getOwnerShipOfFleets(Player p) {
		int count = 0;
		for(int i = 0 ; i < gameFields.length; i++){
			if (gameFields[i] instanceof Fleet ){
				if(((Ownable)gameFields[i]).getOwner() == p){
					count++;
				}
			}
		}
		return count;
	}
	public int getOwnershipOfStreetsInCat(Player p, int cat) {
		int count = 0;
		for(int i = 0 ; i < gameFields.length; i++){
			if ( gameFields[i] instanceof Street){
			if (((Street)gameFields[i]).getStreetCategory() == cat ){
				if(((Ownable)gameFields[i]).getOwner() == p){
					count++;
				}
			}
		}
		}
		return count;
	}
	public int getOwnerShipOfBreweries(Player p) {
		int count = 0;
		for(int i = 0 ; i < gameFields.length; i++){
			if (gameFields[i] instanceof Brewery){
				if(((Ownable)gameFields[i]).getOwner() == p){
					count++;
				}
			}
		}
		return count;
	}
	
		

	public Field[] getFields() {
		return gameFields;
	}

	public int getPropertyValue(Player player){
		int amount = 0;
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(((Ownable)gameFields[i]).getOwner() == player){
					if(gameFields[i] instanceof Street){
						amount += ((Street)gameFields[i]).getamountOfHouses() * ((Street)gameFields[i]).getBuildingPrice();
						amount += ((Street)gameFields[i]).getamountOfHotels() * ((Street)gameFields[i]).getBuildingPrice();
					}
					amount += ((Ownable)gameFields[i]).getPrice();
				}
			}
		}
		return amount;
	}
	
	public boolean ownsEntireStreet(Player player){
		boolean ownsEntireStreet = false;
		int[] streetIndexes = new int[8];
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				streetIndexes[((Street)gameFields[i]).getStreetCategory()]++;
			}
		}
		int[] ownedIndexes = new int[8];
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(((Street)gameFields[i]).getOwner() == player){
					ownedIndexes[((Street)gameFields[i]).getStreetCategory()]++;
				}
			}
		}
		for(int i = 0; i < ownedIndexes.length; i++){
			if(streetIndexes[i] == ownedIndexes[i]){
				ownsEntireStreet = true;
				break;
			}
		}
		return ownsEntireStreet;
	}
	
	public Field[] getOwnedFullStreets(Player player, GameController gameController){
		int[] streetIndexes = new int[8];
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				streetIndexes[((Street)gameFields[i]).getStreetCategory()]++;
			}
		}
		int[] ownedIndexes = new int[8];
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(((Street)gameFields[i]).getOwner() == player){
					ownedIndexes[((Street)gameFields[i]).getStreetCategory()]++;
				}
			}
		}
		ArrayList<Field> listOfBuildings = new ArrayList<Field>();
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(ownedIndexes[((Street)gameFields[i]).getStreetCategory()] == streetIndexes[((Street)gameFields[i]).getStreetCategory()]){
					listOfBuildings.add(gameFields[i]);
				}
			}
		}
		for(int i = 0; i < listOfBuildings.size(); i++){
			if(((Street)listOfBuildings.get(i)).getamountOfHouses() <= ((Street)listOfBuildings.get(i)).getHousesInSection(((Street)listOfBuildings.get(i)).getStreetCategory(), gameController)/((Street)listOfBuildings.get(i)).getAmountOfStreetsInCategory(((Street)listOfBuildings.get(i)).getStreetCategory(),  gameController)){
				listOfBuildings.remove(i);
				i--;
			}
		}
		Field[] fields = new Field[listOfBuildings.size()];
		fields = listOfBuildings.toArray(fields);
		return fields;
	}
}

