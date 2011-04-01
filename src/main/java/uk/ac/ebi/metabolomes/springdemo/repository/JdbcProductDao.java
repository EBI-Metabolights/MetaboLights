package uk.ac.ebi.metabolomes.springdemo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import uk.ac.ebi.metabolomes.springdemo.domain.Product;

/**
 * from: http://static.springsource.org/docs/Spring-MVC-step-by-step/part5.html
 * 
 * JdbcProductDao is the JDBC implementation of the DAO interface. Spring provides a JDBC abstraction framework that we will make use of. 
 * The biggest difference between using straight JDBC and Spring's JDBC framework is that you don't have to worry about opening and closing the connection 
 * or any statements. It is all handled for you. 
 * 
 * Another advantage is that you won't have to catch any exceptions, unless you want to. Spring wraps all SQLExceptions in it's own unchecked exception 
 * hierarchy inheriting from DataAccessException. If you want to, you can catch this exception, but since most database exceptions are impossible to 
 * recover from anyway, you might as well just let the exception propagate up to a higher level. 
 * The class SimpleJdbcDaoSupport provides convenient access to an already configured SimpleJdbcTemplate, so we extend this class. 
 * 
 * All we will have to provide in the application context is a configured DataSource.
 *
 */
public class JdbcProductDao extends SimpleJdbcDaoSupport implements ProductDao {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	public List<Product> getProductList() {
		logger.info("Getting products!");
		List<Product> products = getSimpleJdbcTemplate().query(  //deprecated? check for alternative
				"select id, description, price from products", // hmm, hard coded SQL statements. Perhaps group these together in a separate class
				new ProductMapper());
		return products;
	}

	public void saveProduct(Product prod) {
		logger.info("Saving product: " + prod.getDescription());
		int count = getSimpleJdbcTemplate()
				.update("update products set description = :description, price = :price where id = :id",
						new MapSqlParameterSource()
								.addValue("description", prod.getDescription())
								.addValue("price", prod.getPrice())
								.addValue("id", prod.getId()));
		logger.info("Rows affected: " + count);
	}

	/**
	 * What's this? Must look into
	 * @author markr
	 *
	 */
	private static class ProductMapper implements
			ParameterizedRowMapper<Product> {
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product prod = new Product();
			prod.setId(rs.getInt("id"));
			prod.setDescription(rs.getString("description"));
			prod.setPrice(new Double(rs.getDouble("price")));
			return prod;
		}

	}

}
