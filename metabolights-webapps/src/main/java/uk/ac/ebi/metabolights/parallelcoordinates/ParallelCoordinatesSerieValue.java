package uk.ac.ebi.metabolights.parallelcoordinates;

public class ParallelCoordinatesSerieValue {
	String abbreviation;
	double value;
	public ParallelCoordinatesSerieValue(String abbreviation, double value){
		this.abbreviation = abbreviation;
		this.value= value;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		// Sample: siv:50
		return  abbreviation + ":" + value;
	}
	

}
