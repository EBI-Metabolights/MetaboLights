package uk.ac.ebi.metabolights.utils.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: conesa
 * Date: 07/07/15
 * Time: 16:49
 */
public class MetaboLightsJSONModule extends Module {

	private static final Logger logger = LoggerFactory.getLogger(MetaboLightsJSONModule.class);

	@Override
	public String getModuleName() {
		return "MetaboLights Compound serializer";
	}

	@Override
	public Version version() {
		return null;
	}

	@Override
	public void setupModule(SetupContext context) {

		context.addDeserializers(new MetaboLightsDeserialzer());


	}
}