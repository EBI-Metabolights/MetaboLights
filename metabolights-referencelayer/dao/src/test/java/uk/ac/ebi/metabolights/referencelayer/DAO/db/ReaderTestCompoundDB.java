/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.metabolights.referencelayer.domain.*;

import java.io.File;
import java.sql.Connection;

public class ReaderTestCompoundDB extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(ReaderTestCompoundDB.class);

	private Connection con;
	protected MetaboLightsCompoundDAO mcd;

    static String[] newCompound = new String[]{null,"ACC1","New compound", "New compound description", "New inchi value", "New chebiId", "New iupac name", "New formula", Boolean.TRUE.toString(), Boolean.TRUE.toString(), Boolean.TRUE.toString(), Boolean.TRUE.toString(), Boolean.TRUE.toString(), Boolean.TRUE.toString()};
	static String[] expected = new String[]{null,"ACC1U","Updated compound", "Updated compound description", "Inchi value updated", "ChebiId updated", "iupac names updated", "formula updated", Boolean.FALSE.toString(), Boolean.FALSE.toString(), Boolean.FALSE.toString(), Boolean.FALSE.toString(), Boolean.FALSE.toString(), Boolean.FALSE.toString()};


	@Override
	@BeforeClass
	protected void setUp() throws Exception {


        DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();
		mcd = new MetaboLightsCompoundDAO(con);


	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (mcd != null) mcd.close();
		if (con != null) con.close();

	}

	/*
	 */
	public void testSavingACompound() throws Exception {

		MetaboLightsCompound mc = getNewCompound();


		mcd.save(mc);
		assertMetabolite(mc ,newCompound);

		// at least check id is not 0
		assertTrue("Checking compound id is not 0", mc.getId() != 0 );

		// Update the id (it for now the expected id.
		expected[0] = Long.toString(mc.getId());
		mc.setAccession(expected[1]);
		mc.setName(expected[2]);
		mc.setDescription(expected[3]);
		mc.setInchi(expected[4]);
		mc.setChebiId(expected[5]);
        mc.setIupacNames(expected[6]);
        mc.setFormula(expected[7]);
		mc.setHasLiterature(Boolean.parseBoolean(expected[8]));
        mc.setHasReaction(Boolean.parseBoolean(expected[9]));
        mc.setHasSpecies(Boolean.parseBoolean(expected[10]));
        mc.setHasPathways(Boolean.parseBoolean(expected[11]));
        mc.setHasNMR(Boolean.parseBoolean(expected[12]));
        mc.setHasMS(Boolean.parseBoolean(expected[13]));

		mcd.save(mc);

        assertMetabolite(mc, expected);

	}

    private MetaboLightsCompound getNewCompound(){


        MetaboLightsCompound mc = new MetaboLightsCompound();
//        String[] newCompound = new String[]{null,"ACC1","New compound", "New compound description", "New inchi value", "New chebiId", "New iupac name", "New formula", Boolean.TRUE.toString()};
        mc.setAccession(newCompound[1]);
        mc.setName(newCompound[2]);
        mc.setDescription(newCompound[3]);
        mc.setInchi(newCompound[4]);
        mc.setChebiId(newCompound[5]);
        mc.setIupacNames(newCompound[6]);
        mc.setFormula(newCompound[7]);
        mc.setHasLiterature(Boolean.parseBoolean(newCompound[8]));
        mc.setHasReaction(Boolean.parseBoolean(newCompound[9]));
        mc.setHasSpecies(Boolean.parseBoolean(newCompound[10]));
        mc.setHasPathways(Boolean.parseBoolean(newCompound[11]));
        mc.setHasNMR(Boolean.parseBoolean(newCompound[12]));
        mc.setHasMS(Boolean.parseBoolean(newCompound[13]));

        // Add a met species...
        addMetSpecies(mc);
        addSpectra(mc);
        addPathway(mc);


        return mc;
    }

    private void addMetSpecies(MetaboLightsCompound mc){

        // Create a CrossReference....
        CrossReference cr = ReaderTestCrossReferenceDB.newRandomCrossReference();

        Species sp = ReaderTestSpeciesDB.newRandomSpecies();

        // Create a new MetSpecies
        MetSpecies ms = new MetSpecies(sp,cr);

        mc.getMetSpecies().add(ms);


    }

    private void addPathway(MetaboLightsCompound mc){

        // Create a Database...
        Database db = ReaderTestDatabaseDB.newRandomDatabase();

        Species sp = ReaderTestSpeciesDB.newRandomSpecies();

        // Create a new Pathway
        Pathway pathway = new Pathway("New pathway",db,new File("."), sp);
        pathway.getAttributes().add(getNewAttribute());

        mc.getMetPathways().add(pathway);


    }

    private void addSpectra(MetaboLightsCompound mc){

        Spectra spectra = new Spectra("Spectra path to Json", "Spectra name", Spectra.SpectraType.MS);
        spectra.getAttributes().add(getNewAttribute());
        mc.getMetSpectras().add(spectra);

    }
    private Attribute getNewAttribute(){
        Attribute attribute = new Attribute();
        attribute.setValue("Attribute value");
        attribute.setAttributeDefinition(ReaderTestAttributeDefinitionDB.newRandomAttributeDefinition());

        return attribute;
    }

	public void testFindACompoundByAccession() throws Exception {

		MetaboLightsCompound mc = mcd.findByCompoundAccession(expected[1]);
		assertMetabolite(mc, expected);

	}

	public void testFindACompoundByName() throws Exception {

		MetaboLightsCompound mc = mcd.findByCompoundName(expected[2]);
		assertMetabolite(mc, expected);
	}

	public void testFindACompoundById() throws Exception {

		MetaboLightsCompound mc = mcd.findByCompoundId(Long.parseLong(expected[0]));
		assertMetabolite(mc, expected);
	}

