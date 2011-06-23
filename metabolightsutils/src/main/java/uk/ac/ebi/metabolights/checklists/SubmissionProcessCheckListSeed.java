package uk.ac.ebi.metabolights.checklists;

public enum SubmissionProcessCheckListSeed implements ICheckListItemSeed{
	FILEUPLOAD ("1", "File upload"),
	FILEVALIDATION("2", "File basic validation"),
	FILEUNZIP("3","File unzip"),
	CONTENTVALIDATION("4","Experiment data validation"),
	IDREPLACEMENTS("5","Metabolights Id assignment"),
	FILEPERSISTANCE("6","File persistance"),
	SETPERMISSIONS ("7","Permission and visibility set up")	
	;
	
	private final String key;
	private final String title;
	
	private SubmissionProcessCheckListSeed(String key, String title){
		this.key = key;
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}
}
