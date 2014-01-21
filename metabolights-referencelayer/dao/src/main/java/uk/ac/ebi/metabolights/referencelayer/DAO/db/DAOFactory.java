package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;

import java.io.IOException;
import java.sql.Connection;

/**
 * User: conesa
 * Date: 08/01/2014
 * Time: 10:00
 */
public class DAOFactory {

	private static Connection connection;

	private static SpeciesGroupDAO spgd;
	private static SpeciesMembersDAO spmd;
	private static SpeciesDAO spd;

	public static SpeciesGroupDAO getSpeciesGroupDAO() throws DAOException
	{
		if (spgd == null || !spgd.con.equals(connection))
		{
			try {
				spgd = new SpeciesGroupDAO(connection);
			} catch (IOException e) {
				throw new DAOException(e);
			}
		}

		return spgd;
	}

	public static SpeciesMembersDAO getSpeciesMembersDAO() throws DAOException
	{
		if (spmd == null  || !spmd.con.equals(connection))
		{
			try {
				spmd = new SpeciesMembersDAO(connection);
			} catch (IOException e) {
				throw new DAOException(e);
			}
		}

		return spmd;
	}

	public static SpeciesDAO getSpeciesDAO() throws DAOException
	{

		if (spd == null || !spd.con.equals(connection))
		{
			try {
				spd = new SpeciesDAO(connection);
			} catch (IOException e) {
				throw new DAOException(e);
			}
		}

		return spd;
	}

	public static void setConnection(Connection connection)
	{
		DAOFactory.connection = connection;
	}


}
