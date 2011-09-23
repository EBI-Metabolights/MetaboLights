package uk.ac.ebi.metabolights.repository.accessionmanager;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.ebi.metabolights.model.StableId;

public class StableIdTest {

	final Integer ID=3;
	final String PREFIX="HOLA";
	
	@Test
	public void testId() {
		StableId stableId = new StableId();
		
		stableId.setSeq(ID);
		assertEquals(ID, stableId.getSeq());
		
	}

	@Test
	public void testPrefix() {
		StableId stableId = new StableId();
		
		stableId.setPrefix(PREFIX);
		assertEquals(PREFIX, stableId.getPrefix());
	}
}
