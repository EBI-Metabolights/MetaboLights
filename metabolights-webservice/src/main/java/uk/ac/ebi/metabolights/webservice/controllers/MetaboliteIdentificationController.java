/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 27/09/13 08:59
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

@Controller
@RequestMapping("maf")
public class MetaboliteIdentificationController {

    public static final String MAF_REG_EXP = "(?:__|_).+";         //File path (File.separator) is replaced with "__"
    @RequestMapping("{maf:" + MAF_REG_EXP +"}")
    @ResponseBody
    public MetaboliteAssignment getMetabolites(@PathVariable("maf") String mafPath){
        MzTabDAO mzTabDAO = new MzTabDAO();
        return mzTabDAO.mapMetaboliteAssignmentFile(mafPath);
    }

}
