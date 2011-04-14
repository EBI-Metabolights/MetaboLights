package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.validation.BindingResult;

import uk.ac.ebi.metabolights.form.Login;

import java.util.Map;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@SessionAttributes({"login"})
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map model) {
		if (model.get("login")==null) {
			Login login = new Login();
			model.put("login", login);
		}
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processForm(@Valid Login login, BindingResult result, Map model) {
		String userName = "userName";
		String password = "password";
		if (result.hasErrors()) {
			return "login";
		}
		if (!login.getUserName().equals(userName) || !login.getPassword().equals(password)) {
			return "login";
		}
		model.put("login", login);
		return "loginsuccess";
	}
}
