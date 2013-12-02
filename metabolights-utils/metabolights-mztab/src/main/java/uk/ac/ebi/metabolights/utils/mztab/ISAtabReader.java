/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 27/11/13 15:01
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.utils.mztab;

import uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Contact;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ISAtabReader {

    private StudyDAO studyDAO;

    public StudyDAO getStudyDAO(String configFolder, String studyFolder) {

        if (studyDAO == null)
            studyDAO = new StudyDAO(configFolder,studyFolder, "");
        return studyDAO;
    }

    public Study getMLStudy(String configFolder, String studyFolder, String studyAccession){
        if (studyDAO == null)
            studyDAO = getStudyDAO(configFolder,studyFolder);

        Study mlStudy =  studyDAO.getStudy(studyAccession, true); //include any metabolite rows, this is after all what we are converting ;-)

        return mlStudy;

    }

    public List<Assay> getMLAssays(Study study){
        return study.getAssays();
    }

    public List<String> getMetaboliteIdFiles(Study mlStudy){

        if (mlStudy == null)
            return null;

        List<String> mafs = new ArrayList<String>();
        List<Assay> mlAssays = getMLAssays(mlStudy);

        for (Assay assay: mlAssays) {
            String mafName = null;
            MetaboliteAssignment metaboliteAssignment = assay.getMetaboliteAssignment();
            mafName = metaboliteAssignment.getMetaboliteAssignmentFileName();

            if (mafName != null)
                mafs.add(mafName);

        }

        return mafs;

    }

    public List<Contact> getContactsFromStudy(Study study){

        Collection<Contact> contacts = new ArrayList<Contact>();

        if (study != null)
            contacts = study.getContacts();

        return new ArrayList<Contact>(contacts);
    }


}
