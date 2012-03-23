package uk.ac.ebi.metabolights.model;

import uk.ac.ebi.bioinvindex.model.Metabolite;
import uk.ac.ebi.metabolights.webapp.MetaboLightsUtilities;

public class MetaboliteGUI {
	private Metabolite met;
	public MetaboliteGUI(Metabolite met) {
		this.met = met;
	}
	public Metabolite getMetabolite(){
		return this.met;
	}
	public String getLink(){
		return MetaboLightsUtilities.getLink(met.getIdentifier());
	}
}
