/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 11/10/13 11:59
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignmentLine;
import uk.ac.ebi.pride.jmztab.MzTabFile;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;
import uk.ac.ebi.pride.jmztab.model.Contact;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;
import uk.ac.ebi.pride.jmztab.model.Unit;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ConvertToMzTab {

    CreateMzTabSmallMolecule mzTabSmallMolecule = new CreateMzTabSmallMolecule();
    MzTabUtils utils = new MzTabUtils();
    MzTabDAO mzTabDAO = new MzTabDAO();
    MzTabFileWriter mzTabFileWriter = new MzTabFileWriter();
    MzTabReader mzTabReader = new MzTabReader();

    private final static Logger logger = Logger.getLogger(ConvertToMzTab.class.getName());

    //private String argsMessage = "Please use either'maf_file_name mztab_file_name' to process files.";

    public static void main(String[] args){

        if (!commandLineValidation(args)){

            System.out.println("Usage:");
            System.out.println("Parameter 1: The existing MetaboLights MAF (Metabolite Assignment File)");
            System.out.println("Parameter 2: The name for the new mzTab file we should create");
            System.out.println("Parameter 3: The MetaboLights Accession number (MTBLS id) for this MetaboLights study");

        } else {

            String maf = args[0], mzTab = args[1], accessionNumber = args[2];
            logger.info("Starting to convert file "+maf+ " into "+mzTab+ " for study " +accessionNumber);
            new ConvertToMzTab(maf, mzTab, accessionNumber);

        }
    }

    public ConvertToMzTab(){}

    public ConvertToMzTab(String maf, String mzTab, String accessionNumber) {

        logger.info("Starting to convert file "+maf+ " into "+mzTab+ " for study " +accessionNumber);
        try {
            convertMAFToMzTab(maf, mzTab, accessionNumber);
        } catch (MzTabParsingException e) {
            System.out.println("ERROR: Could not process the file: s" + e.toString());
            logger.error("ERROR: Could not process the files. " + e.toString());
        }

    }



    private Unit addUnit(String accessionNumber) throws MzTabParsingException {
        Unit unit = new Unit();
        unit.setUnitId(accessionNumber);
        unit.setDescription("mzTab generated for MetaboLights accession "+accessionNumber);
        //unit.setContact(addContacts());
        unit.setUri(URI.create("http://www.ebi.ac.uk/metabolights/"+accessionNumber.toUpperCase()));
        unit.setTitle("MetaboLights study "+accessionNumber);
        return unit;
    }

    private List<Contact> addContacts() throws MzTabParsingException {
        //TODO, read contacts from ISAcreator or MetaboLights study
        List<Contact> contactList = new ArrayList<Contact>();
        Contact contact = new Contact();
        contact.setName("Kenneth Haug");
        contact.setAffiliation("European Bioinformatics Institute");
        contactList.add(contact);

        contact.setName("Reza Salek");
        contact.setAffiliation("European Bioinformatics Institute");
        contactList.add(contact);

        return contactList;
    }

    public void convertMAFToMzTab(String mafFileName, String mzTabFile, String accessionNumber) throws MzTabParsingException {
        try {

            MzTabFile mzTab = new MzTabFile();
            File mafFile = new File(mafFileName);

            Collection<MetaboliteAssignmentLine> metaboliteAssignmentLines = new ArrayList<MetaboliteAssignmentLine>();

            MetaboliteAssignment metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(mafFile.toString());
            if (metaboliteAssignment != null){
                metaboliteAssignmentLines = metaboliteAssignment.getMetaboliteAssignmentLines();
                mzTab.setUnit(addUnit(accessionNumber));
            }

            for (MetaboliteAssignmentLine metLine : metaboliteAssignmentLines){

                //Test if we have the database identifier and description, only process rows that have ids
                if (!utils.processLine(metLine))
                    continue;

                SmallMolecule molecule = mzTabSmallMolecule.convertToMzTab(metLine);
                molecule.setUnitId(accessionNumber);
                mzTab.addSmallMolecule(molecule);

            }

            mzTabFileWriter.writeMzTab(mzTabFile, mzTab.toMzTab());

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
            System.out.println("ERROR:  1st parameter must be the existing MetaboLights MAF (Metabolite Assignment File): " + args[0]);
            System.out.println("----");
            return false;
        }

        if (args[1] == null){
            System.out.println("ERROR: 2nd parameter must be the name for the new mzTab file.");
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
