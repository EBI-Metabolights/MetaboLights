package uk.ac.ebi.metabolights.species.model;

/**
 * User: conesa
 * Date: 29/10/2013
 * Time: 12:45
 */
public class Taxonomy {
	String description;
	String id;
	String version;
	String matchingPattern;

	public Taxonomy(String description, String id, String version) {
		this.description = description;
		this.id = id;
		this.version = version;
		/* By default pattern will be id:\d+ (Id + colon + 1 or more digit) */
		this.matchingPattern = id + ":\\d+";
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMatchingPattern() {
		return matchingPattern;
	}

	public void setMatchingPattern(String matchingPattern) {
		this.matchingPattern = matchingPattern;
	}

	public boolean isThisYourTaxon(Taxon taxon){

		return taxon.getId().matches(matchingPattern);

	}
}
