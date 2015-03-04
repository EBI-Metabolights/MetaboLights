/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/15/14 3:59 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * EBI MetaboLights Project - 2012.
 *
 * File: MLAssay.java
 *
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.model;

import uk.ac.ebi.bioinvindex.model.*;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.term.Factor;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.metabolights.parallelcoordinates.ParallelCoordinatesDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * THis is a Metabolights assay as it is needed in the webapp
 * @author conesa
 *
 */
public class MLAssay {
	private String technology;
	private String platform;
	private String measurement;
	private String fileName;
	private List<Factor> factors = new ArrayList<>();
	private List<MLAssayResult> MLAssayResult = new ArrayList<>();
	private List<Assay> assayLines = new ArrayList<>();
	private List<MetaboliteGUI> metabolites = new ArrayList<>();
	private AssayGroup ag;
	private Study study;
	private ParallelCoordinatesDataSet pcds;
	private List<String> sampleColumns = new ArrayList<>();
	
	
	public static String getAssayNameFromAssay(Assay assay){
		
		for (Annotation annotation: assay.getAnnotations()){
			if (annotation.getType().getValue().equalsIgnoreCase("assayFileId")){
				return annotation.getText();
			}
		}
		return "";
		
	}
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	public MLAssay(Assay assay){
		this.fileName = getAssayNameFromAssay(assay);
		this.technology = assay.getTechnologyName();
		this.platform = assay.getAssayPlatform();
		this.measurement = assay.getMeasurement().getName();

		
	}
	
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getMeasurement() {
		return measurement;
	}
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<Factor> getFactors(){
		return factors;
	}
	public List<MLAssayResult> getMLAssayResult(){
		return MLAssayResult;
	}
	public void addAssayResult(AssayResult newAssayResult){

        MLAssayResult.add(new MLAssayResult(newAssayResult));
		checkFactors(newAssayResult);
			
	}
	public List<Assay> getAssayLines(){
		return assayLines;
	}
	public void addAssayLines(Assay newAssayLine){
		assayLines.add(newAssayLine);
		
	}
	private void checkFactors(AssayResult assayResult){
		
		// Get the factors ...
		for (FactorValue factorValue :assayResult.getData().getFactorValues()){
			// If its a new factor
			if (!HaveFactor(factorValue.getType())){
				factors.add(factorValue.getType());
			}
		}
	}
	private boolean HaveFactor(Factor newFactor){
		for (Factor factor:factors){
			if (factor.getValue().equals(newFactor.getValue())){
				return true;
			}
		}
		 return false;
	}
	public void setAssayGroup (AssayGroup ag){
		this.ag = ag;
		addGUIMetabolites();

	}
	public List<MetaboliteGUI> getMetabolitesGUI(){
		return metabolites;
	}
	
	private void addGUIMetabolites(){
		
		for (Metabolite met: ag.getMetabolites()){

            String desc = met.getDescription();

            desc = desc==null?"":desc;

			// Do not add metabolites unknown
			if (!desc.toLowerCase().startsWith("unk") && !desc.toLowerCase().startsWith("unid")){

				metabolites.add(new MetaboliteGUI(met));
				addSampleColumns(met);
				
			}
		}

		fillGapsAndRearrangeGUIMetabolites();
		
	}

	private void addSampleColumns(Metabolite met){

		for (MetaboliteSample ms : met.getMetaboliteSamples()){

			if (!sampleColumns.contains(ms.getSampleName())){
				sampleColumns.add (ms.getSampleName());
			}
		}
	}

	private void sortMetaboLitesSample (List<MetaboliteSample> samples) {

		// loop through the sample columns
		for (int columnIndex = 0; columnIndex < sampleColumns.size(); columnIndex++) {

			// Get the sample at the index
			String sampleColumn = sampleColumns.get(columnIndex);

			// Initialize an empty Sample
			MetaboliteSample sample = null;
			// look for the match
			int sampleIndex= 0;

			// Not at the end of the samples list (this will happen when samples is shorter than sampleColumns.
			if (samples.size()> columnIndex) {
				// Get the sample from the MetabolitesSample list
				sample = samples.get(columnIndex);

				// If it's the same...
				if (sampleColumn.equals(sample.getSampleName())) continue;

				//...it's different
				sample = null;

				for (sampleIndex = columnIndex + 1; sampleIndex < samples.size(); sampleIndex++) {

					MetaboliteSample loopSample = samples.get(sampleIndex);

					if (sampleColumn.equals(loopSample.getSampleName())) {
						sample = loopSample;
						break;
					}
				}



			}

			// If not found...create an empty one
			if (sample == null) {
				sample = new MetaboliteSample(null, sampleColumn, "");

			} else {

				// remove it from the list to add it in the proper place (move it) later
				samples.remove(sampleIndex);
			}

			// At this point we should have a sample, either real or an empty made up one.
			// Add it to the propper place
			samples.add(columnIndex, sample);
		}

	}
	private void fillGapsAndRearrangeGUIMetabolites(){


		// Loop through the collection of metabolites.
		for (MetaboliteGUI metGui: metabolites){


				// Get the samples as ArrayList
				List<MetaboliteSample> samples = (List<MetaboliteSample>) metGui.getMetabolite().getMetaboliteSamples();

				// Sort it according to the sampleColumns list
				sortMetaboLitesSample(samples);

			}
		}


		// This case does not cover some cases, where the final sampleColumn list may not keep the same order as the original Maf file
		// e.g.:
		//MAF FILE
		//Identifier	S1	S2	S3
		//CHEBI:123			2	3
		//CHEBI:456		1	2	3

		// Under this circumstances, sampleColumns will be 2,3,1 therefore second line needs to be rearranged.
		//WEB APP will show:
		//Identifier	S2	S3	S1
		//CHEBI:123		2	3
		//CHEBI:456		2	3	1

//		// Keeping the original order implies a more complez algorithm, and in some cases it can be imposible.
//
//			for (String sampleColumn: sampleColumns){
//
//				// Get the index...
//				int index = sampleColumn.indexOf(sampleColumn);
//
//				// for each MetaboliteSample row...
//				for (MetaboliteGUI metGui: metabolites){
//
//					// If the sample count matches the sampleColumns count
//					if (metGui.getMetabolite().getMetaboliteSamples().size() == sampleColumns.size()){
//						continue;
//					}
//
//					// Get the samples as ArrayList
//					List<MetaboliteSample> samples = (List<MetaboliteSample>) metGui.getMetabolite().getMetaboliteSamples();
//
//					// Get the sample ant the index position
//					MetaboliteSample sample = samples.get(index);
//
//					if (!sample.getSampleName().equals(sampleColumn)){
//
//						// Miising sample....add an empty one
//						samples.add(index, new MetaboliteSample(metGui.getMetabolite(),sampleColumn,""));
//					}
//				}
//			}

}
