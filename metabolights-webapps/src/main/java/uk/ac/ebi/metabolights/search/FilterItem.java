package uk.ac.ebi.metabolights.search;

import java.io.UnsupportedEncodingException;

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

    public FilterItem(String text, String name, String value){
        this.text = text;
        this.name = name;
        this.value = value;
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

    public String getUTF8Value(){
        return toUFT8(value);
    }

	public void setUTF8Value(String value){
         this.value = toUFT8(value);
	}

    public void setValue(String value){
        this.value = value;
    }

	public int getNumber(){
		return number;
	}

	public void addToNumber(int value){
		number = number + value;
	}

	public void reset(){
		this.isChecked = false;
		this.number = 0;
	}

    private String toUFT8(String valueStr) {
        String newStr = "";

        if (valueStr != null){
            byte[] ptext = valueStr.getBytes();
            try {
                newStr = new String(ptext, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();  //TODO
            }
        }

        return newStr;
    }

}
