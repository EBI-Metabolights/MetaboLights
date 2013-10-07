/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 10:39
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;
import org.junit.Test;
import uk.ac.ebi.metabolights.utils.mztab.ConfigurationReader;

public class ConfigurationReaderTest {

    ConfigurationReader configurationReader = new ConfigurationReader();

    @Test
    public void getMSTableReference(){
        TableReferenceObject tableReferenceObject = configurationReader.getMSConfig();
        assert tableReferenceObject != null;
    }

    @Test
    public void getNMRTableReference(){
        TableReferenceObject tableReferenceObject = configurationReader.getNMRConfig();
        assert tableReferenceObject != null;
    }
}
