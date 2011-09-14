package uk.ac.ebi.metabolights.repository.accessionmanager;
/**
 *  Data structure to hold information for the accession number
 *  Just a structure to be populated by hibernate
 *  Properties:
 *  Id: integer
 *  Prefix: String
 * @author conesa
 *
 */
class StableId {
	private Integer id;
	private Integer seq;
	private String prefix;
	
	/**
	 * Empty constructor
	 */
	public StableId(){}

	public void setId (Integer id){
		this.id = id;
	}
	public Integer getId (){
		return this.id;
	}
	public void setSeq (Integer seq){
		this.seq = seq;
	}
	public Integer getSeq (){
		return this.seq;
	}
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}
	public String getPrefix(){
		return this.prefix;
	}
}
