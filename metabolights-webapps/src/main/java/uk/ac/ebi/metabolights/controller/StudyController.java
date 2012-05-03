/*
 * EBI MetaboLights Project - 2012.
 *
 * File: StudyController.java
 *
 * Last modified: 4/5/12 2:12 PM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.bioinvindex.model.security.User;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.StudyService;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 05/04/2012
 * Time: 14:12
 */

@Controller
public class StudyController extends AbstractController {

    private static Logger logger = Logger.getLogger(StudyController.class);

    @Autowired
    private StudyService studyService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UpdateStudyController updateStudyController;


    @RequestMapping(value = { "/checkpublic"})
    public ModelAndView findStudiesToGoPublic(){
        List<Study> studyList = studyService.findStudiesToGoPublic();
        String studiesToUpdate = "", studyMessage = "";
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        Iterator iterator =  studyList.iterator();
        while (iterator.hasNext()){
            if (studiesToUpdate != "")
                studiesToUpdate = studiesToUpdate + ", ";

            Study study = (Study) iterator.next();

            if (now.after(study.getReleaseDate())) {  //Are we after the public release date?

                if (study.getUsers() != null){
                    for (User user: study.getUsers()){    //Find the users to email (username and email the same)
                        logger.info(study.getAcc()+ " study has a public release date of " +study.getReleaseDate().toString() + " now it's " +now.toString());
                        emailService.sendStudyToGoPublicMessage(user.getUserName(), study.getAcc(), study.getReleaseDate().toString(), false);

                        try {
                            updateStudyController.updatePublicReleaseDate(study.getAcc(), VisibilityStatus.PUBLIC, study.getReleaseDate(), user.getId()); //Update the study in the database and the lucene index
                            emailService.sendStudyToGoPublicMessage(user.getUserName(), study.getAcc(), study.getReleaseDate().toString(), true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new ModelAndView("index", "message", e.getMessage());
                        }

                    }

                }

            }

            studiesToUpdate = studiesToUpdate + study.getAcc() + "("+study.getReleaseDate()+")";
        }

        if (studiesToUpdate != null){
            studyMessage = "The following studies will go public soon:  "+studiesToUpdate;
        } else {
            studyMessage = "No studies going live any time soon.....";
        }


        return new ModelAndView("index", "message", studyMessage);

    }

}
