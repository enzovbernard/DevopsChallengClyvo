package br.com.fiap.clyvo_java.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@GetMapping("/login")
	public ModelAndView efetuarLogin() {
		return new ModelAndView("/login");
	}

}