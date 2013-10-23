/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/05/13 09:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import org.apache.log4j.Logger;
import uk.ac.ebi.biobabel.util.db.SQLLoader;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.IDatabaseDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public abstract class AbstractDAO {


	protected static Logger LOGGER ;

	protected Connection con;
	protected SQLLoader sqlLoader;

	/**
	 * @param connection to the database
	 * @throws java.io.IOException
	 */
	public AbstractDAO(Connection connection) throws IOException{
        this.con = connection;
    }

    protected void setUp(Class DAOClass) throws IOException {
        this.sqlLoader = new SQLLoader(DAOClass, con);
        this.LOGGER = Logger.getLogger(DAOClass);

    }

    /**
     * Setter for database connection. It also sets the same connection
     * for the underlying objects.<br>
     * This method should be used with pooled connections, and only when the
     * previous one and its prepared statements have been properly closed
     * (returned).
     * @param con
     * @throws java.sql.SQLException
     */
	public void setConnection(Connection con) throws SQLException{
		this.con = con;
		sqlLoader.setConnection(con);
	}

	@Override
	protected void finalize() throws Throwable {
        super.finalize();
		close();
	}

	/**
	 * Closes prepared statements, but not the connection.
	 * If you want to close the connection or return it to a pool,
	 * please call explicitly the method {@link java.sql.Connection#close()} or
	 * {@link #returnPooledConnection()} respectively.
	 */
	public void close() throws DAOException {
        try {
            sqlLoader.close();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
	}

	/**
	 * Closes (returns to the pool) prepared statements and connection.
	 * This method should be called explicitly before finalising this object,
	 * in case its connection belongs to a pool.
	 * <br>
	 *      *
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException while closing the compound reader.
     * @throws java.sql.SQLException while setting the compound reader connection to
     *      null.
     */
	public void returnPooledConnection() throws DAOException, SQLException{

		close();
		if (con != null){
			con.close();
			con = null;
		}
	}
}