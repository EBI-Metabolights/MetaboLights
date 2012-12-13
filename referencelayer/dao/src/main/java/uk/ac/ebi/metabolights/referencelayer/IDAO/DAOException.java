package uk.ac.ebi.metabolights.referencelayer.IDAO;

/**
 * Exception thrown when accessing, reading or writing Reference Layer data from/to its
 * data source.
 * @author Pablo Conesa
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = -4078421691096305050L;

	public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }

}