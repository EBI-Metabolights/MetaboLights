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
    private @Value("#{isatabConfigurationLocation}") String isatabConfigurationLocation;

    @RequestMapping("{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}")
    @ResponseBody
    public Study getById(@PathVariable("metabolightsId") String metabolightsId) {

            StudyDAO invDAO = new StudyDAO(isatabConfigurationLocation, publicStudiesLocationProp,privateStudiesLocationProp);

            return  invDAO.getStudy(metabolightsId);

    }




}
