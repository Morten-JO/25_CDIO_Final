package chancecards;

import controllers.*;

public class MainChanceCard {

	public MainChanceCard() {
	}

	public static void main(String[] args) {
		GameController gc = new GameController();
		ChanceCardController controllerCC = new ChanceCardController();
		
	
		for(int i = 0;i<10;i++){
			controllerCC.drawCard(gc);
			//controllerCC.shuffleCards();
		}
		
		//gc.pc.adjustBalance(2, -3500);
		int bal = gc.getPlayerController().getPlayer(0).getBalance();
		
		
		System.out.println(bal);
		System.out.println(controllerCC.toString());
	}

}
