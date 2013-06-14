package uk.ac.ebi.metabolights.referencelayer.domain;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 15:21
 */
public class PathwayTest {
    @Test
    public void testEquals() throws Exception {

        Database databse1 = new Database();
        databse1.setName("db1");

        Species sp = new Species();
        sp.setSpecies("1234");

        Pathway pathway1 = new Pathway("pathway name",databse1, new File("."),sp);
        assertTrue("Equals to itself expected to be true", pathway1.equals(pathway1));


        Database db2 = new Database();
        databse1.setName("db2");

        pathway1.setName("pathway name2");


        Pathway pathway2 = new Pathway(pathway1.getName(),db2, new File("."),sp);

        assertFalse("Equals expected to be false", pathway1.equals(pathway2));

        pathway2 = new Pathway(pathway1.getName(),databse1, new File("."),sp);
        assertTrue("Equals expected to be true", pathway1.equals(pathway2));

        Species sp2 = new Species();
        sp.setSpecies("4321");

        pathway2.setSpeciesAssociated(sp2);
        assertFalse("Equals expected to be false", pathway1.equals(pathway2));

        pathway2 = new Pathway(null,null, null,null);
        assertFalse("Equals expected to be false", pathway1.equals(pathway2));

        pathway1 = new Pathway(null,null, null,null);
        assertTrue("Equals expected to be true", pathway1.equals(pathway2));


    }
}
