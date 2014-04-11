package uk.ac.ebi.metabolights.species.model;

/**
 * User: conesa
 * Date: 29/10/2013
 * Time: 12:49
 */
public class Taxon {

	public static final String DEFAULT_SEPATATOR = ":";

	String name;
	String commonName;
	String parentId;
	String prefix;
	String recordIdentifier;
	String idSeparator = DEFAULT_SEPATATOR;

	public Taxon(String id, String name, String commonName, String parentId) {
		splitId(id);
		this.name = name;
		this.commonName = commonName;
		this.parentId = parentId;
	}

	public String getId() {

		return composeId();
	}

	public void setId(String id) {
		splitId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	private void splitId(String id) {

		String[] fragments = id.split(idSeparator);

		if (fragments.length>1){
			prefix = fragments[0];
			recordIdentifier = fragments[1];
		} else {
			prefix = "";
			recordIdentifier = fragments[0];
		}

	}
	private String composeId(){

		if (prefix.equals("")){
			return recordIdentifier;
		} else {
			return prefix + idSeparator + recordIdentifier;
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getRecordIdentifier() {
		return recordIdentifier;
	}

	public void setRecordIdentifier(String recordIdentifier) {
		this.recordIdentifier = recordIdentifier;
	}

	public String getIdSeparator() {
		return idSeparator;
	}

	public void setIdSeparator(String idSeparator) {
		this.idSeparator = idSeparator;
	}

	@Override
	public boolean equals(Object obj){

		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Taxon))
			return false;

		Taxon attribute = (Taxon)obj;

		return attribute.getId().equals(this.getId());

	}

	@Override
	public String toString (){
		return name + "' " + composeId();
	}
}
