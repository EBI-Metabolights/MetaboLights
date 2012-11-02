package uk.ac.ebi.metabolights.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.AssayGroup;
import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.security.User;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.bioinvindex.model.term.PropertyValue;
import uk.ac.ebi.metabolights.model.MLAssay;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.service.StudyService;
import uk.ac.ebi.metabolights.service.TextTaggerService;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.Zipper;
//import uk.ac.ebi.metaboligths.referencelayer.model.ModelObjectFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.*;

/**
 * Controller for entry (=study) details.
 * 
 */
@Controller
public class EntryController extends AbstractController {

	private static Logger logger = Logger.getLogger(EntryController.class);
	private final String DESCRIPTION="descr";

	@Autowired
	private StudyService studyService;
	@Autowired
	private SearchService searchService;

    private @Value("#{publicFtpLocation}") String publicFtpDirectory;
    private @Value("#{privateFtpStageLocation}") String privateFtpDirectory;
    private @Value("#{ftpServer}") String ftpServer;
    private @Value("#{filebase}") String filebase;
    private @Value("#{ondemand}") String zipOnDemandLocation;     // To store the zip files requested from the Entry page, both public and private files goes here

	//(value = "/entry/{metabolightsId}")
	@RequestMapping(value = "/{metabolightsId}")
	public ModelAndView showEntry(@PathVariable("metabolightsId") String mtblId, HttpServletRequest request) {
		logger.info("requested entry " + mtblId);

		// If mtblID is a compound
		if (mtblId.startsWith("MTBLC")){ 
			return showMTBLC(mtblId);
		}
		
        Study study = null;

        try {
            request.setCharacterEncoding("UTF-8");
            study = studyService.getBiiStudy(mtblId,true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //TODO, change
        }

        // Is there a study with this name?  Was there an error?  Have you tried to access a PRIVATE study?
        if (study.getAcc() == null || study.getAcc().equals("Error") || study.getAcc().equals(VisibilityStatus.PRIVATE.toString()))
            return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.noStudyFound") + " (" +mtblId + ")");
		
		Collection<String> organismNames = getOrganisms(study);

        // Get the DownloadLink
        String fileLocation = getDownloadLink(study.getAcc(), study.getStatus());

		ModelAndView mav = new ModelAndView("entry");
		mav.addObject("study", study);
		mav.addObject("organismNames", organismNames);
		mav.addObject("factors", getFactorsSummary(study));
		mav.addObject("assays", getMLAssays(study));

        //Have to give the user the download stream as the study is not on the public ftp
        //if (!study.getAcc().equals(VisibilityStatus.PRIVATE.toString()))
            mav.addObject("fileLocation",fileLocation); //All zip files are now generated on the fly, so ftpLocation is really fileLocation
        
        //Stick text for tagging (Whatizit) in the session..                       //TODO, Whatizit is not responding
        //if (study.getDescription()!=null) {
        //	logger.debug("placing study description in session for Ajax highlighting");
        //	request.getSession().setAttribute(DESCRIPTION,study.getDescription());
        //}

		return mav;
	}
	
	private ModelAndView showMTBLC(String accession){
		
		ModelAndView mav = new ModelAndView("compound");
		//mav.addObject("compound", ModelObjectFactory.getCompound(accession));
		
		return mav;
		
	}
	
	

	/**
	 * @param study
	 * @return
	 */
	private Collection<String> getOrganisms(Study study) {

		
		Collection<String> organismNames = new TreeSet<String>();
		
		
		
		for (AssayResult assRes : study.getAssayResults()) {
			for (PropertyValue<?> pv : assRes.getCascadedPropertyValues()) {
				if (pv.getType().getValue().equalsIgnoreCase("organism")) {
					organismNames.add(pv.getValue());

				}
			}
		}
		


		
		return organismNames;
	}

	private Collection<MLAssay> getMLAssays(Study study){
		
		HashMap<String,MLAssay> mlAssays = new HashMap<String, MLAssay>();
		
		for (AssayResult ar : study.getAssayResults()){
			
			MLAssay mlAssay= null;
			
			for (Assay assay: ar.getAssays()){
				
				String assayName = MLAssay.getAssayNameFromAssay(assay);
								
				// If we don't have the MLAssay
				if (!mlAssays.containsKey(assayName)){
					mlAssay = new MLAssay(assay);
					mlAssay.setStudy(study);
					mlAssays.put(assayName, mlAssay);
				} else {
					mlAssay = mlAssays.get(assayName);
				}
				
				
				mlAssay.addAssayLines(assay);
				
			}
			
			// Add the assay line to the assay
			mlAssay.addAssayResult(ar);
			
			if (ar.getAssays().size()!=1) logger.info("*****WARNING:Assays collection's size is different from 1, we expect to have a one-to-one relationship!!!: " + ar.getAssays().size());

		}
		
		// Assign AssayGroup and metabolites
		for (AssayGroup ag: study.getAssayGroups()){
			MLAssay mlAssay = mlAssays.get(ag.getFileName());
		
			if (mlAssay == null){
				logger.info("Oh! Not MLAssay forund for AssayGroup: " + ag.getFileName());
			}else{
				// First set the study
				mlAssay.setAssayGroup(ag);
			}
		}
		
		
		return mlAssays.values();
		
	}

    @RequestMapping(value = "/publicfiles/{file_name}")
    public void getFile(@PathVariable("file_name") String fileName,	HttpServletResponse response) {
        try {
            serveFile(publicFtpDirectory, zipOnDemandLocation, fileName, response);
            response.flushBuffer();

        } catch (Exception e) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
	
	@RequestMapping(value = "/privatefiles/{file_name}")
	public void getFile(@PathVariable("file_name") String fileName,	HttpServletResponse response, Principal principal) {
		try {

			Boolean validUser = false;
            final String currentUser = principal.getName(); //The logged in user.  principal = MetabolightsUser

            //TODO, not very elegant, this is just to determine if the logged in user us a curator
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getPrincipal().equals(new String("anonymousUser"))){
                MetabolightsUser metabolightsUser = (MetabolightsUser) auth.getPrincipal();

                if (metabolightsUser != null && metabolightsUser.isCurator())
                    validUser = true;
            }

			if (currentUser != null) { //Check if the logged in user can access the study
				Study study = studyService.getBiiStudy(fileName,true);
				Collection<User> users = study.getUsers();
				Iterator<User> iter = users.iterator();
				while (iter.hasNext()){
					User user = iter.next();
					if (user.getUserName().equals(currentUser)){
						validUser = true;
						break;
					}
				}
			}

			if (!validUser)
				throw new RuntimeException(PropertyLookup.getMessage("Entry.notAuthorised"));

			try {
                serveFile(privateFtpDirectory, zipOnDemandLocation, fileName, response);

			} catch (Exception e) {
				throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing")); 
			}

			response.flushBuffer();

		} catch (Exception e) {
			throw new RuntimeException("IOError writing file to output stream");
		}

	}


    private void serveFile(String readFromPath, String writeToPath, String fileName, HttpServletResponse response){
        try {
            if (!getZipFile(writeToPath, fileName, response))      // Get the file, stream to the user
                createZipFile(readFromPath, writeToPath, fileName, response);    // If the file is not there, create the file and send to the user
        } catch (Exception e) {
            throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing"));
        }
    }

    //Get the file, stream to browser
    private boolean getZipFile(String path, String fileName, HttpServletResponse response){
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(path + fileName + ".zip");

            // let the browser know it's a zip file
            response.setContentType("application/zip");

            // copy it to response's OutputStream
            IOUtils.copy(is, response.getOutputStream());

            return true;

        } catch (FileNotFoundException e) {
            logger.info("User requested zip file "+ fileName + ".zip not found, will create now!");
            return false;
            //throw new RuntimeException("File not in input stream");
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '"+ fileName + "'");
            throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing"));
        }
    }

