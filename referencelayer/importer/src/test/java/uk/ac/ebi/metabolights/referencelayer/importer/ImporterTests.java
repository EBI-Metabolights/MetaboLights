package uk.ac.ebi.metabolights.referencelayer.importer;

import java.sql.Connection;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.chebi.webapps.chebiWS.model.RelationshipType;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.importer.ReferenceLayerImporter;

public class ImporterTests extends TestCase{
	
	protected static final Logger LOGGER = Logger.getLogger(ImporterTests.class);
	
	private Connection con;

	
	@Override
	@BeforeClass
	protected void setUp() throws Exception {
	
		// Set up a simple configuration that logs on the console.
	    BasicConfigurator.configure();
	    
		DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsMYSQL"); //OracleDatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();


	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (con != null) con.close();
		
	}

	 
	/*
	 */
	public void testImport() throws Exception {

        ReferenceLayerImporter rli = new ReferenceLayerImporter(con);

        // Set the root to anminoacid
        rli.setChebiIDRoot("CHEBI:16449");
        rli.setRelationshipType(RelationshipType.HAS_FUNCTIONAL_PARENT);
        rli.importMetabolitesFromChebi();

        /*
        Expected compounds are:
        CHEBI:177403-oxoalanine3CHEBI:48459N-(2,6-dichlorobenzoyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]alanine3CHEBI:48463N-(2,6-dichlorobenzoyl)-3-[6-(2,6-dimethoxyphenyl)-2-naphthyl]alanine3CHEBI:48473methyl 3-[2-(2,6-dichlorophenyl)quinolin-6-yl]alaninate3CHEBI:48475methyl N-(2,6-dichlorobenzyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]alaninate3CHEBI:48477methyl N-(2,6-dichlorobenzyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]-N-methylalaninate3CHEBI:48478N-(2,6-dichlorobenzyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]-N-methylalanine3CHEBI:48479N-(2,6-dichlorobenzoyl)-3-(2-phenoxy-6-quinolyl)alanine3CHEBI:48482methyl 3-(2-phenoxy-6-quinolyl)alaninate3CHEBI:48491methyl N-(tert-butoxycarbonyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]alaninate3CHEBI:48492methyl N-(tert-butoxycarbonyl)-3-[2-(2,6-dichlorophenyl)-4-(phenylsulfanyl)-1,2,3,4,4a,8a-hexahydro-6-quinolyl]alaninate3



         */

	}

}
