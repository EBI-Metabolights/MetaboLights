/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/09/13 12:05
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment.MetaboliteAssignmentLine;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;

public class MzTabDAO {

    public MetaboliteAssignment mapMetaboliteAssignmentFile(String fileName) {
        MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();

        metaboliteAssignment.setMetaboliteAssignmentFileName(fileName);
        metaboliteAssignment.setMetaboliteAssignmentLines(getMetaboliteAssignmentLines(fileName));

        return metaboliteAssignment;

    }

    private Collection<MetaboliteAssignmentLine> getMetaboliteAssignmentLines(String fileName){
        Collection<MetaboliteAssignmentLine> metaboliteAssignmentLines = null;
        //TODO, read MAF

        return metaboliteAssignmentLines;

    }

    //This can be used to fine filenames on the filesystem
    private File[] findMAFiles(String folderName){
        File dir = new File(folderName);      //Folder that holds the MAF files

        File[] matches = dir.listFiles(
            new FilenameFilter(){
                public boolean accept(File dir, String name){
                        return name.startsWith("m_") && name.endsWith(".tsv");
                }

            }

        );

        return matches;

    }
}
