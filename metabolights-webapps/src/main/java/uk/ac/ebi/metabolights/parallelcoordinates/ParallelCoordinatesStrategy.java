package uk.ac.ebi.metabolights.parallelcoordinates;

import uk.ac.ebi.bioinvindex.model.AssayGroup;
import uk.ac.ebi.bioinvindex.model.Study;

public interface ParallelCoordinatesStrategy {
	public ParallelCoordinatesDataSet Proccess(AssayGroup ag, Study study);

}
