package uk.ac.ebi.bioinvindex.utils.processing;

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

import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Data;
import uk.ac.ebi.bioinvindex.model.Material;
import uk.ac.ebi.bioinvindex.model.processing.DataNode;
import uk.ac.ebi.bioinvindex.model.processing.GraphElement;
import uk.ac.ebi.bioinvindex.model.processing.MaterialNode;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Used by {@link AssayResult#findPipelineFactorValues()}, provides the visit action that collects the 
 * {@link FactorValue} in an experimental pipeline. 
 * 
 * @author brandizi
 * <b>date</b>: Nov 24, 2009
 *
 */
public class FactorValueVisitor implements ProcessingVisitAction
{
	
	private Set<FactorValue> pipelineFVs = new HashSet<FactorValue> ();
	
	public boolean visit ( GraphElement graphel ) 
	{
		Collection<FactorValue> fvs;
		if ( graphel instanceof MaterialNode ) 
		{
			Material material = ( (MaterialNode) graphel ).getMaterial ();
			if ( material == null ) return true;
			fvs = material.getFactorValues ();
		}
		else if ( graphel instanceof DataNode ) {
			Data data = ( (DataNode) graphel ).getData ();
			if ( data == null ) return true;
			fvs = data.getFactorValues ();
		}
		else
			return true;
		
		if ( fvs != null && fvs.size () > 0 )
			pipelineFVs.addAll ( fvs );
		
		return true;
	}

	
	public Collection<FactorValue> getPipelineFVs () {
		return pipelineFVs;
	}

}
