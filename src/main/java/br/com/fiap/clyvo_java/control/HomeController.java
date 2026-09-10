package br.com.fiap.clyvo_java.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.usuario.Usuario;
import br.com.fiap.clyvo_java.repository.usuario.UsuarioRepository;

@Controller
public class HomeController {

	@Autowired
	private UsuarioRepository repU;

	@GetMapping("/home")
	public ModelAndView popularHomePage() {
		ModelAndView mv = new ModelAndView("/home/index");

		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();

		Optional<Usuario> op = repU.findByUsername(autenticado.getName());

		if (op.isPresent()) {
			mv.addObject("usuario", op.get());
		}

		return mv;
	}

}