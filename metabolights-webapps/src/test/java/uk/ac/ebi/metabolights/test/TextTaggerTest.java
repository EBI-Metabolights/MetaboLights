/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/5/12 10:35 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ac.ebi.metabolights.service.TextTaggerService;

/**
 * Test text tagging by Whatizit.
 * @author markr
 *
 */
public class TextTaggerTest {

	private ApplicationContext applicationContext;
    private TextTaggerService ttService;
    
	@Before
	public void letsGo() {
		if (applicationContext==null){
			applicationContext =new ClassPathXmlApplicationContext("test.spring.xml");
			ttService =  applicationContext.getBean(TextTaggerService.class);
		}
	}

	/**
	 * Tests highlighting of Chebi terms in a bit of text.
	 */
	@Test
	public void testHighlightedChemicalDict() {
		String wikiGlycerol=
			    "Glycerol (or glycerin, glycerine) is a simple polyol compound. " +
				"It is a colorless, odorless, viscous liquid that is widely used in pharmaceutical formulations. " +
				"Glycerol has three hydrophilic hydroxyl groups that are responsible for its solubility in water and its hygroscopic nature. " +
				"The glycerol backbone is central to all lipids known as triglycerides. " +
				"Glycerol is sweet-tasting and of low toxicity. " +
				"The term polyglycerol refers to many glycerol molecules together.";
		
		String fnorkn=ttService.tagText(wikiGlycerol);
		System.out.println(fnorkn);
		Assert.assertTrue("Contains water accession", fnorkn.contains("15377"));
		Assert.assertTrue("Contains glycerol accession", fnorkn.contains("17754"));
		Assert.assertTrue("Contains lipid accession", fnorkn.contains("18059"));
		Assert.assertTrue("Contains glycerol link", fnorkn.contains("http://www.ebi.ac.uk/chebi/searchId.do?chebiId=CHEBI%3A17754"));
	}

}
