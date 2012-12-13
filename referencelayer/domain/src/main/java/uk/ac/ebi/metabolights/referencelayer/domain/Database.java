package uk.ac.ebi.metabolights.referencelayer.domain;

/**
 * 
 * @author conesa
 *
 * Source of any entry (CHEBI, METABOLIGHTS,....)
 */
public class Database {

	private long id;
	private String name;
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
	
}
