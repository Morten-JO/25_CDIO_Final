package field;

public abstract class Field {
	
	private String Title, Subtitle, Description ; 
	private int fieldnum;
	
	
	public Field (String Titel, String Sub, String Desc, int fieldNum) {
		this.Title = Titel;
		this.Subtitle = Sub;
		this.Description = Desc;
		this.fieldnum = fieldNum;
		
	}

	public abstract boolean landOn(GameController gameController);

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getSubtitle() {
		return Subtitle;
	}

	public void setSubtitle(String subtitle) {
		Subtitle = subtitle;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getFieldNo() {
		return fieldnum;
	}

	public void setFieldNo(int fieldNo) {
		this.fieldnum = fieldNo;
	}
	

}
	
	

