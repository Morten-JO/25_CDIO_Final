package chancecards;

import controllers.PlayerController;

public class GameControllerold {
	private String[] names;// = new String[] {"anders", "johan", "svend", "bjarne"};
	PlayerController pc;// = new PlayerController(names);
	
	public GameControllerold() {
		 names = new String[] {"anders", "johan", "svend", "bjarne"};
		
		 pc = new PlayerController();
		 pc.createPlayers(names);
	}
	
	//landon for test
	public boolean landOn(int currentPlayer, int amount){
		return true;
	}

}
