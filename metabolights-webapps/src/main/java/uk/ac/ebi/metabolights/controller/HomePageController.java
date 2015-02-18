/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2/8/13 2:43 PM
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

package uk.ac.ebi.metabolights.controller;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.ModelObjectFactory;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.MetaboLightsParametersService;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.utils.StringUtils;
import uk.ac.ebi.metabolights.webapp.GalleryItem;
import uk.ac.ebi.metabolights.webapp.GalleryItem.GalleryItemType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller for Metabolights searching.  
 * @author pconesa
 */
@Controller
public class HomePageController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(HomePageController.class);
	
	@Autowired
	private SearchService searchService;

    @Autowired
    private MetaboLightsParametersService metaboLightsParametersService;

    private Date galleryAge;
	private String galleryItemsIds = null;
    private String galleryParam = "GALLERY_ITEMS";
	private List<GalleryItem> gallery;
	

    /**
     * Controller for a browse (empty search) request
     */
    @RequestMapping(value = "/index")
    public ModelAndView homePage(@RequestParam(required = false, value = "message") String message) {
	    
	    // Instantiate the model and view
	    ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("index", "message", message);
	    
	    //Add the gallery Items
	    mav.addObject("gallery", getGalleryItems());
	
	    return mav;
    }
    
    private List<GalleryItem> getGalleryItems(){


        if (gallery != null){
            // If it's less than 1 day old...
            if (DateUtils.isSameDay(galleryAge, new Date())) {
                return gallery;
            }

        }
    	
    	// Instantiate the gallery.
    	gallery = new ArrayList<GalleryItem>();
        galleryAge = new Date();
    	
    	// Get the gallery ids
    	String galleryItemsIdsS = getGalleryItemsIds();
    	
    	// Split the studies and compounds...
    	String[] galleryItemsIds = galleryItemsIdsS.split(",");

    	// For each Item....
    	for (String item: galleryItemsIds){
    	
    		GalleryItem gi = null;
    		
    		// If its a compound (Starts with MTBLC)
    		if (item.indexOf("MTBLC") ==0){
    			
    			try {

                    // ... it is a compound..
                    Compound comp = ModelObjectFactory.getCompound(item);

                    // Convert it into a gallery item....
                    gi = new GalleryItem( comp.getChebiEntity().getChebiAsciiName() + " - " + item,comp.getChebiEntity().getDefinition() , item, "http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=" + comp.getChebiEntity().getChebiId(),GalleryItemType.Compound);
                } catch (Exception e){
                  // log the error..and move on
                    logger.error(e.getMessage());
                }
    			
    		} else {

    			LuceneSearchResult study = searchService.getStudy(item);
    			
    			if (study != null){
    				
    				String description = study.getTitle();

                    String title = item;
                    if (!study.getAffiliations().isEmpty()){

                        title = title.concat(" from ");

                        for (String affiliation: study.getAffiliations()){
                            // Will the first affiliation be enough? Let's see..
                            title = title.concat(affiliation + ", ");
                        }

                        // Truncate the last 2 characters ", "
                        title = StringUtils.truncate(title,2);

                    }

    				// Convert it into a gallery item....
    				gi = new GalleryItem(title, description, item, null,GalleryItemType.Study);
    			}

    		}
    		
    		// If we have got anything...
    		if (gi != null){
    			// Add it to the gallery collection.
    			gallery.add(gi);
    		}
    	}
    	
    	return gallery;
    }
    
    public String getGalleryItemsIds(){

        // Try to get the ids from the DB if null
        if (galleryItemsIds == null || galleryItemsIds.isEmpty()) {
            MetaboLightsParameters metaboLightsParameters = metaboLightsParametersService.getMetaboLightsParametersOnName(galleryParam);
            galleryItemsIds = metaboLightsParameters.getParameterValue();
        }

        logger.info("Gallery Items: "+ galleryItemsIds);

        if (galleryItemsIds == null || galleryItemsIds.isEmpty()){
            galleryItemsIds = "MTBLS1,MTBLC16159,MTBLS3,MTBLC22660,MTBLS4,MTBLC28300,MTBLS2"; //just in case there is nothing in the table
            logger.warn("Could not find any Gallery Items in the database, using default set: "+ galleryItemsIds);
        }
    	return galleryItemsIds;
    }

    //Temporaily override the gallery id's, just for this session so a tomcat restart will set back to table defaults
    @RequestMapping(value = "/setgalleryItems")
    public ModelAndView setNewGallery(@RequestParam(required = true, value = "galleryitems") String newGaleryItems) {
	    
    	gallery = null;
    	galleryItemsIds = newGaleryItems;


    	// redirect to index
	    ModelAndView mav = new ModelAndView("redirect:index?message=New gallery items set: " + newGaleryItems);

	    return mav;
    }
}

