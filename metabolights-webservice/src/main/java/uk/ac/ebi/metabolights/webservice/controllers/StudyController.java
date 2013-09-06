package uk.ac.ebi.metabolights.webservice.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO;
import uk.ac.ebi.metabolights.repository.model.Study;

@Controller
@RequestMapping("study")
public class StudyController {

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";

    // Properties from context
    private @Value("#{publicStudiesLocation}") String publicStudiesLocationProp;
    private @Value("#{privateStudiesLocation}") String privateStudiesLocationProp;
    private @Value("#{isatabConfigurationLocation}") String isatabRootConfigurationLocation;

    @RequestMapping("{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}")
    @ResponseBody
    public Study getById(@PathVariable("metabolightsId") String metabolightsId) {

            StudyDAO invDAO = new StudyDAO(isatabRootConfigurationLocation, publicStudiesLocationProp,privateStudiesLocationProp);


            // Get the study
            Study study = invDAO.getStudy(metabolightsId);

            // If the study is private.
            if (!study.isPublic()){

                // Let's return an empty one, until we implement security..
                study = new Study();
                study.setStudyIdentifier(metabolightsId);
                study.setPublic(false);
                study.setTitle("PRIVATE STUDY");
                study.setDescription("This study is private and its data can't be accessed. Soon we will implement the security layer in the webservice, and the you will be able to get it, once authenticated and checked you are allowed to access the study.");

            }


            return  study;

    }




}
