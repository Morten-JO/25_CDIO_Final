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
		gameFields[0] = new Start(Language.FieldController_StartField, "Passér og modtag kr. 4000", "", 0);
		gameFields[1] = new Street(Language.FieldController_Roedovrevej, Language.FieldController_Owner, Language.FieldController_FieldPrice, 1, 1200, 600, new int[] {50, 100, 250, 750, 2250, 4000, 6000},1000, 0);
		gameFields[2] = new Chance(Language.FieldController_Chance, "Træk et kort", "", 2);
		gameFields[3] = new Street(Language.FieldController_Hvidovrevej, Language.FieldController_Owner, Language.FieldController_FieldPrice, 3, 1200, 600, new int[] {50, 100, 250, 750, 2250, 4000, 6000},1000, 0);
		gameFields[4] = new TaxPercent(Language.FieldController_Taxfield, Language.FieldController_PayTax, "", 4, 4000, 10);
		gameFields[5] = new Fleet(Language.FieldController_HelsingoerHelsingborg, Language.FieldController_Fleet, Language.FieldController_FieldPrice, 5, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[6] = new Street(Language.FieldController_Roskildevej, Language.FieldController_Owner, Language.FieldController_FieldPrice, 6, 2000, 1000, new int [] {100, 200, 600,1800,5400,8000,11000}, 1000, 1);
		gameFields[7] = new Chance(Language.FieldController_Chance, Language.FieldController_DrawChanceCard, "", 7);
		gameFields[8] = new Street(Language.FieldController_ValbyLanggade, Language.FieldController_Owner, Language.FieldController_FieldPrice, 8, 2000, 1000, new int [] {100, 200, 600,1800,5400,8000,11000}, 1000, 1);
		gameFields[9] = new Street(Language.FieldController_Allegade, Language.FieldController_Owner, Language.FieldController_FieldPrice, 9, 2400, 1200, new int [] {150, 300, 800,2000,6000,9000,12000}, 1000, 1);
		gameFields[10] = new VisitJail(Language.FieldController_JailVisit, Language.FieldController_Sanctuary, "", 10);
		gameFields[11] = new Street(Language.FieldController_FrederiksbergAlle, Language.FieldController_Owner, Language.FieldController_FieldPrice, 11, 2800, 1400, new int [] {200, 400, 1000,3000,9000,12500,15000}, 2000, 2);
		gameFields[12] = new Brewery(Language.FieldController_Tuborg,Language.FieldController_HaveADrink , Language.FieldController_FieldPrice, 12, 3000, 1500,  new int[] {100,200});
		gameFields[13] = new Street(Language.FieldController_Bylowsvej, Language.FieldController_Owner, Language.FieldController_FieldPrice, 13, 2800, 1400, new int [] {200, 400, 1000,3000,9000,12500,15000}, 2000, 2);
		gameFields[14] = new Street(Language.FieldController_GlKongevej, Language.FieldController_Owner, Language.FieldController_FieldPrice, 14, 3200, 1600, new int [] {250, 500, 1250, 3750, 10000, 14000, 18000}, 2000, 2);
		gameFields[15] = new Fleet(Language.FieldController_MolsLinien, Language.FieldController_Fleet, Language.FieldController_FieldPrice, 15, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[16] = new Street(Language.FieldController_Bernstorffsvej, Language.FieldController_Owner, Language.FieldController_FieldPrice, 16, 3600, 1800, new int [] {300, 600, 1400, 4000, 11000, 15000, 18000}, 2000, 3);
		gameFields[17] = new Chance(Language.FieldController_Chance, Language.FieldController_DrawChanceCard, "", 17);
		gameFields[18] = new Street(Language.FieldController_Hellerupvej, Language.FieldController_Owner, Language.FieldController_FieldPrice, 18, 3600, 1800, new int [] {300, 600, 1400, 4000, 11000, 15000, 18000}, 2000, 3);
		gameFields[19] = new Street(Language.FieldController_Strandvejen, Language.FieldController_Owner, Language.FieldController_FieldPrice, 19, 4000, 2000, new int [] {350, 700, 1600, 4400, 12000, 16000, 20000}, 2000, 3);
		gameFields[20] = new Bonus(Language.FieldController_BonusField, Language.FieldController_GetBonus, "", 20, 5000);
		gameFields[21] = new Street(Language.FieldController_Trianglen, Language.FieldController_Owner, Language.FieldController_FieldPrice, 21, 4400, 2200, new int [] {350, 700, 1800, 5000, 14000, 17500, 21000}, 3000, 4);
		gameFields[22] = new Chance(Language.FieldController_Chance, Language.FieldController_DrawChanceCard, "", 22);
		gameFields[23] = new Street(Language.FieldController_Oesterbrogade, Language.FieldController_Owner, Language.FieldController_FieldPrice, 23, 4400, 2200, new int [] {350, 700, 1800, 5000, 14000, 17500, 21000}, 3000, 4);
		gameFields[24] = new Street(Language.FieldController_Groenningen, Language.FieldController_Owner, Language.FieldController_FieldPrice, 24, 4800, 2400, new int [] {400, 800, 2000, 6000, 15000, 18500, 22000}, 3000, 4);
		gameFields[25] = new Fleet(Language.FieldController_GedserRostock, Language.FieldController_Fleet, Language.FieldController_FieldPrice, 25, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[26] = new Street(Language.FieldController_Bredgade, Language.FieldController_Owner, Language.FieldController_FieldPrice, 26, 5200, 2600, new int [] {450, 900, 2200, 6600, 16000, 19500, 23000}, 3000, 5);
		gameFields[27] = new Street(Language.FieldController_KgsNytorv, Language.FieldController_Owner, Language.FieldController_FieldPrice, 27, 5200, 2600, new int [] {450, 900, 2200, 6600, 16000, 19500, 23000}, 3000, 5);
		gameFields[28] = new Brewery(Language.FieldController_Carlsberg, Language.FieldController_HaveADrink, Language.FieldController_FieldPrice, 28, 3000, 1500,  new int[] {100,200});
		gameFields[29] = new Street(Language.FieldController_Oestergade, Language.FieldController_Owner, Language.FieldController_FieldPrice, 29, 5600, 2800, new int [] {500, 1000, 2400, 7200, 17000, 20500, 24000}, 3000, 5);
		gameFields[30] = new Jail(Language.FieldController_JailField, Language.FieldController_GoToJail, "", 30);
		gameFields[31] = new Street(Language.FieldController_Amagertorv, Language.FieldController_Owner, Language.FieldController_FieldPrice, 31, 6000, 3000, new int [] {550, 1100, 2600, 7800, 18000, 22000, 25000},4000, 6);
		gameFields[32] = new Street(Language.FieldController_Vimmelskaftet, Language.FieldController_Owner, Language.FieldController_FieldPrice, 32, 6000, 3000, new int [] {550, 1100, 2600, 7800, 18000, 22000, 25000},4000, 6);
		gameFields[33] = new Chance(Language.FieldController_Chance, Language.FieldController_DrawChanceCard, "", 33);
		gameFields[34] = new Street(Language.FieldController_Nygade, Language.FieldController_Owner, Language.FieldController_FieldPrice, 34, 6400, 3200, new int [] {600, 1200, 3000, 9000, 20000, 24000, 28000},4000, 6);
		gameFields[35] = new Fleet(Language.FieldController_RoedbyPuttgarden, Language.FieldController_Fleet, Language.FieldController_FieldPrice, 35, 4000, 2000, new int[]{500,1000,2000,4000});
		gameFields[36] = new Chance(Language.FieldController_Chance, Language.FieldController_DrawChanceCard, "", 36);
		gameFields[37] = new Street(Language.FieldController_Frederiksberggade, Language.FieldController_Owner, Language.FieldController_FieldPrice, 37, 7000, 3500, new int [] {700, 1400, 3500, 10000, 22000, 26000, 30000},4000, 7);
		gameFields[38] = new Tax(Language.FieldController_Taxfield, Language.FieldController_PayTaxAmount, "", 38, 2000);
		gameFields[39] = new Street(Language.FieldController_Raadhuspladsen, Language.FieldController_Owner, Language.FieldController_FieldPrice, 39, 8000, 4000, new int [] {1000, 2000, 4000, 12000, 28000, 34000, 40000},4000, 7);
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
					if(((Street)gameFields[i]).getAmountOfHouses()==5){
						hotels++;
						houses+= 4;
					}else{
						houses+= ((Street)gameFields[i]).getAmountOfHouses();
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
				if(((Ownable)gameFields[i]).getOwner() != null){
					if(((Ownable)gameFields[i]).getOwner().equals(player)){
						listOfProperties.add(gameFields[i]);
					}
				}
			}
		}
		Field[] array = new Field[listOfProperties.size()];
		array = listOfProperties.toArray(array);
		return array;
	}
	
	public Field[] getAllOwnedPropertiesNotPawned(Player player){
		ArrayList<Field> listOfProperties = new ArrayList<Field>();
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(((Ownable)gameFields[i]).getOwner() != null){
					if(((Ownable)gameFields[i]).getOwner().equals(player)){
						if(!((Ownable)gameFields[i]).getIsPawn()){
							listOfProperties.add(gameFields[i]);
						}
					}
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
				if(((Ownable)gameFields[i]).getOwner() != null){
					if(((Ownable)gameFields[i]).getOwner() == p){
						count++;
					}
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
						amount += ((Street)gameFields[i]).getAmountOfHouses() * ((Street)gameFields[i]).getBuildingPrice();
						amount += ((Street)gameFields[i]).getAmountOfHotels() * ((Street)gameFields[i]).getBuildingPrice();
					}
					amount += ((Ownable)gameFields[i]).getPrice();
				}
			}
		}
		return amount;
	}
	
	public int getPropertyValueNotPawned(Player player){
		int amount = 0;
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(((Ownable)gameFields[i]).getOwner() == player){
					if(!((Ownable)gameFields[i]).getIsPawn()){
						if(gameFields[i] instanceof Street){
							amount += ((Street)gameFields[i]).getAmountOfHouses() * ((Street)gameFields[i]).getBuildingPrice();
							amount += ((Street)gameFields[i]).getAmountOfHotels() * ((Street)gameFields[i]).getBuildingPrice();
						}
						amount += ((Ownable)gameFields[i]).getPrice();
					}
				}
			}
		}
		return amount;
	}
	
	public boolean ownsEntireStreet(Player player){
		boolean ownsEntireStreet = false;
		int[] streetIndexes = new int[8];
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Street){
				streetIndexes[((Street)gameFields[i]).getStreetCategory()]++;
			}
		}
		int[] ownedIndexes = new int[8];
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Street){
				if(((Ownable)gameFields[i]).getOwner() != null){
					if(((Street)gameFields[i]).getOwner().equals(player)){
						ownedIndexes[((Street)gameFields[i]).getStreetCategory()]++;
					}
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
			if(gameFields[i] instanceof Street){
				streetIndexes[((Street)gameFields[i]).getStreetCategory()]++;
			}
		}
		int[] ownedIndexes = new int[8];
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Street){
				if(((Ownable)gameFields[i]).getOwner() != null){
					if(((Street)gameFields[i]).getOwner().equals(player)){
						ownedIndexes[((Street)gameFields[i]).getStreetCategory()]++;
					}
				}
			}
		}
		ArrayList<Field> listOfBuildings = new ArrayList<Field>();
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Street){
				if(ownedIndexes[((Street)gameFields[i]).getStreetCategory()] == streetIndexes[((Street)gameFields[i]).getStreetCategory()]){
					listOfBuildings.add(gameFields[i]);
				}
			}
		}
		/*
		for(int i = 0; i < listOfBuildings.size(); i++){
			if(((Street)listOfBuildings.get(i)).getAmountOfHouses() <= ((Street)listOfBuildings.get(i)).getHousesInSection(((Street)listOfBuildings.get(i)).getStreetCategory(), gameController)/((Street)listOfBuildings.get(i)).getAmountOfStreetsInCategory(((Street)listOfBuildings.get(i)).getStreetCategory(),  gameController)){
				listOfBuildings.remove(i);
				i--;
			}
		}*/
		Field[] fields = new Field[listOfBuildings.size()];
		fields = listOfBuildings.toArray(fields);
		return fields;
	}
	
	public void removeAllOwnershipOfPlayer(Player player){
		for(int i = 0; i < gameFields.length; i++){
			if(gameFields[i] instanceof Ownable){
				if(((Ownable)gameFields[i]).getOwner() == player){
					((Ownable)gameFields[i]).setOwner(null);
					if(gameFields[i] instanceof Street){
						((Street)gameFields[i]).removeAllOwnership();
					}
				}
			}
		}
	}
	
		
}

