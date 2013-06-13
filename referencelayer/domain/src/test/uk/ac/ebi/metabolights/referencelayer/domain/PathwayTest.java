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


        Pathway pathway1 = new Pathway("pathway name",databse1, new File("."));
        assertTrue("Equals to itself expected to be true", pathway1.equals(pathway1));


        Database db2 = new Database();
        databse1.setName("db2");

        pathway1.setName("pathway name2");


        Pathway pathway2 = new Pathway(pathway1.getName(),db2, new File("."));

        assertFalse("Equals expected to be false", pathway1.equals(pathway2));

        pathway2 = new Pathway(pathway1.getName(),databse1, new File("."));
        assertTrue("Equals expected to be true", pathway1.equals(pathway2));


        pathway2 = new Pathway(null,null, null);
        assertFalse("Equals expected to be false", pathway1.equals(pathway2));

        pathway1 = new Pathway(null,null, null);
        assertTrue("Equals expected to be true", pathway1.equals(pathway2));


    }
}
