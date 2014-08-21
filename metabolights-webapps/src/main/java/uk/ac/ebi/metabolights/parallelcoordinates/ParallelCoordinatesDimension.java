/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
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

package uk.ac.ebi.metabolights.parallelcoordinates;

/**
 * A unit for the parrallel coordinates javascript.
 * 
 * We need to build this:
 *	var units = {
 *		  st: {name: "Sample Type", unit: ""},
 *		  dsp: {name: "Deconvolution", unit: ""},
 *		  lbs: {name: "Alignment", unit: ""},
 *		  siv: {name: "Spike in volume", unit: " �l"},
 *		  year: {name: "mass", unit: " Da"}
 *	};
 *
 * siv --> abbreviation
 * Spyke in volume -->  name
 * �l --> unit
 * 
 * @author conesa
 *
 */
public class ParallelCoordinatesDimension {
	
	private String unit, abbreviation, name;
	
	public ParallelCoordinatesDimension(String abbreviation, String name, String unit){
		this.abbreviation = abbreviation;
		this.name = name;
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getAbbreviation() {
		return abbreviation;
	}


	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){

		// We need to return this
		//siv: {name: "Spike in volume", unit: " �l"},
		return (this.abbreviation + ": {name: \"" + this.name + "\", unit: \"" + this.unit + "\"}"); 
		
	}
}
