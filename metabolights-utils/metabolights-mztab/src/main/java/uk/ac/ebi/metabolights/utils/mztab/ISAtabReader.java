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

import uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
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

    public Study getMLStudy(String configFolder, String studyFolder, String studyAccession) throws IsaTabException, DAOException {
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
