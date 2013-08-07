package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;
import uk.ac.ebi.metabolights.service.MetaboLightsParametersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class Proxy extends AbstractController{

    @Autowired
    MetaboLightsParametersService metaboLightsparametersService;


    private static Logger logger = Logger.getLogger(Proxy.class);

    @RequestMapping({"/proxy"})
	public ModelAndView getUrl(@RequestParam("url") String sUrl){


        // If the url is not allowed...
        if (!IsUrlAllowed(sUrl)) {

            logger.info("Proxy url not allowed: " + sUrl);
            return null;
        }

        StringBuffer sbf = new StringBuffer();
    
        //Access the page
        try {
                URL url = new URL(sUrl);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine;
                while ( (inputLine = in.readLine()) != null) sbf.append(inputLine);
                in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
         
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("response",sbf);//.toString();
        
        return mav;
        
     
    }

    private boolean IsUrlAllowed(String url){

        // So far we will allow only load balanced servers
        MetaboLightsParameters instancesParam = metaboLightsparametersService.getMetaboLightsParametersOnName("instances");


        String[] instances = instancesParam.getParameterValue().split(ManagerController.INSTANCES_SEP);
        for (String instance : instances){
            if (url.indexOf(instance)!= -1){
                return true;
            }
        }

        return false;
    }
 }