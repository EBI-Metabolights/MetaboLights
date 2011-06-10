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
