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

/*
 * Represents a data series in a parallel coordinates graphic
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
