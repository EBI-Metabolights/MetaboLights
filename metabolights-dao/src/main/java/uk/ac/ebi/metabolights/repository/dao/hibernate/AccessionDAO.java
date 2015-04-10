/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-21
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StableId;


/**
 * User: conesa
 * Date: 21/01/15
 * Time: 13:18
 */
public class AccessionDAO extends DAO <StableId,StableId>{

	private static String defaultPrefix = "MTBLS";

	/**
	 * Default prefix to use in case of creating the table from scratch
	 * @return String with the default prefix
	 */
	public static String getDefaultPrefix (){
		return defaultPrefix;
	}

	public static void setDefaultPrefix(String newDefaultPrefix){
		defaultPrefix = newDefaultPrefix;
	}


	@Override
	protected void init() {
		// Nothing to initialize
	}

	@Override
	protected Class getDataModelClass() {
		return StableId.class;
	}

	@Override
	protected void preSave(StableId datamodel) throws DAOException {
		// Nothing to do. No parents.
	}

	/**
	 * It has the logic concatenating the Id and the prefix. It also increase the Id by one,
	 * and invoke persistance of the new value to the database with the new Id increased
	 * @return String with the concatenated Accession number
	 */
	public String getStableId(String prefix) throws DAOException {

		String accession;

		//Log info
		logger.info("Stable id requested");

		//Get the stable id object

		StableId stableId = findByPrefix(prefix);


		if (stableId == null ) {

			logger.info("There isn't a StableId record. Creating a new default id.");

			//Instantiate the StableId object
			stableId = new StableId();

			//Populate with default values
			//Populate de id of the row.
			stableId.setSeq(0);  //Increment will be done below
			stableId.setPrefix(prefix);

		}

		//Increment the sequence by one
		stableId.setSeq(stableId.getSeq() + 1);
		save(stableId);

		logger.debug("Stable Id sequence increased to " + stableId.getSeq());

		//Get the new accession number
		accession = stableId.getPrefix() + stableId.getSeq();

		logger.info("New stableId is " + accession);


		//Return the accession number
		return accession;
	}

	private StableId findByPrefix(String prefix) throws DAOException {

		return findSingle("prefix=:prefix",new Filter(new Object[]{"prefix",prefix}));

	}

	public String getStableId() throws DAOException {
		return getStableId(defaultPrefix);
	}



}