//    public void testGetAllCompounds() throws Exception{
//
//        // Get all the compounds
//        Set<MetaboLightsCompound> mcs = spgd.getAllCompounds();
//
//        // There must be at least one
//        assertEquals("testing getAllCompounds, at least there must be one", true, mcs.size()>0);
//
//    }

    public  void testExistCompound() throws Exception{

        boolean exists = mcd.doesCompoundExists(Long.parseLong(expected[0]));

        assertTrue("Compound existence", exists);

        // This one shouldn't exist
        exists = mcd.doesCompoundExists(new Long(-23));

        assertTrue("Compound in-existence", !exists);
    }

    public void testDeleteACompound() throws Exception {

		MetaboLightsCompound mc = mcd.findByCompoundId(Long.parseLong(expected[0]));

		mcd.delete(mc);

        // We should delete the Database, species and CrossReference created for the metSpecies
        MetSpecies ms = mc.getMetSpecies().iterator().next();

        CrossReferenceDAO crd = new CrossReferenceDAO(mcd.con);
        crd.delete(ms.getCrossReference());

        DatabaseDAO dbd = new DatabaseDAO(mcd.con);
        dbd.delete(ms.getCrossReference().getDb());

        SpeciesDAO spd = new SpeciesDAO(mcd.con);
        spd.delete(ms.getSpecies());

        Pathway pathway = mc.getMetPathways().iterator().next();
        spd.delete(pathway.getSpeciesAssociated());

        // Delete the attribute definition created for the Spectra and pathways
        Spectra spectra  = mc.getMetSpectras().iterator().next();

        AttributeDefinitionDAO add = new AttributeDefinitionDAO(mcd.con);
        add.delete(spectra.getAttributes().iterator().next().getAttributeDefinition());

        add.delete(pathway.getAttributes().iterator().next().getAttributeDefinition());

        mc = mcd.findByCompoundId(Long.parseLong(expected[0]));

		assertTrue("Deleted compound must not be found" , mc == null);


	}


	private void assertMetabolite(MetaboLightsCompound mc, String[] expectedvalues){

		assertNotNull(mc);
		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id" , Long.parseLong(expectedvalues[0]) , mc.getId());
		assertEquals("Checking " + expectedvalues[1] + " accesion" , expectedvalues[1] , mc.getAccession());
		assertEquals("Checking " + expectedvalues[1] + " name" , expectedvalues[2] , mc.getName());
		assertEquals("Checking " + expectedvalues[1] + " description" , expectedvalues[3] , mc.getDescription());
		assertEquals("Checking " + expectedvalues[1] + " inchi" , expectedvalues[4] , mc.getInchi());
		assertEquals("Checking " + expectedvalues[1] + " chebiId" , expectedvalues[5] , mc.getChebiId());
        assertEquals("Checking " + expectedvalues[1] + " iupac names" , expectedvalues[6] , mc.getIupacNames());
        assertEquals("Checking " + expectedvalues[1] + " formula" , expectedvalues[7] , mc.getFormula());
        assertEquals("Checking " + expectedvalues[1] + " literature" , Boolean.parseBoolean(expectedvalues[8]) , mc.getHasLiterature());
        assertEquals("Checking " + expectedvalues[1] + " reactions" , Boolean.parseBoolean(expectedvalues[9]) , mc.getHasSpecies());
        assertEquals("Checking " + expectedvalues[1] + " pathways" , Boolean.parseBoolean(expectedvalues[11]) , mc.getHasPathways());
        assertEquals("Checking " + expectedvalues[1] + " NMR" , Boolean.parseBoolean(expectedvalues[12]) , mc.getHasNMR());
        assertEquals("Checking " + expectedvalues[1] + " MS" , Boolean.parseBoolean(expectedvalues[13]) , mc.getHasMS());

        assertMetSpecies(mc);
        assertMetSpectra(mc);
        assertMetPathway(mc);


	}

    private void assertMetSpecies(MetaboLightsCompound mc){

        // There must be one metSpecies
        assertEquals("Check number of metSpecies" ,1, mc.getMetSpecies().size() );

        // Get it....
        MetSpecies ms = mc.getMetSpecies().iterator().next();

        // Check it has a database
        assertNotNull("Has MetSpecies a CrossReference?", ms.getCrossReference());
        assertNotNull("Has MetSpecies a Species?", ms.getSpecies());


        // If the id is not null (compound is saved)...
        if (mc.getId() != 0){
            assertTrue("Is Database saved?", ms.getCrossReference().getDb().getId() != 0);
            assertTrue("Is CrossReference saved?", ms.getCrossReference().getId() != 0);
            assertTrue("Is Species saved?",  ms.getSpecies().getId()!=0);
            assertTrue("Is MetSpecies saved?",  ms.getId()!=0);

        }
    }

    private void assertMetPathway(MetaboLightsCompound mc){

        // There must be one pathway
        assertEquals("Check number of pathways" ,1, mc.getMetPathways().size());

        // Get it....
        Pathway pathway = mc.getMetPathways().iterator().next();



        // Check it has a database
        assertNotNull("Has Pathway a database?", pathway.getDatabase());
        // Check it has a values
        assertNotNull("Has Pathway a name?", pathway.getName());
        assertNotNull("Has Pathway a file?", pathway.getPathToPathwayFile());

        // There must be one attribute
        assertEquals("Check number of attributes" ,1, pathway.getAttributes().size());

        // If the id is not null (compound is saved)...
        if (mc.getId() != 0){
            assertTrue("Is Database saved?", pathway.getDatabase().getId() != 0);
            assertTrue("Is species saved?",  pathway.getSpeciesAssociated().getId()!=0);
            assertTrue("Is pathway saved?",  pathway.getId()!=0);


        }
    }

    private void assertMetSpectra(MetaboLightsCompound mc){

        // There must be one metSpecies
        assertEquals("Check number of metSpectras" ,1, mc.getMetSpectras().size() );

        // Get it....
        Spectra spectra = mc.getMetSpectras().iterator().next();

        // Check it has a values
        assertNotNull("Has spectra a name?", spectra.getName());
        assertNotNull("Has spectra a file?", spectra.getPathToJsonSpectra());


        // There must be one attribute
        assertEquals("Check number of attributes" ,1, spectra.getAttributes().size());

        // If the id is not null (compound is saved)...
        if (mc.getId() != 0){

            assertTrue("Is Spectra saved?",  spectra.getId()!=0);

        }
    }
}
