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
