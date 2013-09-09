/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.nmr.rest.spectra;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.restlet.representation.Representation;

/**
 *
 * @author pmoreno
 */
public class NMRSpectraDataResourceTest {
    
    public NMRSpectraDataResourceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of represent method, of class NMRSpectraDataResource.
     */
    @Test
    public void testRepresent() throws IOException {
        System.out.println("represent");
        NMRSpectraDataResource instance = new NMRSpectraDataResource();        
        Representation result = instance.represent();
        assertNotNull(result);
        System.out.println(result.getText());        
    }
}
