package uk.ac.ebi.metabolights.parallelcoordinates;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a date series in a parallel coordinates graphic
 * It will hold the name of the series and the series data values (numeric):
 * {name:"water", siv:50, st:1, dsp:307, hp:130, lbs:3504, acc:12, year:789, origin:1},
 */
public class ParallelCoordinatesSeries {
	String name;
	List<ParallelCoordinatesSerieValue> values = new ArrayList<ParallelCoordinatesSerieValue>();
	
	public ParallelCoordinatesSeries(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ParallelCoordinatesSerieValue> getValues() {
		return values;
	}

	public void setValues(List<ParallelCoordinatesSerieValue> values) {
		this.values = values;
	}
	
	@Override
	public String toString(){
		
	// We aim this..: {name:"water", siv:50, st:1, dsp:307, hp:130, lbs:3504, acc:12, year:789, origin:1}
		
		String result = "{name:\"" + name + "\"";
		
		for (ParallelCoordinatesSerieValue value: values){
			result = result.concat("," + value.toString()) ;
		}
		
		// Remove the first comma
		//result.replaceFirst(",", "");
		
		return result + "}";
	}
	
	

}
