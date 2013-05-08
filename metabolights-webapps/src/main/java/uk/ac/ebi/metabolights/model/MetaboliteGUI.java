package uk.ac.ebi.metabolights.model;

import uk.ac.ebi.bioinvindex.model.Metabolite;
import uk.ac.ebi.metabolights.webapp.MetaboLightsUtilities;

import java.util.Collection;
import java.util.Map;

public class MetaboliteGUI {
	private Metabolite met;

	public MetaboliteGUI(Metabolite met) {
		this.met = met;
	}
	public Metabolite getMetabolite(){
		return this.met;
	}
	public String getLink(){
		
		String link = met.getChebiId()==null?met.getIdentifier():met.getChebiId();
		return MetaboLightsUtilities.getLink(link);
	}
    public String getIdentifier() {
        if (met.getChebiId() == null){
            return met.getIdentifier();
        } else {
            return met.getChebiId();
        }
    }
}
