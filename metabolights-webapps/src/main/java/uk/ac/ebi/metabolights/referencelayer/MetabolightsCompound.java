//checkout in SVN

package uk.ac.ebi.metabolights.referencelayer;

/**
 * @author The Metabolights Team
 */


public class MetabolightsCompound {
	private String Name;
	private String[] iupac;
	private String chebiId;
	private String ChebiURL;
	private String[] MTBLStudies;
	private String accession;
	private String[] domains;
	

	public String[] getDomains() {
		return domains;
	}
	public void setDomains(String[] domains) {
		this.domains = domains;
	}
	public String getAccession() {
		return accession;
	}
	public void setAccession(String accession) {
		this.accession = accession;
	}
	public String[] getIupac() {
		return iupac;
	}
	public void setIupac(String[] iupac) {
		this.iupac = iupac;
	}
	public String[] getMTBLStudies() {
		return MTBLStudies;
	}
	public void setMTBLStudies(String[] mTBLStudies) {
		MTBLStudies = mTBLStudies;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getChebiId() {
		return chebiId;
	}
	public void setChebiId(String chebiId) {
		this.chebiId = chebiId;
	}
	public String getChebiURL() {
		return ChebiURL;
	}
	public void setChebiURL(String chebiURL) {
		ChebiURL = chebiURL;
	}

}
