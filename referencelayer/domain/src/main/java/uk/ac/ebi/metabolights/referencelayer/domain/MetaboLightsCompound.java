package uk.ac.ebi.metabolights.referencelayer.domain;

/**
 * This class represents a MetaboLights reference layer compound.
 * 
 *
 * @author Pablo Conesa
 */
public class MetaboLightsCompound {

// The internal identifier (for persistance)
private long id;
// The public accession number of this compound.
private String accession;
// The name of this compound
private String name;
// The description of this compound
private String description;
// Standard inchi of the compound
private String inchi;
// ChEBI id
private String chebiId;



/**
 * @return the id
 */
public long getId() {
	return id;
}

/**
 * @param id the id to set
 */
public void setId(long id) {
	this.id = id;
}

/**
 * @return the accession
 */
public String getAccession() {
	return accession;
}

/**
 * @param accession the accession to set
 */
public void setAccession(String accession) {
	this.accession = accession;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @return the description
 */
public String getDescription() {
	return description;
}

/**
 * @param description the description to set
 */
public void setDescription(String description) {
	this.description = description;
}

/**
 * @return the inchi
 */
public String getInchi() {
	return inchi;
}

/**
 * @param inchi the inchi to set
 */
public void setInchi(String inchi) {
	this.inchi = inchi;
}
/**
 * @return the chebiId
 */
public String getChebiId() {
	return chebiId;
}

/**
 * @param chebiId the chebiId to set
 */
public void setChebiId(String chebiId) {
	this.chebiId = chebiId;
}

}
