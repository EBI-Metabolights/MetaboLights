/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 26/09/13 15:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.webservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;

@Controller
@RequestMapping("maf")
public class MetaboliteIdentificationController {

    // Properties from context
    private @Value("#{publicStudiesLocation}") String publicStudiesLocationProp;
    private @Value("#{privateStudiesLocation}") String privateStudiesLocationProp;
    private @Value("#{isatabConfigurationLocation}") String isatabRootConfigurationLocation;

    @RequestMapping("/maf")
    @ResponseBody
    public MetaboliteAssignment getMetabolites(@RequestParam(required = false, value = "maf") String mafPath){
        MzTabDAO mzTabDAO = new MzTabDAO();
        return mzTabDAO.mapMetaboliteAssignmentFile(mafPath);
    }

}
