package uk.ac.ebi.metabolights.checklists;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.CheckListItem;
import uk.ac.ebi.metabolights.checklists.ICheckListItemSeed;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;

public class CheckListTest {
	
	@Test
	public void testCheckList() {
		//Create a check list from an array
		CheckList cl = createCheckList();
	
		//Check the items
		//There must be 2 items
		assertEquals(2, cl.size());
		
		//Check first item
		assertTrue("Key1 item exists", cl.containsKey("key1"));
		CheckListItem cli = cl.get("key1");
		assertEquals("key1", cli.getKey());
		assertEquals("title1", cli.getTitle());
		
		//Check second item
		assertTrue("Key2 item exists", cl.containsKey("key2"));
		cli = cl.get("key2");
		assertEquals("key2", cli.getKey());
		assertEquals("title2", cli.getTitle());
		
	}
	public void testCheckListfromSeed(){
		CheckList cl = new CheckList(SubmissionProcessCheckListSeed.values());
		
		//Test items
		assertEquals(SubmissionProcessCheckListSeed.values().length, cl.size());
		
		//Test each item is in the check list...
		for (ICheckListItemSeed clis: SubmissionProcessCheckListSeed.values()){
		
			CheckListItem cli = cl.get(clis.getKey());
			assertEquals(clis.getKey(), cli.getKey());
			assertEquals(clis.getTitle(), cli.getTitle());
			
		}
		
	}
	private CheckList createCheckList(){
		return  new CheckList(new String[]{"key1","title1","key2","title2"});
	}

	@Test
	public void testCheckItem() {
		
		CheckList cl = createCheckList();
		
		//Check item2
		cl.CheckItem("key2", "my notes");
		
		//Check when item exists...
		CheckListItem cli = cl.get("key2");
		assertEquals(true, cli.getIsChecked());
		assertEquals("my notes", cli.getNotes());
		
		//Check when the item does not exists
		cl.CheckItem("key3", "other notes");
		
		cli = cl.get("key3");
		assertEquals("key3", cli.getKey());
		assertEquals(null, cli.getTitle());
		assertEquals("other notes", cli.getNotes());
		
	}

	@Test
	public void testAddCheckListItem() {
		
		CheckList cl = createCheckList();
		cl.AddCheckListItem("key3", "title3");

		CheckListItem cli = cl.get("key3");
		assertEquals("key3", cli.getKey());
		assertEquals("title3", cli.getTitle());

	}

}
