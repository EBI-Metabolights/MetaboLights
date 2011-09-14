package uk.ac.ebi.metabolights.checklists;

public class CheckListItem {
	private boolean isChecked;
	private String key;
	private String title;
	private String notes;
	
	public CheckListItem(String key, String title){
		this.key= key;
		this.title= title;
	}
	public void Check(String newNotes){
		isChecked = true;
		notes = newNotes;
	}
	public boolean getIsChecked(){return isChecked;}
	public String getKey(){return key;}
	public String getTitle(){return title;}
	public String getNotes(){return notes;}
}
