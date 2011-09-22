package uk.ac.ebi.metabolights.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  Data structure to hold information for the accession number
 *  Just a structure to be populated by hibernate
 *  Properties:
 *  Id: integer
 *  Prefix: String
 * @author conesa
 *
 */
@Entity
@Table(name = "STABLE_ID")

public class StableId {
    @Id
	@Column(name="ID")
	private Integer id;

	@Column(name="SEQ")
    @NotEmpty
	private Integer seq;

    @Column(name="PREFIX")
    @NotEmpty
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
