package uk.ac.ebi.metabolights.webservice.dao;

/**
 * User: conesa
 * Date: 04/06/2014
 * Time: 15:13
 */
public interface SecurityDAO {

	public boolean canUserAccessStudy(String userToken, String studyId);
}