/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-May-07
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 14:55
 */
public abstract class DAO<BusinessEntity,dataModel extends DataModel> {

	protected String dataModelName;
	protected static Logger logger = LoggerFactory.getLogger(DAO.class);

	// Wrapper for the session
	protected SessionWrapper session = HibernateUtil.getSession();

	public DAO(){

		this.dataModelName = getDataModelClass().getSimpleName();
		init();

	}

	public SessionWrapper getSession() {
        return session;
    }

	public void setSession(SessionWrapper session) {
		this.session = session;
	}

	protected abstract void init();

	protected abstract Class<dataModel> getDataModelClass();

	protected abstract void preSave(dataModel datamodel) throws DAOException;

	public List<BusinessEntity> findBy(String where , Filter filter) throws DAOException {

        List<BusinessEntity> businessEntities = null;

        if (session == null)
            session = HibernateUtil.getSession();

        try {
            logger.info("Requesting database session");
            session.needSession();

            logger.debug("Getting SQL query");
            Query query = getDefaultQuery(where, filter);

            logger.debug("SQL query is : " + query.getQueryString());
            List<dataModel> dataModels = query.list();

            logger.debug("Converting to MTBLS data model");
            // We are bypassing any lazy initialization with this. So far it's fine.
            businessEntities = convertDataModelToBusinessModel(dataModels);

        } catch (Exception e) {
            logger.error("ERROR: Could not query the database: " + e.getMessage());
        } finally {
            logger.info("Closing the session");
			session.noNeedSession();
		}

		return businessEntities;

	}


	public BusinessEntity findSingle(String where, Filter filter) throws DAOException {

        if (where == null && filter == null)
            return null;

		try {
			return findBy(where, filter).iterator().next();
		} catch (NoSuchElementException e){
			return null;
		} catch (Exception e){
		    logger.error("Could not query the database using: "+ where);
		    return null;
        }

	}

	public  List<BusinessEntity>  findAll() throws DAOException {

		return findBy(null, null);

	}

	public Object getUniqueValue(String wholeQuery, Filter filter) throws DAOException {

		session.needSession();

		Query query = getQuery(wholeQuery, filter);

		Object value = query.uniqueResult();

		session.noNeedSession();

		return value;

	}

	public List getList(String wholeQuery, Filter filter) throws DAOException {

		session.needSession();

		Query query = getQuery(wholeQuery, filter);

		List value = query.list();

		session.noNeedSession();

		return value;

	}

	public void save(BusinessEntity bussinessEntity ) throws DAOException {

		// Convert BusinessEntity to DataModel
		dataModel datamodel = getDataModel();

		// Invoke the conversion
		datamodel.setBussinesModelEntity(bussinessEntity);

		session.needSession();

		// Pre save will trigger the save of parent objects if any.
		preSave(datamodel);

		saveDataModel(datamodel);

		session.noNeedSession();

	}


	protected void saveDataModel(dataModel datamodel){

		session.needSession();

		session.saveOrUpdate(datamodel);

		session.noNeedSession();

	}


	public void save(Set<dataModel> dataModels) throws DAOException {
		save(dataModels,false);
	}

	// DO not save if business entity is new: (To allow a user to have 2 studies):
	// (NEW) Study1 <-->  (NEW) User1     converts into  StudyData1 --> UserData1 (note UserData1 doesn't point back to Study)
	// (NEW) Study2 <-->  (SAVED) User1   converts into StudyData2 --> UserData1 (If we save UserData1 we will loose StudyData1<->UserData1)
	public void save(Set<dataModel> dataModels, boolean onlyNewOnes) throws DAOException {

		for (dataModel dataModel:dataModels) {

			if (onlyNewOnes && !dataModel.isNew()){
				// Next
				continue;
			}
			saveDataModel(dataModel);

		}

	}

	public void delete(BusinessEntity bussinessEntity) throws DAOException {

		session.needSession();

		// Convert BusinessEntity to DataModel
		dataModel datamodel = getDataModel();

		// Invoke the conversion
		datamodel.setBussinesModelEntity(bussinessEntity);
		session.delete(datamodel);

		session.noNeedSession();

	}

	private Query getDefaultQuery(String where, Filter filter) throws DAOException {

		String queryS = "from " + dataModelName;

		// Add the where clause if exists
		if (where != null && where.length()!=0) queryS = queryS + " where " + where;

		return getQuery(queryS,filter);
	}

	private Query getQuery(String queryS, Filter filter) throws DAOException {


		Query query = session.createQuery(queryS);

		// Fill query with parameters
		if (filter != null) filter.fillQuery(query);

		return query;
	}

	private List<BusinessEntity> convertDataModelToBusinessModel(List<dataModel>dataModels){

		List<BusinessEntity> bussinessModels = new ArrayList<BusinessEntity>();

		for (dataModel datamodel: dataModels){
			BusinessEntity bussinessModel = (BusinessEntity) datamodel.dataModelToBusinessModel();
			bussinessModels.add(bussinessModel);

		}

		return bussinessModels;
	}

	public dataModel getDataModel() throws DAOException {
		try {
			return getDataModelClass().newInstance();
		} catch (Exception e) {
			throw new DAOException("Can't instantiate the datamodel " + getDataModelClass().getSimpleName() + ". Check You DAO implements getDataModelClass",e);
		}
	}

	public BusinessEntity findById(Long id) throws DAOException {
		return findSingle("id=:id", new Filter(new Object[]{"id", id}));
	}
}
