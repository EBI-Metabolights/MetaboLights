package uk.ac.ebi.bioinvindex.utils;

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

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The internationalization and user messaging API.
 * 
 * It's a simple wrapper that loads a conventionally named resource bundle (user_message.properties) and 
 * does the job of translating symbolic constants to formatted messages. It also supports parameterized
 * messages, via {@link MessageFormat}.
 *  
 * We don't actually use this class for localization purposes (we support only English), however it should
 * be able to automatically switch to a file like user_messages_IT.properties.
 * 
 * The user_message.properties file is loaded by {@link ResourceBundle#getBundle(String)}, so it should be somewhere
 * like the .jar's root. The class is designed for having one such file per .jar.   
 * 
 * @author brandizi
 * <b>date</b>: May 1, 2009
 *
 */
public class i18n
{
	private static final String BUNDLE_NAME = "user_messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle ( BUNDLE_NAME );
	
	private i18n () {
	}
	
	
	/**
	 * Gets the message pattern corresponding to the key and apply it to the other arguments. 
	 * The pattern is specified in user_messages.properties and is passed to the {@link MessageFormat} 
	 * constructor (i.e.: see there for the syntax). See the test package for usage examples. 
	 *   
	 */
	public static String msg ( String key, Object... args ) 
	{
		
		try 
		{
			// TODO: Speed might be a concern here, we should cache all the MessageFormat(s) 
			// in an map indexed by the keys.
			// 
			String pattern = RESOURCE_BUNDLE.getString ( key );
			MessageFormat fmt = new MessageFormat ( pattern );
			return fmt.format ( args );
		} catch ( MissingResourceException e ) {
			return "__INVALID_MSG_KEY: '" + key + "'__";
		}
	}
}
