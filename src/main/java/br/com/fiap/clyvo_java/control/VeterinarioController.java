package br.com.fiap.clyvo_java.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.individuos.Veterinario;
import br.com.fiap.clyvo_java.repository.individuos.VeterinarioRepository;
import jakarta.validation.Valid;

@Controller
public class VeterinarioController {

	@Autowired
	private VeterinarioRepository repV;

	@GetMapping("/veterinarios")
	public ModelAndView listarVeterinarios() {
		ModelAndView mv = new ModelAndView("/veterinario/lista");
		mv.addObject("veterinarios", repV.findAll());
		return mv;
	}

	@GetMapping("/veterinarios/novo")
	public ModelAndView popularFormVeterinario() {
		ModelAndView mv = new ModelAndView("/veterinario/novo");
		mv.addObject("veterinario", new Veterinario());
		return mv;
	}

	@PostMapping("/veterinarios/cadastrar")
	public ModelAndView cadastrarVeterinario(@Valid Veterinario veterinario, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/veterinario/novo");
			mv.addObject("veterinario", veterinario);
			return mv;
		}
		repV.save(veterinario);
		return new ModelAndView("redirect:/veterinarios");
	}

	@GetMapping("/veterinarios/detalhes/{id}")
	public ModelAndView exibirDetalhesVeterinario(@PathVariable Long id) {
		Optional<Veterinario> op = repV.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/veterinario/detalhes");
			mv.addObject("veterinario", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/veterinarios");
	}

	@GetMapping("/veterinarios/editar/{id}")
	public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
		Optional<Veterinario> op = repV.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/veterinario/edicao");
			mv.addObject("veterinario", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/veterinarios");
	}

	@PostMapping("/veterinarios/atualizar/{id}")
	public ModelAndView atualizarVeterinario(@PathVariable Long id, @Valid Veterinario veterinario, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/veterinario/edicao");
			mv.addObject("veterinario", veterinario);
			return mv;
		}
		Optional<Veterinario> op = repV.findById(id);
		if (op.isPresent()) {
			Veterinario veterinarioBanco = op.get();
			veterinarioBanco.transferirVeterinario(veterinario);
			repV.save(veterinarioBanco);
		}
		return new ModelAndView("redirect:/veterinarios");
	}

	@GetMapping("/veterinarios/remover/{id}")
	public ModelAndView removerVeterinario(@PathVariable Long id) {
		Optional<Veterinario> op = repV.findById(id);
		if (op.isPresent()) {
			repV.deleteById(id);
		}
		return new ModelAndView("redirect:/veterinarios");
	}

}