package field;

import controllers.GameController;

public abstract class Field {
	
	protected String name, subText, descriptionText ; 
	private int number;
	
	
	public Field (String name, String descriptionText, String subText, int number) {
		this.name = name;
		this.subText = subText;
		this.descriptionText = descriptionText;
		this.number = number;
		
	}

	public abstract boolean landOn(GameController GameController);

	public String getName() {
		return name;
	}

	public String getSubText() {
		return subText;
	}

	public String getDescriptionText() {
		return descriptionText;
	}
	
	public int getNumber(){
		return number;
	}

	
	
	
}
	
	

