package uk.ac.ebi.metabolights.controller;

import java.io.*;
import java.net.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sshtools.j2ssh.net.HttpResponse;

@Controller
public class Proxy extends AbstractController{
	
    @RequestMapping({"/proxy"})
	public ModelAndView getUrl(@RequestParam("url") String sUrl){
	
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
        
        mav.addObject("response",sbf).toString();
        
        return mav;
        
     
    }
 }