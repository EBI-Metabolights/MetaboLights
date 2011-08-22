package uk.ac.ebi.metabolights.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.ebi.metabolights.dao.UserDAO;

@Service
public class TextTaggerWhatizitImpl implements TextTaggerService {


	private static Logger logger = Logger.getLogger(TextTaggerService.class);
	private final String xsltFileName = "chemicals.xslt";

	//See various pipelines listed at http://www.ebi.ac.uk/webservices/whatizit/info.jsf
	//private final String pipeLine = "whatizitEBIMedDiseaseChemicals"; 
	private final String pipeLine = "whatizitChebiDict"; 
	
	/**
	 * Takes in a text String to be marked up by the WhatIzit pipeline.
	 * <br> 
	 * See further info on: http://www.ebi.ac.uk/Rebholz-srv/whatizit/pipe
	 * XSLT files can be found in EBI directory /ebi/textmining/Web/xslt/
	 * <br>
	 * Class calls a servlet, alternatively you could call the web service. See
	 * http://www.ebi.ac.uk/webservices/whatizit/info.jsf
	 *
	 * @param text : input text (abstract or title)
	 * @param pipeLine : type of analysis to be done by WhatIzIt
	 * @return text with identified keywords/hyperlinks added by WhatIzIt
	 */
	@Override
	public String tagText(String text) {

		String output = text;

		if (TextUtils.isNullOrEmpty(text))
			return output;

		try {
			InputStream ins = this.getClass().getClassLoader().getResourceAsStream(xsltFileName);

			String pipeInputText = 
				pipeLine + "\n" + WhatizitHttpClient.XML_START + "<text>" + StringEscapeUtils.escapeXml(text) + "</text>" + WhatizitHttpClient.XML_END;

			Source streamSource = new StreamSource(ins);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(streamSource);

			logger.info("making Whatizit call");

			WhatizitHttpClient client = new WhatizitHttpClient();
			client.upload(pipeInputText);

			String originalPipeOutputText = client.download();

			ByteArrayInputStream inputStream = new ByteArrayInputStream(originalPipeOutputText.getBytes("UTF-8"));
			ByteArrayOutputStream transformedOutputStream = new ByteArrayOutputStream();
			transformer.transform(new StreamSource(inputStream), new StreamResult(transformedOutputStream));

			output = new String(transformedOutputStream.toByteArray(), "UTF-8").replaceAll(WhatizitHttpClient.XML_START, "").replaceAll(WhatizitHttpClient.XML_END, "");
			client.close();

		} catch (Exception e) {
			output = text;
			e.printStackTrace();
		}
		return output;
	}


	public static void main(String[] args) {
		TextTaggerWhatizitImpl xx = new TextTaggerWhatizitImpl();
		String inp =
			"Genistein and bromotetramisole (Br-t) strongly activate cystic fibrosis transmembrane conductance regulator (CFTR; ABCC7) chloride channels on Chinese hamster ovary cells and human airway epithelial cells. We have examined the possible role of phosphatases in stimulation by these drugs using patch-clamp and biochemical methods. Genistein inhibited the spontaneous rundown of channel activity that occurs after membrane patches are excised from cAMP-stimulated cells but had no effect on purified protein phosphatase type 1 (PP1), PP2A, PP2B, PP2C, or endogenous phosphatases when assayed as [(32)P]PO(4) release from prelabeled casein, recombinant GST-R domain fusion protein, or immunoprecipitated full-length CFTR. Br-t also slowed rundown of CFTR channels, but, in marked contrast to genistein, it did inhibit all four protein phosphatases tested. Half-maximal inhibition of PP2A and PP2C was observed with 0.5 and 1.5 mM Br-t, respectively. Protein phosphatases were also sensitive to (+)-p-Br-t, a stereoisomer of Br-t that does not inhibit alkaline phosphatases. Br-t appeared to act exclusively through phosphatases since it did not affect CFTR channels in patches that had low apparent endogenous phosphatase activity (i.e., those lacking spontaneous rundown). We conclude that genistein and Br-t act through different mechanisms. Genistein stimulates CFTR without inhibiting phosphatases, whereas Br-t acts by inhibiting a membrane-associated protein phosphatase (probably PP2C) that presumably allows basal phosphorylation to accumulate.";

		System.out.println(xx.tagText(inp));

	}


}
