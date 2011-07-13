package uk.ac.ebi.metabolights.search;

public class FilterItem {
	private String text;
	private String name;
	private String value;
	private int number = 0;
	private boolean isChecked;
	public FilterItem(String text, String name){
		this.text = text;
		this.name = name;
		this.value = text;
	}
	public String getText(){
		return this.text;
	}
	public String getName(){
		return this.name;
	}
	public boolean getIsChecked(){
		return isChecked;
	}
	public void setIsChecked(boolean isChecked){
		this.isChecked = isChecked;
	}
	public String getValue(){
		return this.value;
	}
	public int getNumber(){
		return number;
	}
	public void addToNumber(int value){
		number= number + value;
	}
}
