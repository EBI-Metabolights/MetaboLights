/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/9/13 10:46 AM
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

package uk.ac.ebi.metabolights.search;

import java.io.UnsupportedEncodingException;

public class FilterItem {

	private String text;
	private String name;
	private String value;
	private int number = 0;
	private boolean isChecked;
	public FilterItem(String text, String name){
		this.text = text;
		this.name = name;
		this.value = text;
	}

    public FilterItem(String text, String name, String value){
        this.text = text;
        this.name = name;
        this.value = value;
    }

	public String getText(){
		return this.text;
	}

	public String getName(){
		return this.name;
	}

	public boolean getIsChecked(){
		return isChecked;
	}

	public void setIsChecked(boolean isChecked){
		this.isChecked = isChecked;
	}

	public String getValue(){
		return this.value;
    }

    public String getUTF8Value(){
        return toUFT8(value);
    }

	public void setUTF8Value(String value){
         this.value = toUFT8(value);
	}

    public void setValue(String value){
        this.value = value;
    }

	public int getNumber(){
		return number;
	}

	public void addToNumber(int value){
		number = number + value;
	}

	public void reset(){
		this.isChecked = false;
		this.number = 0;
	}

    private String toUFT8(String valueStr) {
        String newStr = "";

        if (valueStr != null){
            byte[] ptext = valueStr.getBytes();
            try {
                newStr = new String(ptext, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();  //TODO
            }
        }

        return newStr;
    }

}
