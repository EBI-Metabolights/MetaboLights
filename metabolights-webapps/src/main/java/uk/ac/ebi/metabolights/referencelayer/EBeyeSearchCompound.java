/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 06/09/13 21:27
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer;

public class EBeyeSearchCompound {

	private String Name;
    private String chebiId;
    private String ChebiURL;
    private String accession;
    private String description;
    private String submitter;
    private String last_modification_date;

    private String hasLiterature;
    private String hasReactions;
    private String hasSpecies;
    private String hasPathways;
    private String hasNMR;
    private String hasMS;
    private String studyStatus;

	private String[] iupac;
	private String[] MTBLStudies;
	private String[] domains;
    private String[] technology_type;
    private String[] organism;
    private String[] study_design;
    private String[] study_factor;

    public String getHasLiterature() {
        return hasLiterature;
    }

    public void setHasLiterature(String hasLiterature) {
        this.hasLiterature = hasLiterature;
    }

    public String getHasReactions() {
        return hasReactions;
    }

    public void setHasReactions(String hasReactions) {
        this.hasReactions = hasReactions;
    }

    public String getHasSpecies() {
        return hasSpecies;
    }

    public void setHasSpecies(String hasSpecies) {
        this.hasSpecies = hasSpecies;
    }

    public String getHasPathways() {
        return hasPathways;
    }

    public void setHasPathways(String hasPathways) {
        this.hasPathways = hasPathways;
    }

    public String getHasNMR() {
        return hasNMR;
    }

    public void setHasNMR(String hasNMR) {
        this.hasNMR = hasNMR;
    }

    public String getHasMS() {
        return hasMS;
    }

    public void setHasMS(String hasMS) {
        this.hasMS = hasMS;
    }

    public String getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(String studyStatus) {
        this.studyStatus = studyStatus;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String[] getStudy_design() {
        return study_design;
    }

    public void setStudy_design(String[] study_design) {
        this.study_design = study_design;
    }

    public String[] getStudy_factor() {
        return study_factor;
    }

    public void setStudy_factor(String[] study_factor) {
        this.study_factor = study_factor;
    }

    public String getLast_modification_date() {
        return last_modification_date;
    }

    public void setLast_modification_date(String last_modification_date) {
        this.last_modification_date = last_modification_date;
    }

    public String[] getTechnology_type() {
        return technology_type;
    }

    public void setTechnology_type(String[] technology_type) {
        this.technology_type = technology_type;
    }

    public String[] getOrganism() {
        return organism;
    }

    public void setOrganism(String[] organism) {
        this.organism = organism;
    }

    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