    //Create the requested zip file based on the study folder
    private void createZipFile(String fromPath, String outPath, String fileName, HttpServletResponse response) throws IOException {

        String zipFile = outPath + fileName + ".zip";

        if (!FileUtil.fileExists(zipFile))  // Just to be sure that the file *don't already* exist
            Zipper.zip(fromPath + fileName, zipFile);

        if (FileUtil.fileExists(zipFile))  // Just to be sure that the file *now* exist
            getZipFile(outPath, fileName, response);

    }


    @Autowired
    private TextTaggerService textTagger;

    /**
     * Intended for an asynchronous call, to tag the description in a study.
     * @param request
     * @return ugly pixie 
     */
	@RequestMapping(value="/tagText")
	public ModelAndView whatIzItStuff (HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("taggedText");
		String description = (String) request.getSession().getAttribute(DESCRIPTION);
    	if(description!=null) {
			logger.debug("Calling WhatWhatIzIt");
			String taggedContent = textTagger.tagText(description);
			mav.addObject("taggedContent", taggedContent);
			logger.debug(taggedContent);
    	}
    	else 
    		mav.addObject("taggedContent", null);
		return mav;
	}
	
	public String getDownloadLink(String study, VisibilityStatus status){

		String ftpLocation = null;
		
		if (status.equals(VisibilityStatus.PRIVATE)){	// Only for the submitter
			ftpLocation = "privatefiles/" + study;  //Private download, file stream
		} else {  //Serve back public ftp link
            //No longer downloading zip from ftp, ftp only have unzipped folders, so create a zip file and stream a file on demand
            //ftpLocation = publicFtpDirectory.replaceFirst(filebase,"ftp://" + ftpServer + "/")  + study +".zip";     //Remove the filesystem (filebase), replase with ftp address (ftpServer)
            ftpLocation = "publicfiles/" + study;
        }

		return ftpLocation;
		
	}
	
	private HashMap<String,ArrayList<String>> getFactorsSummary(Study study){
		
		HashMap<String, ArrayList<String>> fvSummary = new HashMap<String, ArrayList<String>>();
		
		// Loop through the assay results
		for (AssayResult ar:study.getAssayResults()){
			
			// For each factor value in Data item
			for (FactorValue fv:ar.getData().getFactorValues()){
				
				
				// ... if we don't have the factor already in the collection
				if (!fvSummary.containsKey(fv.getType().getValue())){
					fvSummary.put(fv.getType().getValue(),new ArrayList<String>());
				}
				
				// Get the values for that factor...
				ArrayList<String> values = fvSummary.get(fv.getType().getValue());
				
				
				String value;
				
				// Add the new value
				if (!(fv.getUnit() == null)){
					value = fv.getValue() + " " + fv.getUnit().getValue();
				} else {
					value = fv.getValue();
				}
				
				if (!values.contains(value)) values.add(value);
				
			}
		}
		
		return fvSummary;
		
		
	}
	private void foo(){
		
		
	}

}
