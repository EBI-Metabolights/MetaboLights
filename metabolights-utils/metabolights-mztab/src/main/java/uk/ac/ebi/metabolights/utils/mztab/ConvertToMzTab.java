/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/5/13 10:41 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.utils.mztab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.pride.jmztab.model.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;


public class ConvertToMzTab {

    CreateMzTabSmallMolecule mzTabSmallMolecule = new CreateMzTabSmallMolecule();
    MzTabUtils utils = new MzTabUtils();
    MzTabDAO mzTabDAO = new MzTabDAO();
    MzTabFileWriter mzTabFileWriter = new MzTabFileWriter();
    //private MzTabReader mzTabReader = new MzTabReader();
    private Metadata metadata = new Metadata();
    ISAtabReader isaReader = new ISAtabReader();

    private final static Logger logger = LoggerFactory.getLogger(ConvertToMzTab.class.getName());

    //private String argsMessage = "Please use either'maf_file_name mztab_file_name' to process files.";

    public static void main(String[] args){

        if (!commandLineValidation(args)){

            System.out.println("Usage:");
            System.out.println("Parameter 1: The root folder for your ISAtab studies");
            System.out.println("Parameter 2: The root folder for your ISAtab configuration files");
            System.out.println("Parameter 3: The MetaboLights Accession number (MTBLS id) for the study");
            System.out.println();

        } else {

            String studyFolder = args[0], configFolder = args[1], accessionNumber = args[2];
            logger.info("Starting to convert Metabolite Assignment File(s) into mzTab files for study " +accessionNumber);
            new ConvertToMzTab(studyFolder, accessionNumber, configFolder);

        }
    }

    public ConvertToMzTab(){}

    public ConvertToMzTab(String studyFolder, String accessionNumber, String configFolder) {

        logger.info("Starting to convert mzTab files for study " +accessionNumber);
        try {
            convertMAFToMzTab(studyFolder, accessionNumber, configFolder);
        } catch (Exception e) {
            System.out.println("ERROR: Could not process the file: s" + e.toString());
            logger.error("ERROR: Could not process the files. " + e.toString());
        }

    }

    private Metadata addMetaDataHeader(String accessionNumber) throws URISyntaxException, MalformedURLException {
        // Setting version, mode, and type in MZTabDescription
        MZTabDescription tabDescription = new MZTabDescription(MZTabDescription.Mode.Summary, MZTabDescription.Type.Identification);
        tabDescription.setId(accessionNumber);
        Metadata mtd = new Metadata(tabDescription);
        mtd.setTitle("MetaboLights study "+accessionNumber);
        mtd.setDescription("mzTab generated for MetaboLights accession "+accessionNumber);
        mtd.addUri(new URI("http://www.ebi.ac.uk/metabolights/" + accessionNumber.toUpperCase()));
        mtd.addMsRunLocation(1, new URL("ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/"+accessionNumber));

        return mtd;
    }


    private void addContacts(Study study){

        List<uk.ac.ebi.metabolights.repository.model.Contact> contactList = isaReader.getContactsFromStudy(study);

        int i = 0;

        for (uk.ac.ebi.metabolights.repository.model.Contact contact : contactList){
            i++;
            metadata.addContactName(i, contact.getFirstName() + " " + contact.getLastName());
            metadata.addContactAffiliation(i, contact.getAffiliation());
            metadata.addContactEmail(i, contact.getEmail());

        }

    }

    private Study getMlStudy(String configFolder, String studyFolder, String studyAccession){
        Study study = isaReader.getMLStudy(configFolder,studyFolder, studyAccession);

        return study;
    }

    public void convertMAFToMzTab(String studyFolder, String accessionNumber, String configDir) {

        //Get any mafFiles from the study
        Study study = isaReader.getMLStudy(configDir, studyFolder, accessionNumber);
        List<Assay> assays = study.getAssays();

        for (Assay assay : assays){
            String mafFileName = assay.getMetaboliteAssignment().getMetaboliteAssignmentFileName();
            File mafFile = new File(mafFileName);
            addDataToFile(mafFile, mafFileName+".mztab", study, assay);
        }

    }


    private void addDataToFile(File mafFile, String mzTabFile, Study study, Assay assay){
        try {

            Collection<MetaboliteAssignmentLine> metaboliteAssignmentLines = assay.getMetaboliteAssignment().getMetaboliteAssignmentLines();

            if (metaboliteAssignmentLines != null){
                metadata = addMetaDataHeader(study.getStudyIdentifier());
                addContacts(study);
                //TODO, add instrument etc etc
            }

            MZTabFile mzTab = new MZTabFile(metadata);


            for (MetaboliteAssignmentLine metLine : metaboliteAssignmentLines){

                //Test if we have the database identifier and description, only process rows that have ids
                if (!utils.processLine(metLine))
                    continue;

                MZTabColumnFactory factory = MZTabColumnFactory.getInstance(Section.Small_Molecule);

                // Fill data
                SmallMolecule molecule = mzTabSmallMolecule.convertToMzTab(metLine, metadata, factory);
                mzTab.setSmallMoleculeColumnFactory(factory); //This stores the column headers
                mzTab.addSmallMolecule(molecule);   //This adds the column data rows

            }

            mzTabFileWriter.writeMzTab(mzTabFile, mzTab);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the correct parameters are given
     * @param args
     * @return true if you got it right
     */
    public static boolean commandLineValidation(String args[]){

        // If there isn't any parameter
        if (args == null || args.length == 0){
            return false;
        }

        File first = new File(args[0]);
        if (!first.exists()){
            System.out.println("ERROR:  1st parameter must be the root folder for your ISAtab studies: " + args[0]);
            System.out.println("----");
            return false;
        }

        File secound = new File(args[1]);
        if (!secound.exists()){
            System.out.println("ERROR: 2nd parameter must be the root folder for your ISAtab configuration files: " + args[1]);
            System.out.println("----");
            return false;
        }

        if (args[2] == null){
            System.out.println("ERROR: You must also give us the MetaboLights Accession number (MTBLS id) for this MetaboLights study");
            System.out.println("----");
            return false;
        }

        return true;

    }

}
