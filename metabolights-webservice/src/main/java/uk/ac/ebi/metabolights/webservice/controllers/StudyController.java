package uk.ac.ebi.metabolights.webservice.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO;
import uk.ac.ebi.metabolights.repository.model.Study;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FilenameFilter;

@Controller
@RequestMapping("study")
public class StudyController {

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";

    // Properties from context
    private @Value("#{publicStudiesLocation}") String publicStudiesLocationProp;
    private @Value("#{privateStudiesLocation}") String privateStudiesLocationProp;
    private @Value("#{isatabConfigurationLocation}") String isatabConfigurationLocation;

    // Folders with the studies...
    private static File publicStudiesLocation;
    private static File privateStudiesLocation;


    @PostConstruct
    public void initialize(){

        if (publicStudiesLocation == null) {
            publicStudiesLocation = new File(publicStudiesLocationProp);
            privateStudiesLocation = new File(privateStudiesLocationProp);
        }
    }

    @RequestMapping("{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}")
    @ResponseBody
    public Study getById(@PathVariable("metabolightsId") String metabolightsId) {

          return  lookForInvestigation(metabolightsId);

    }

    private Study lookForInvestigation(String metabolightsId){

        // Try public studies location
        File studyFolder = getInvestigationFolder(metabolightsId, publicStudiesLocation);

        // If we got something...
        if (studyFolder != null) {

            StudyDAO invDAO = new StudyDAO(isatabConfigurationLocation);

            return  invDAO.getInvestigation(studyFolder.getAbsolutePath());

        } else {
            return null;
        }



    }


    private File getInvestigationFolder(final String metabolightsId, File location){

        File[] files = location.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.equals(metabolightsId);
            }
        });

        if (files != null && files.length == 1 ){
            return  files[0];
        } else {
            return null;
        }


    }

}
