package ChanceCards;

import controllers.ChanceCardController;

public class MainChanceCard {

	public MainChanceCard() {
	}

	public static void main(String[] args) {
		GameControllerold gc = new GameControllerold();
		ChanceCardController controllerCC = new ChanceCardController();
	
		for(int i = 0;i<10;i++){
			//controllerCC.drawCard(gc);
			controllerCC.shuffleCards();
		}
		
		//gc.pc.adjustBalance(2, -3500);
		//int bal = gc.getBalance(0);
		
		
		//System.out.println(bal);
		System.out.println(controllerCC.toString());
	}

}
