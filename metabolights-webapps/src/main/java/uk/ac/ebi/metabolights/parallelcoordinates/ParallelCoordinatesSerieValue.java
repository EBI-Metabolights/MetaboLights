package uk.ac.ebi.metabolights.parallelcoordinates;

public class ParallelCoordinatesSerieValue {
	String abbreviation;
	String value;
	public ParallelCoordinatesSerieValue(String abbreviation, String value){
		this.abbreviation = abbreviation;
		this.value= value;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString(){

		// Sample: siv:50 or siv:"male"
		try{
			Double number = Double.parseDouble(value);
			
			return abbreviation + ":" + number;
		
		// Is not a number
		}catch (Exception e) {

			return  abbreviation + ":\"" + value + "\"";
		}
		
		
	}
	

}
