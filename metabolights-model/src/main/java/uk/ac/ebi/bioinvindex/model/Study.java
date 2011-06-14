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

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import uk.ac.ebi.bioinvindex.mibbi.MIProject;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.security.User;
import uk.ac.ebi.bioinvindex.model.term.Design;
import uk.ac.ebi.bioinvindex.model.xref.Xref;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge.AssayBridge;
import uk.ac.ebi.bioinvindex.search.hibernatesearch.bridge.ProtocolBridge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.CascadeType;
import javax.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Study is the central unit, containing information on the subject under study, its characteristics and any treatments
 * applied, and it has associated Assays. Study corresponds to an experiment, as defined by ArrayExpress,
 * but it contains more information than an experiment as defined by PRIDE.
 * <p/>
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Jun 21, 2007
 * <p/>
 * TODO: Should extend {@link HasReferences} (at least in principle)
 */

@Entity
@Table(name = "study")
@Indexed(index = "bii")
public class Study extends HasReferences {

	@Field(name
			= "title", index = Index.TOKENIZED, store = Store.YES)
	private String title;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String description;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String objective;

	@IndexedEmbedded(prefix = "design_")
	private List<Design> designs = new ArrayList<Design>();

	private Date submissionDate;

	private Date releaseDate;

	@IndexedEmbedded(prefix = "contact_")
	private Collection<Contact> contacts = new HashSet<Contact>();

	@IndexedEmbedded(prefix = "investigation_")
	private Collection<Investigation> investigations = new HashSet<Investigation>();

	@IndexedEmbedded(prefix = "publication_")
	private Collection<Publication> publications = new HashSet<Publication>();

