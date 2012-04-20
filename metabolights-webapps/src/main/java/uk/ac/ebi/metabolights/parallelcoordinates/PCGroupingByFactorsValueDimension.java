package uk.ac.ebi.metabolights.parallelcoordinates;

import java.util.ArrayList;

public class PCGroupingByFactorsValueDimension extends ParallelCoordinatesDimension{

	private ArrayList<Double> values = new ArrayList<Double>();
	public PCGroupingByFactorsValueDimension(String abbreviation, String name, String unit) {
		super(abbreviation, name, unit);
	}
	
	public ArrayList<Double> getValues(){
		return values;
	}
	public double calculateMean(){
		
		double mean= 0;
		
		// Add all values
		for (int i=0;i<values.size();i++){
			mean=mean + values.get(i);
		}
		
		// Calculate the mean
		mean = mean / values.size();
		
		return mean;
	}
}
