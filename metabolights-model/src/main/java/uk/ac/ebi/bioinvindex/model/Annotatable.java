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

import uk.ac.ebi.bioinvindex.model.term.AnnotationType;
import uk.ac.ebi.utils.regex.RegEx;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;

/**
 * A superslass to allow annotations {@see uk.ac.ebi.bioinvindex.model.impl.Annotation} to be added.
 * Almost all key classes are Annotatable, giving the possibility to attach some annotations.
 * @author: Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Feb 12, 2008
 */
@MappedSuperclass
public abstract class Annotatable extends Identifiable
{
	private Collection<Annotation> annotations = new HashSet<Annotation> ();

	//@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OneToMany
	public Collection<Annotation> getAnnotations() {
		return annotations;
	}

	protected void setAnnotations(Collection<Annotation> annotations) {
		this.annotations = annotations;
	}

	public void addAnnotation(Annotation annotation) {
		if (annotation == null) {
			throw new IllegalArgumentException ( "Null value for Annotation is not allowed." );
		}
		getAnnotations().add(annotation);
	}

	public boolean removeAnnotation(Annotation annotation) {
		return annotations.remove(annotation);
	}

	/**
	 * A helper which returns all the values associated to a given type name
	 * @param typeName name of Annotation Type
	 * @return a List for Annotations.
	 */
	@Transient
	public List<String> getAnnotationValues ( String typeName ) 
	{
		List<String> result = new ArrayList<String> (); 
		for ( Annotation ann: getAnnotations () ) {
			AnnotationType type = ann.getType ();
			if ( type != null && typeName.equals ( type.getValue () ) )
				result.add ( ann.getText () );
		}
		return result;
	}

	
	/**
	 * A helper which returns all the annotations associated to a given type name
	 * @param typeName name of Annotation Type
	 * @return a List for Annotations.
	 */
	@Transient
	public List<Annotation> getAnnotation ( String typeName ) 
	{
		List<Annotation> result = new ArrayList<Annotation> (); 
		for ( Annotation ann: getAnnotations () ) {
			AnnotationType type = ann.getType ();
			if ( type != null && typeName.equals ( type.getValue () ) )
				result.add ( ann );
		}
		return result;
	}

	
	
	/**
	 * Returns all the annotation values of annotation which of type string matches the 
	 * regular expression parameter. 
	 */
	public List<Annotation> getAnnotationValuesByRe ( RegEx typeNameRe ) 
	{
		List<Annotation> result = new ArrayList<Annotation> (); 
		for ( Annotation ann: getAnnotations () ) {
			AnnotationType type = ann.getType ();
			if ( type != null && typeNameRe.matches ( type.getValue () ) )
				result.add ( ann );
		}
		return result;
	}

	/** 
	 * Returns the value corresponding to an annotation type, matched via regular expression. 
	 * Result is undefined if more than one value is stored for a given type. 
	 * Returns null if no annotation of the parameter type exists.
	 * 
	 */
	public Annotation getSingleAnnotationValueByRe ( RegEx typeNameRe  ) {
		List<Annotation> values = getAnnotationValuesByRe ( typeNameRe );
		return values.size() == 0 ? null : values.get ( 0 );
	}

	
	/** 
	 * Returns the value corresponding to an annotation type. Result is undefined if more than one value is stored 
	 * for a given type. Returns null if no annotation of the parameter type exists.
	 * 
	 */
	public String getSingleAnnotationValue ( String typeName ) {
		List<String> values = getAnnotationValues ( typeName );
		return values.size() == 0 ? null : values.get ( 0 );
	}

	/** 
	 * Returns the value corresponding to an annotation type. Result is undefined if more than one value is stored 
	 * for a given type. Returns null if no annotation of the parameter type exists.
	 * 
	 */
	public Annotation getSingleAnnotation ( String typeName ) {
		List<Annotation> values = getAnnotation ( typeName );
		return values.size() == 0 ? null : values.get ( 0 );
	}
	
	
	
	public boolean equals(Object o) 
	{
		if (this == o) return true;
		if (!(o instanceof Annotatable)) return false;

		Annotatable that = (Annotatable) o;

		Annotation[] annotationsArr= annotations.toArray(new Annotation[annotations.size()]);
		Annotation[] thatArr = that.annotations.toArray(new Annotation[that.annotations.size()]);
		if(!Arrays.deepEquals(annotationsArr, thatArr)) return false;

		return true;
	}

	public int hashCode() {
		Annotation[] annotationsArr= annotations.toArray(new Annotation[annotations.size()]);
		return Arrays.deepHashCode(annotationsArr);
	}
}