	@IndexedEmbedded(prefix = "assay_")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = AssayBridge.class)
	private Collection<Assay> assays = new HashSet<Assay>();

	@Field(name = "protocol_", index = Index.TOKENIZED, store = Store.NO)
	@FieldBridge(impl = ProtocolBridge.class)
	private Collection<Protocol> protocols = new ArrayList<Protocol>();

	@IndexedEmbedded(prefix = "assay_result_")
	private Collection<AssayResult> assayResults = new HashSet<AssayResult>();

	@IndexedEmbedded(prefix = "user_")
	private Collection<User> users = new HashSet<User>();

	private Collection<MIProject> miProjects = new HashSet<MIProject>();

	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private VisibilityStatus status = VisibilityStatus.PRIVATE;

	private String obfuscationCode = null;
	
	public Study() {
	}

	public Study(String title) {
		this.title = title;
	}

	@Lob
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	/**
	 * @return the first element of {@link #getDesigns()}.
	 * @deprecated TODO: This is a legacy method and should be removed at some point.
	 */
	@Deprecated
	@Transient
	public Design getDesign() {
		return designs.size() > 0 ? designs.get(0) : null;
	}

	/**
	 * Removes all the designs previously associated to the study and set up the current one
	 *
	 * @deprecated TODO: This is a legacy method and should be removed at some point.
	 */
	@Deprecated
	public void setDesign(Design design) {
		designs.clear();
		addDesign(design);
	}

	@OneToMany(targetEntity = Design.class)
	@JoinColumn(name = "study_id", nullable = true)
	public Collection<Design> getDesigns() {
//		return Collections.unmodifiableCollection ( designs );
		return designs;
	}

	public void addDesign(Design design) {
		if (design == null) {
			throw new IllegalArgumentException("design cannot be null!");
		}
		designs.add(design);
	}

	public boolean removeDesign(Design design) {
		return designs.remove(design);
	}

	protected void setDesigns(List<Design> designs) {
		this.designs = designs;
	}


	@Temporal(TemporalType.DATE)
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@OneToMany(targetEntity = Assay.class,
			mappedBy = "study")
	public Collection<Assay> getAssays() {
		return assays;
	}

	/**
	 * Symmetrically fixes the study in the assay
	 */
	public void addAssay(Assay assay) {
		if (assay == null) {
			throw new IllegalArgumentException("assay cannot be null!");
		}

		if (assay.getStudy() != this) {
			assay.setStudy(this);
		}

		assays.add(assay);
	}

	public boolean removeAssay(Assay assay) {
		if (assay == null) return false;
		if (assay.getStudy() != this) return false;
		assays.remove(assay);
		assay.setStudy(null);
		return true;
	}

	protected void setAssays(Collection<Assay> assays) {
		this.assays = assays;
	}

	@OneToMany(targetEntity = Contact.class, mappedBy = "study")
	public Collection<Contact> getContacts() {
		return contacts;
	}

	protected void setContacts(Collection<Contact> contacts) {
		this.contacts = contacts;
	}

	public void addContact(Contact contact) {
		contacts.add(contact);
	}

	public boolean removeContact(Contact contact) {
		return contacts.remove(contact);
	}


	@OneToMany(targetEntity = Publication.class, mappedBy = "study")
	public Collection<Publication> getPublications() {
		return this.publications;
	}

	protected void setPublications(Collection<Publication> publications) {
		this.publications = publications;
	}

	/**
	 * Symmetrically fixes the study in the publication
	 */
	public void addPublication(Publication publication) {
		if (publication == null) {
			throw new IllegalArgumentException("publication cannot be null!");
		}
		if (!publications.contains(publication)) {
			publications.add(publication);
		}
		publication.setStudy(this);
	}

	/**
	 * Symmetrically fixes the study in the publication
	 */
	public boolean removePublication(Publication publication) {
		if (publication == null) return false;
		if (publication.getStudy() != this) return false;
		publications.remove(publication);
		publication.setStudy(this);
		return true;
	}


	@ManyToMany ( targetEntity = Investigation.class )
	@PrimaryKeyJoinColumns ( { 
		@PrimaryKeyJoinColumn ( name = "STUDY_ID", referencedColumnName = "ID" ), 
		@PrimaryKeyJoinColumn ( name = "INVESTIGATION_ID", referencedColumnName = "ID" ) 
	})
	@JoinTable(
		name = "STUDY2INVESTIGATION",
		joinColumns = {@JoinColumn(name = "STUDY_ID")},
		inverseJoinColumns = @JoinColumn(name = "INVESTIGATION_ID"),
		uniqueConstraints = { @UniqueConstraint ( columnNames = { "STUDY_ID", "INVESTIGATION_ID" } )}
	)
	public Collection<Investigation> getInvestigations() {
		return investigations;
	}

	protected void setInvestigations(Collection<Investigation> investigations) {
		this.investigations = investigations;
	}

	/** 
	 * A facility that returns the first investigation in {@link #getInvestigations()} or null 
	 * if the collection is empty.
	 * 
	 */
	@Transient
	public Investigation getUniqueInvestigation () {
		Iterator<Investigation> invItr = investigations.iterator ();
		return invItr.hasNext () ? invItr.next () : null;
	}
	
	public void addInvestigation(Investigation investigation) {
		this.investigations.add(investigation);
		if (!investigation.getStudies().contains(this)) {
			investigation.addStudy(this);
		}
	}

	public void removeInvestigation(Investigation investigation) {
		this.investigations.remove(investigation);
		if (investigation.getStudies().contains(this)) {
			investigation.removeStudy(this);
		}
	}
	
	
	
	@ManyToMany ( targetEntity = Protocol.class )
	@PrimaryKeyJoinColumns ( { 
		@PrimaryKeyJoinColumn ( name = "STUDY_ID", referencedColumnName = "ID" ), 
		@PrimaryKeyJoinColumn ( name = "PROTOCOL_ID", referencedColumnName = "ID" ) 
	})
	@JoinTable(
		name = "Study2Protocol",
		joinColumns = {@JoinColumn(name = "STUDY_ID")},
		inverseJoinColumns = @JoinColumn(name = "PROTOCOL_ID"),
		uniqueConstraints = { @UniqueConstraint ( columnNames = { "STUDY_ID", "PROTOCOL_ID" } )}
	)
	public Collection<Protocol> getProtocols() {
		return this.protocols;
	}

	public void addProtocol(Protocol protocol) {
		this.protocols.add(protocol);
	}

	public boolean removeProtocol(Protocol protocol) {
		return protocols.remove(protocol);
	}

	protected void setProtocols(Collection<Protocol> protocols) {
		this.protocols = protocols;
	}

	@OneToMany(targetEntity = AssayResult.class, mappedBy = "study")
	public Collection<AssayResult> getAssayResults() {
		return Collections.unmodifiableCollection(assayResults);
	}

	public void addAssayResult(AssayResult assayResult) {
		if (assayResult == null) {
			throw new IllegalArgumentException("assayResult cannot be null!");
		}

		if (assayResult.getStudy() != this) {
			assayResult.setStudy(this);
		}

		assayResults.add(assayResult);
	}

	public boolean removeAssayResult(AssayResult assayResult) {
		if (assayResult == null) return false;
		if (assayResult.getStudy() != this) return false;
		assayResults.remove(assayResult);
		assayResult.setStudy(null);
		return true;
	}

	protected void setAssayResults(Collection<AssayResult> assayResults) {
		this.assayResults = assayResults;
	}

	
