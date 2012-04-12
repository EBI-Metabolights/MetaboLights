package uk.ac.ebi.metabolights.parallelcoordinates;

public class ParallelCoordinatesStrategyFactory {
	
	public static ParallelCoordinatesStrategy getStrategy(){
		return new ParallelCoordinatesStrategyFixed();
	}

}
