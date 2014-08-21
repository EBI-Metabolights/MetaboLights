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

package uk.ac.ebi.metabolights.checklists;

import java.util.LinkedHashMap;

public class CheckList extends LinkedHashMap<String,CheckListItem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CheckList(String[] items){
		
		//For each item in the array
		for (int i=0;i<items.length;i=i+2){
			AddCheckListItem(items[i], items[i+1]);
		}
		
	}
	public CheckList(ICheckListItemSeed[] cliss){
		for (ICheckListItemSeed clis: cliss){
			AddCheckListItem(clis.getKey(), clis.getTitle());
		}
	}
	public void CheckItem(String key, String newNotes){
		
		//Get the item by the key
		CheckListItem item = super.get(key);
		
		//If the item is not present...be flexible..
		if (item == null){
			//Add it to the list...sorry, without any title!
			item = AddCheckListItem(key, null);
		}
		
		//Mark the item as checked and add notes...
		item.Check(newNotes);
		
	}
	public CheckListItem AddCheckListItem(String key, String title){
		
		//Instantiate the Item
		CheckListItem newItem = new CheckListItem(key, title);
		
		//Put the item
		super.put(key, newItem);
		
		//Return the item
		return newItem;
	}
}
