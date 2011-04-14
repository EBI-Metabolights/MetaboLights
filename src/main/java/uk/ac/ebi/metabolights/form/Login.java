package uk.ac.ebi.metabolights.form;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * User login form. Hibernate annotations.
 * @author markr
 */
public class Login {

	@NotEmpty
	@Size(min = 1, max = 50)
	private String userName;
	@NotEmpty
	@Size(min = 1, max = 20)
	private String password;
	
	public void setUserName(String userName) {
	        this.userName = userName;
	}
	public String getUserName() {
	        return userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
	        return password;
	}
}
