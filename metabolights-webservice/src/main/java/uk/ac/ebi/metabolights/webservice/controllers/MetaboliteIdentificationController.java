/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/5/13 3:24 PM
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

package uk.ac.ebi.metabolights.webservice.controllers;

//import org.slf4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
//import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
//
//import java.io.File;

//@Controller
//@RequestMapping("maf")
public class MetaboliteIdentificationController {

//    private final static Logger logger = Logger.getLogger(MetaboliteIdentificationController.class.getName());
//
//    public static final String MAF_REG_EXP = "(?:__|_).+";         //File path (File.separator) is replaced with "__"
//
//    @RequestMapping("{maf:" + MAF_REG_EXP +"}")
//    @ResponseBody
//    public MetaboliteAssignment getMetabolites(@PathVariable("maf") String mafPath){
//        MzTabDAO mzTabDAO = new MzTabDAO();
//        MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();
//        logger.info("MAF file given as: "+mafPath);
//
//        String filePath = cleanFilePath(mafPath);      //http:localhost:8080/metabolights/webservice/maf/__nfs__public__rw__homes__tc_cm01__metabolights__dev__studies__stage__public__MTBLS1__m_live_mtbl1_rms_metabolite+profiling_NMR+spectroscopy_v2_maf.tsv
//        if (checkFileExists(filePath)){
//            logger.info("MAF file found, starting to read data from "+filePath);
//            metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(filePath);
//        }  else {
//            logger.error("MAF file " + filePath + " does not exist!");
//            metaboliteAssignment.setMetaboliteAssignmentFileName("ERROR: " + filePath + " does not exist!");
//        }
//
//        return metaboliteAssignment;
//    }
//
//    private String cleanFilePath(String filePath){
//
//        if (filePath.contains("%2F")) //This is the "converted" path
//            filePath = filePath.replaceAll("%2F", File.separator);
//
//        if (filePath.contains("+")) //Plus in the filename
//            filePath = filePath.replaceAll("%2B"," ");
//
//        if (filePath.contains("%20")) //The other way to represent spaces
//            filePath = filePath.replaceAll("%20"," ");
//
//        logger.info("Converted MAF file is: "+filePath);
//        return filePath;
//    }
//
//    private boolean checkFileExists(String filePath){
//        File mafFile = new File(filePath);
//
//        if (mafFile.exists())
//            return true;
//
//        return false;
//
//    }

}
