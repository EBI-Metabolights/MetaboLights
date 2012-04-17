package uk.ac.ebi.metabolights.parallelcoordinates;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ac.ebi.bioinvindex.model.AssayGroup;
import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Metabolite;
import uk.ac.ebi.bioinvindex.model.MetaboliteSample;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.metabolights.service.SearchServiceImpl;
/*
 * Generates the data summary that parallel coordinates javascript needs
 * 
 * Example:
 * 
 * MEATABOLITE FILE (maf)***********
 * 
 * 			SAMPLE1	SAMPLE2	SAMPLE3 SAMPLE4	SAMPLE5	SAMPLE6	SAMPLE7	SAMPLE8	
 * MET1		2		3		6		6		8		9		23		12
 * MET2		4		6		1		5		9		4		23		17
 * MET3		10		1		12		2		3		21		12		8
 * 
 * 
 * *********************************
 * 
 * FACTORS:  VALUES
 * Gender: Male, Female [2]
 * Condition: Disease, control, unknown [3]
 * 
 * Now, we want to calculate the mean concentration for each combianation value. 
 * For this we need to look at the samples and its factor values.
 * 
 * SAMPLE1: Male, control
 * SAMPLE2: Male, disease
 * SAMPLE3: Female, control
 * SAMPLE4: Female, disease
 * SAMPLE5: Male, unknown
 * SAMPLE6: Male, control
 * SAMPLE7: Female, unknown
 * SAMPLE8: Female, disease
 * 
 * 
 * GROUPS: 2*3 (2 genders * 3 Conditions)= 6
 * Male, control
 * Male, disease
 * Male, unknown
 * Female, control
 * Female, disease
 * Female, unknown
 * 
 * 
 * Now we can calculate the mean for each factor value
 * Lets calculate the mean for "Male-control" samples (SAMPLE1 and SAMPLE6
 * 
 * 			SAMPLE1	SAMPLE2	SAMPLE3 SAMPLE4	SAMPLE5	SAMPLE6	SAMPLE7	SAMPLE8		MEAN	
 * MET1		2										9							5.5
 * MET2		4										4							4
 * MET3		10										21							15.5
 * 
 * 
 * Now "Male-disease" samples (there is only one)
 *
 * 			SAMPLE2		MEAN
 * MET1		3			3
 * MET2		6			6
 * MET3		1			1
 * 
 * 
 * ..and so on 
 * 
 * 
 * So, the final matrix we want for the parallel coordinates is
 * 
 * 			Males-control	Male-disease	Male-unknown	Female-control	Female-disease	Female-unknown
 * MET1		5.5				3				8				6				9				23
 * MET2		4				6				9				1				11				23
 * MET3		15.5			1				3				12				5				12
 *
 * 
 * NOMENCLATURE:
 * Males-control,..:	Lets call it units,.
 * METX: 		Lets call it Serie. Corresponds to a line in parallel coordinates, It must have as much values as units.
 */
public class ParallelCoordinatesStrategyAllCombinations implements ParallelCoordinatesStrategy{
	
	private static Logger logger = Logger.getLogger(ParallelCoordinatesStrategyAllCombinations.class);
	private AssayGroup ag;
	private Study study;
	private ParallelCoordinatesDataSet ds;
	// This will hold a map between sampleNames and ParallelCoodinatesUnit.names
	// In our example:
	//		<"SAMPLE1", "Males - Control">
	//		<"SAMPLE2", "Male-disease">
	// 		...
	private Map<String,String> sampleNameToUnitName = new HashMap<String,String>();
	
	
	@Override
	public ParallelCoordinatesDataSet Proccess( AssayGroup ag, Study study) {

		
		logger.info("Initiating process method. Generating parallel coordinates dataset");
		
		this.ag = ag;
		this.study = study;
		this.ds = new ParallelCoordinatesDataSet();
		
		// Fill the samplenameToUnitName map
		fillSampleNameToUnitNameMap();
		
		// Generate the dataset for the parallel coordinates
		// Go through all the metabolites
		for(Metabolite met:ag.getMetabolites()){
				
				// Process the metabolite
				ParallelCoordinatesSeries serie = processMetabolite(met);
				
				// Add it to the data set
				ds.getSeries().add(serie);
				
		}
		
		return null;
		
	}
	private ParallelCoordinatesSeries processMetabolite(Metabolite met){
				
		
		// Instantiate a series with the metabolite name
		ParallelCoordinatesSeries series = new ParallelCoordinatesSeries(met.getDescription());
		
		for (MetaboliteSample sample:met.getMetaboliteSamples()){
			
			// Get the unit name from the hashmap
			String factors = sampleNameToUnitName.get(sample.getSampleName());
			
		}
		
		return series;
		
	}

	private void fillSampleNameToUnitNameMap(){
		
		logger.info("Filling sampleNameToUnitName map");
		
		String unitName="";
		
		printStudy();
		
		// Clear the map
		sampleNameToUnitName.clear();
		
		// Loop through the assay results
		for (AssayResult ar:study.getAssayResults()){
			
			//Get sample name
			String sampleName = ar.getAssays().iterator().next().getMaterial().getName();
			
			// If we do not have this sample name in the map
			if (!sampleNameToUnitName.containsKey(sampleName)){

				// For each factor value in Data item
				for (FactorValue fv:ar.getData().getFactorValues()){
					
					
					String value;
					
					// Add the new value
					if (!(fv.getUnit() == null)){
						value = fv.getValue() + " " + fv.getUnit().getValue();
					} else {
						value = fv.getValue();
					}
					
					// Concatenate the factor value
					unitName = unitName.concat(" - " + value);
					
				}
			
				// Clean first "-"
				unitName = unitName.replaceFirst(" - ", "");

				// Add an item to the map
				sampleNameToUnitName.put(sampleName, unitName);
			} // If
		} // For each assayresult
	}
	
	private void printStudy(){
		
		
		System.out.println("STUDY\t\t\t" + study.getDescription());
		
		
		for (AssayResult ar: study.getAssayResults()){
			
			
			System.out.println("STUDY\tASSAYRESULT\t\t" + ar.getId());
			
			for (Assay assay: ar.getAssays() ){
				System.out.println("STUDY\tASSAYRESULT\tASSAY\t" + assay.getAcc());
			}
			
			System.out.println("\n\n\n");
			
			for (FactorValue fv: ar.getFactorValues() ){
				System.out.println("STUDY\tASSAYRESULT\tFACTOR VALUES\t" + fv.getValue());
			}
			
			
		}
	}
}
