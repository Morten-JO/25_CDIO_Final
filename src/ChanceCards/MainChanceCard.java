package ChanceCards;

import controllers.*;

public class MainChanceCard {

	public MainChanceCard() {
	}

	public static void main(String[] args) {
		GameController gc = new GameController();
		ChanceCardController controllerCC = new ChanceCardController();
		gc.se
	
		for(int i = 0;i<10;i++){
			controllerCC.drawCard(gc);
			controllerCC.shuffleCards();
		}
		
		//gc.pc.adjustBalance(2, -3500);
		//int bal = gc.getBalance(0);
		
		
		//System.out.println(bal);
		System.out.println(controllerCC.toString());
	}

}
