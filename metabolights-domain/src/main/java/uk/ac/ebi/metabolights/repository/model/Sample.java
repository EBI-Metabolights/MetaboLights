package uk.ac.ebi.metabolights.repository.model;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 17/09/13
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
public class Sample {
    private String sourceName;
    private String charactersticsOrg;
    private String charactersticsOrgPart;
    private String protocolRef;
    private String sampleName;

    private Collection<Factors> factors;

    public Collection<Factors> getFactors() {
        return factors;
    }

    public void setFactors(Collection<Factors> factors) {
        this.factors = factors;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCharactersticsOrg() {
        return charactersticsOrg;
    }

    public void setCharactersticsOrg(String charactersticsOrg) {
        this.charactersticsOrg = charactersticsOrg;
    }

    public String getCharactersticsOrgPart() {
        return charactersticsOrgPart;
    }

    public void setCharactersticsOrgPart(String charactersticsOrgPart) {
        this.charactersticsOrgPart = charactersticsOrgPart;
    }

    public String getProtocolRef() {
        return protocolRef;
    }

    public void setProtocolRef(String protocolRef) {
        this.protocolRef = protocolRef;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }
}
