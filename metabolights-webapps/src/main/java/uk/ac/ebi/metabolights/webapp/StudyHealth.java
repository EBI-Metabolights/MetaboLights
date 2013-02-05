package uk.ac.ebi.metabolights.webapp;

import java.io.File;
import java.io.FileNotFoundException;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;

public class StudyHealth {

	static IsaTabUploader itu = new IsaTabUploader(null, null, null,PropertiesUtil.getProperty("publicFtpLocation"), PropertiesUtil.getProperty("privateFtpStageLocation"));

	String identifier;
	boolean isPublic;
	String studyPath;
	boolean isThere;
	
	public StudyHealth(LuceneSearchResult study){
		identifier = study.getAccStudy();
		isPublic = study.getIsPublic();
		
		// Calculate the path where the study files are meant to be.
		studyPath = itu.getStudyFilePath(identifier, (isPublic?VisibilityStatus.PUBLIC:VisibilityStatus.PRIVATE));
		
		// Check if it is there
		isThere = new File(studyPath).exists();
	}

	public String getIdentifier() {
		return identifier;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public String getStudyPath() {
		return studyPath;
	}

	public boolean getIsThere() {
		return isThere;
	}
	
}
