/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/21/14 9:46 AM
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

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/7/13 1:47 PM
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

package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.referencelayer.importer.ConnectionProvider;
import uk.ac.ebi.metabolights.referencelayer.importer.ReferenceLayerImporter;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@Controller
public class MetaboliteImporterController extends AbstractController{


    private static Logger logger = Logger.getLogger(MetaboliteImporterController.class);
	private static ReferenceLayerImporter importer;

    @RequestMapping({"/importmetabolites"})
	public ModelAndView importMetabolites(){


        return AppContext.getMAVFactory().getFrontierMav("importmetabolites");
        
     
    }
	@Transactional
	@RequestMapping({"/importmetabolitesrun"})
	public ModelAndView importMetabolitesRun(
			@RequestParam(required=true,value="chebiId") final String chebiId
	) throws IOException, SQLException, NamingException {


		new Thread(){

			public void run(){

				// Initialize the importer
				try {

					initializeImporter();

					importer.setChebiIDRoot(chebiId);

					importer.importMetabolitesFromChebi();

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (NamingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}.start();


		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:" + "importmetabolitesstatus");


	}

	private void initializeImporter() throws SQLException, NamingException, IOException {

		ConnectionProvider connectionProvider = new ConnectionProvider() {
			@Override
			public Connection getConnection() {
				try {

					return AppContext.getConnection();

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (NamingException e) {
					e.printStackTrace();
				}

				return null;
			}
		};


		importer = new ReferenceLayerImporter(connectionProvider);
	}
	@RequestMapping({"/importmetabolitesstatus"})
	public ModelAndView showImportStatus() {

		ModelAndView importStatusMAV = AppContext.getMAVFactory().getFrontierMav("importmetabolitesstatus");

		// If there is an importer
		if (importer != null) {

			// ... if there is a process report
			if (importer.getProcessReport() != null) {
				importStatusMAV.addObject("report", importer.getProcessReport());
			}
		}

		return importStatusMAV;

	}
 }