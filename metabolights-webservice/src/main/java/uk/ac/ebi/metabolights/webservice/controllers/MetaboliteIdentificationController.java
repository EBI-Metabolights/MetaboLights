/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 02/10/13 14:17
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.webservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;

import java.io.File;

@Controller
@RequestMapping("maf")
public class MetaboliteIdentificationController {

    public static final String MAF_REG_EXP = "(?:__|_).+";         //File path (File.separator) is replaced with "__"

    @RequestMapping("{maf:" + MAF_REG_EXP +"}")
    @ResponseBody
    public MetaboliteAssignment getMetabolites(@PathVariable("maf") String mafPath){
        MzTabDAO mzTabDAO = new MzTabDAO();
        MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();

        String filePath = cleanFilePath(mafPath);      //http:localhost:8080/metabolights/webservice/maf/__nfs__public__rw__homes__tc_cm01__metabolights__dev__studies__stage__public__MTBLS1__m_live_mtbl1_rms_metabolite+profiling_NMR+spectroscopy_v2_maf.tsv
        if (checkFileExists(filePath)){
            metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(filePath);
        }  else
            metaboliteAssignment.setMetaboliteAssignmentFileName("ERROR: " + filePath + " does not exist!");


        return metaboliteAssignment;
    }

    private String cleanFilePath(String filePath){

        if (filePath.contains("__")) //This is the "converted" path
            filePath = filePath.replaceAll("__", File.separator);

        if (filePath.contains("+")) //This is to allow spaces in the filename
            filePath = filePath.replaceAll("\\+"," ");

        if (filePath.contains("%20")) //The other way to represent spaces
            filePath = filePath.replaceAll("%20"," ");

        return filePath;
    }

    private boolean checkFileExists(String filePath){
        File mafFile = new File(filePath);

        if (mafFile.exists())
            return true;

        return false;

    }

}
