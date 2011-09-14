package uk.ac.ebi.metabolights.checklists;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.ebi.metabolights.checklists.CheckListItem;

public class CheckListItemTest {

	@Test
	public void testCheckListItem() {
		CheckListItem cli = new CheckListItem("K1","hola");
		
		assertEquals("hola", cli.getTitle());
		assertEquals("K1", cli.getKey());
		assertEquals(false, cli.getIsChecked());
		assertEquals(null, cli.getNotes());
		
		//Check the item
		cli.Check("my notes");
		assertEquals("my notes", cli.getNotes());
		assertEquals(true, cli.getIsChecked());
		
	}


}