//TODO: Remove
//@ManyToMany(targetEntity = User.class, cascade = CascadeType.PERSIST )
//@JoinTable(
//		name = "Study2User",
//		joinColumns = {@JoinColumn(name = "STUDY_ID")},
//		inverseJoinColumns = @JoinColumn(name = "USER_ID")
//)
	@ManyToMany (targetEntity = User.class, cascade = CascadeType.PERSIST )
	@PrimaryKeyJoinColumns ( { 
		@PrimaryKeyJoinColumn ( name = "STUDY_ID", referencedColumnName = "ID" ), 
		@PrimaryKeyJoinColumn ( name = "USER_ID", referencedColumnName = "ID" ) 
	})
	@JoinTable(
		name = "Study2User",
		joinColumns = {@JoinColumn(name = "STUDY_ID")},
		inverseJoinColumns = @JoinColumn(name = "USER_ID"),
		uniqueConstraints = { @UniqueConstraint ( columnNames = { "STUDY_ID", "USER_ID" } )}
	)
	public Collection<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public boolean removerUser(User user) {
		return this.users.remove(user);
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@ManyToMany(targetEntity = MIProject.class)
	@JoinTable(
			name = "Study2MIProject",
			joinColumns = {@JoinColumn(name = "STUDY_ID")},
			inverseJoinColumns = @JoinColumn(name = "MIProject_ID")
	)
	public Collection<MIProject> getMiProjects() {
		return miProjects;
	}

	public void setMiProjects(Collection<MIProject> miProjects) {
		this.miProjects = miProjects;
	}

	@Override
	@Transient
	@Field(name = "acc", index = Index.UN_TOKENIZED, store = Store.YES)
	public String getAcc() {
		return super.getAcc();
	}

	@OneToMany
	@JoinTable(
			name = "study2xref",
			joinColumns = {@JoinColumn(name = "study_id", nullable = false)},
			inverseJoinColumns = @JoinColumn(name = "xref_id", nullable = false)
	)
	@Override
	public Collection<Xref> getXrefs() {
		return super.getXrefs();
	}

	public VisibilityStatus getStatus() {
		return status;
	}

	public void setStatus(VisibilityStatus status) {
		this.status = status;
	}

	
	
	/**
	 * This a special code that is to be used in file paths of this study-related files. The idea is that, if a file
	 * path contains this code and its upper container hasn't listing permissions, it's not possible to reconstruct
	 * the path itself and therefore it is kept safe from unauthorized accession. 
	 * 
	 * @return a 10 character length string, composed of alphanumeric characters. The value is initially null, the Study
	 * client is supposed to set it properly via the setter. 
	 * 
	 */
	@Column ( length = 10 )
	public String getObfuscationCode () {
		return obfuscationCode;
	}

	public void setObfuscationCode ( String obfuscationCode ) {
		this.obfuscationCode = obfuscationCode;
	}

	@Override
	public String toString() {

		String investigationsStr = "";
		for (Investigation investigation : investigations) {
			investigationsStr += String.format(
					"Investigation{ id = '%s', accession = '%s', title = '%s' }",
					investigation.getId(), investigation.getAcc(), investigation.getTitle()
			);
		}

		String assaysStr = "";

		{
			String separator = "";
			int count = 0;
			for (Assay assay : getAssays()) {
				assaysStr += String.format("%s{#%d, acc: '%s', ep: %s, tech %s, material = %s}",
						separator, assay.getId(), assay.getAcc(), assay.getMeasurement(), assay.getTechnology(),
						assay.getMaterial()
				);
				separator = ", ";
				if (count++ > 10) break;
			}
		}

		String assayResultsStr = "";
		{
			String separator = "";
			int count = 0;
			for (AssayResult assay : getAssayResults()) {
				assaysStr += String.format("%s{#%d, FV: %s, data: %s}",
						separator, assay.getId(), assay.getFactorValues(), assay.getData()
				);
				separator = ", ";
				if (count++ > 10) break;
			}
		}

		return "Study{" +
				"id ='" + getId() + '\'' +
				", acc ='" + getAcc() + '\'' +
				", title='" + title + '\'' +
				"\n description='" + StringUtils.substring(description, 0, 20) + '\'' +
				"\n objective='" + objective + '\'' +
				"\n designs=" + getDesigns() +
				", submissionDate=" + submissionDate +
				", releaseDate=" + releaseDate +
				"\n contacts=" + contacts +
				"\n first assays=[" + assaysStr + "]" +
				"\n assay-results=" + assayResultsStr +
				"\n publications=" + publications +
				"\n protocols=" + protocols +
				"\n\n investigations={\n" + investigationsStr + "\n  }\n}\n" +
				"\n\n users={\n" + users + "\n  }\n}\n" +
				"\n}";
	}


}
