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

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.DataModel;
import uk.ac.ebi.metabolights.repository.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 14:55
 */
public abstract class DAO<BussinessEntity,dataModel extends DataModel> {

	protected String dataModelName;
	private static Logger logger = LoggerFactory.getLogger(DAO.class);

	public DAO(){
		this.dataModelName = getDataModelClass().getSimpleName();
	}


	protected abstract Class<dataModel> getDataModelClass();


	protected Session getSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	protected void closeSession() {
		Session session =  HibernateUtil.getSessionFactory().getCurrentSession();
		session.close();

	}

	public List<BussinessEntity> findBy(String where , Filter filter) throws DAOException {

		Session session = getSession();


		Query query = getQuery(session, where, filter);

		List<dataModel> dataModels = query.list();

		session.close();

		return convertDataModelToBussinessModel(dataModels);

	}


	public BussinessEntity findSingle(String where, Filter filter) throws DAOException {

		return findBy(where, filter).iterator().next();

	}

	public  List<BussinessEntity>  findAll() throws DAOException {

		return findBy(null, null);

	}


	public void save(BussinessEntity bussinessEntity) throws DAOException {

		Session session = getSession();
		session.beginTransaction();

		// Convert BusinessEntity to DataModel
		dataModel datamodel = getDataModel();

		// Invoke the conversion
		datamodel.setBussinesModelEntity(bussinessEntity);
		session.save(datamodel);

		session.getTransaction().commit();
		session.close();

	}

	public void delete(BussinessEntity bussinessEntity) throws DAOException {

		Session session = getSession();
		session.beginTransaction();

		// Convert BusinessEntity to DataModel
		dataModel datamodel = getDataModel();

		// Invoke the conversion
		datamodel.setBussinesModelEntity(bussinessEntity);
		session.delete(datamodel);

		session.getTransaction().commit();
		session.close();

	}


	private Query getQuery(Session session, String where, Filter filter) throws DAOException {

		String queryS = "from " + dataModelName;
		
		// Add the where clause if exists
		if (where != null && where.length()!=0) queryS = queryS + " where " + where;

		Query query = session.createQuery(queryS);

		// Fill query with parameters
		if (filter != null) filter.fillQuery(query);

		return query;
	}

	private List<BussinessEntity> convertDataModelToBussinessModel(List<dataModel>dataModels){

		List<BussinessEntity> bussinessModels = new ArrayList<BussinessEntity>();

		for (dataModel datamodel: dataModels){
			BussinessEntity bussinessModel = (BussinessEntity) datamodel.dataModelToBusinessModel();
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

	public BussinessEntity findById(Long id) throws DAOException {
		return findSingle("id=:id", new Filter(new Object[]{"id", id}));
	}
}
