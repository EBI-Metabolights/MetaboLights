package uk.ac.ebi.bioinvindex.model;

/*
 * __________
 * CREDITS
 * __________
 *
 * Team page: http://isatab.sf.net/
 * - Marco Brandizi (software engineer: ISAvalidator, ISAconverter, BII data management utility, BII model)
 * - Eamonn Maguire (software engineer: ISAcreator, ISAcreator configurator, ISAvalidator, ISAconverter,  BII data management utility, BII web)
 * - Nataliya Sklyar (software engineer: BII web application, BII model,  BII data management utility)
 * - Philippe Rocca-Serra (technical coordinator: user requirements and standards compliance for ISA software, ISA-tab format specification, BII model, ISAcreator wizard, ontology)
 * - Susanna-Assunta Sansone (coordinator: ISA infrastructure design, standards compliance, ISA-tab format specification, BII model, funds raising)
 *
 * Contributors:
 * - Manon Delahaye (ISA team trainee:  BII web services)
 * - Richard Evans (ISA team trainee: rISAtab)
 *
 *
 * ______________________
 * Contacts and Feedback:
 * ______________________
 *
 * Project overview: http://isatab.sourceforge.net/
 *
 * To follow general discussion: isatab-devel@list.sourceforge.net
 * To contact the developers: isatools@googlegroups.com
 *
 * To report bugs: http://sourceforge.net/tracker/?group_id=215183&atid=1032649
 * To request enhancements:  http://sourceforge.net/tracker/?group_id=215183&atid=1032652
 *
 *
 * __________
 * License:
 * __________
 *
 * This work is licenced under the Creative Commons Attribution-Share Alike 2.0 UK: England & Wales License. To view a copy of this licence, visit http://creativecommons.org/licenses/by-sa/2.0/uk/ or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California 94105, USA.
 *
 * __________
 * Sponsors
 * __________
 * This work has been funded mainly by the EU Carcinogenomics (http://www.carcinogenomics.eu) [PL 037712] and in part by the
 * EU NuGO [NoE 503630](http://www.nugo.org/everyone) projects and in part by EMBL-EBI.
 */

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jul 10, 2007
 */

/**
 * A suprcalss for all prsistent class in the BII model
 */
@MappedSuperclass
public abstract class Identifiable{

	private Long id;
	private Timestamp submissionTs = null;
		

	protected Identifiable() {
	}

	@Id
	@GeneratedValue(strategy= GenerationType.TABLE)
	@GenericGenerator(name = "hibseq", strategy = "seqhilo")
	@DocumentId
	//@Column ( columnDefinition = "INTEGER" )
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * A reference which identifies the submission with which this object was created. This is used to implement
	 * features such as unloading or exact submission reconstruction. 
	 *
	 * Note: I need columnDefinition because in MYSQL the default is current time and current time on update.
	 * 
	 */
	@Column ( nullable = true, name = "submission_ts", columnDefinition = "TIMESTAMP NULL" )
	public Timestamp getSubmissionTs () {
		return submissionTs;
	}

	public void setSubmissionTs ( Timestamp submissionTs ) {
		this.submissionTs = submissionTs;
	}

	
	
	@Override
	public String toString() {
		return "Identifiable{" +
				"id=" + id +
				'}';
	}


}
