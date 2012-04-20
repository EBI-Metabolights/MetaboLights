package uk.ac.ebi.metabolights.parallelcoordinates;

import java.util.ArrayList;
import java.util.List;

public class ParallelCoordinatesDataSet {
	
	List<ParallelCoordinatesSeries> series = new ArrayList<ParallelCoordinatesSeries>();
	List<ParallelCoordinatesDimension> units = new ArrayList<ParallelCoordinatesDimension>();

	public List<ParallelCoordinatesDimension> getUnits() {
		return units;
	}

	public void setUnits(List<ParallelCoordinatesDimension> units) {
		this.units = units;
	}

	public List<ParallelCoordinatesSeries> getSeries() {
		return series;
	}

	public void setSeries(List<ParallelCoordinatesSeries> series) {
		this.series = series;
	}
	
	
	public String getUnitsToString(){
		String result = "";
		
		for (ParallelCoordinatesDimension unit: units){
			result = result.concat("," + unit.toString()) ;
		}
		
		// Remove the first comma
		result = result.replaceFirst(",", "");
		
		return result;
	}
	
	public String getSeriesToString(){
		String result = "";
		
		for (ParallelCoordinatesSeries serie: series){
			result = result.concat("," + serie.toString()) ;
		}
		
		// Remove the first comma
		result = result.replaceFirst(",", "");
		
		return result;
	}
	
	

}
