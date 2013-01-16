package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.referencelayer.model.ModelObjectFactory;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.webapp.GalleryItem;
import uk.ac.ebi.metabolights.webapp.GalleryItem.GalleryItemType;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Metabolights searching.  
 * @author pconesa
 */
@Controller
public class HomePageController extends AbstractController{
	
	private static Logger logger = Logger.getLogger(HomePageController.class);
	
	@Autowired
	private SearchService searchService;
	
	private static String galleryItemsIds = "MTBLS1,MTBLC16159,MTBLS3,MTBLC22660,MTBLS4,MTBLC28300";
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

    	if (gallery != null) return gallery;
    	
    	// Instantiate the gallery.
    	gallery = new ArrayList<GalleryItem>();
    	
    	// Get the gallery ids
    	String galleryItemsIdsS = getGalleryItemsIds();
    	
    	// Split the studies and compounds...
    	String[] galleryItemsIds = galleryItemsIdsS.split(",");

    	// For each Item....
    	for (String item: galleryItemsIds){
    	
    		GalleryItem gi = null;
    		
    		// If its a compound (Starts with MTBLC)
    		if (item.indexOf("MTBLC") ==0){
    			
    			// ... it is a compound..
    			Compound comp = ModelObjectFactory.getCompound(item);
    			
    			// Convert it into a gallery item....
    			gi = new GalleryItem( comp.getChebiEntity().getChebiAsciiName() + " - " + item,comp.getChebiEntity().getDefinition() , item, "http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=" + comp.getChebiEntity().getChebiId(),GalleryItemType.Compound);
    			
    		} else {

    			LuceneSearchResult study = searchService.getStudy(item);
    			
    			if (study != null){
    				
    				String description = study.getTitle();
    				String title = item + " - " + study.getSubmitter().getName();
    				
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
    
    public static String getGalleryItemsIds(){
    	return galleryItemsIds;
    }
    
    @RequestMapping(value = "/setgalleryItems")
    public ModelAndView setNewGallery(@RequestParam(required = true, value = "galleryitems") String newGaleryItems) {
	    
    	gallery = null;
    	galleryItemsIds =newGaleryItems;
    	
    	
    	// redirect to index
	    ModelAndView mav = new ModelAndView("redirect:index?message=New gallery items set: " + newGaleryItems);
	    
	    return mav;
    }
}

