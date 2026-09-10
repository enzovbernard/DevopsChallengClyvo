package br.com.fiap.clyvo_java.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.pet.saude.Vacina;
import br.com.fiap.clyvo_java.repository.pet.saude.VacinaRepository;
import jakarta.validation.Valid;

@Controller
public class VacinaController {

	@Autowired
	private VacinaRepository repV;

	@GetMapping("/vacinas")
	public ModelAndView listarVacinas() {
		ModelAndView mv = new ModelAndView("/vacina/lista");
		mv.addObject("vacinas", repV.findAll());
		return mv;
	}

	@GetMapping("/vacinas/nova")
	public ModelAndView popularFormVacina() {
		ModelAndView mv = new ModelAndView("/vacina/nova");
		mv.addObject("vacina", new Vacina());
		return mv;
	}

	@PostMapping("/vacinas/cadastrar")
	public ModelAndView cadastrarVacina(@Valid Vacina vacina, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/vacina/nova");
			mv.addObject("vacina", vacina);
			return mv;
		}
		repV.save(vacina);
		return new ModelAndView("redirect:/vacinas");
	}

	@GetMapping("/vacinas/detalhes/{id}")
	public ModelAndView exibirDetalhesVacina(@PathVariable Long id) {
		Optional<Vacina> op = repV.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/vacina/detalhes");
			mv.addObject("vacina", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/vacinas");
	}

	@GetMapping("/vacinas/editar/{id}")
	public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
		Optional<Vacina> op = repV.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/vacina/edicao");
			mv.addObject("vacina", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/vacinas");
	}

	@PostMapping("/vacinas/atualizar/{id}")
	public ModelAndView atualizarVacina(@PathVariable Long id, @Valid Vacina vacina, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/vacina/edicao");
			mv.addObject("vacina", vacina);
			return mv;
		}
		Optional<Vacina> op = repV.findById(id);
		if (op.isPresent()) {
			Vacina vacinaBanco = op.get();
			vacinaBanco.transferirVacina(vacina);
			repV.save(vacinaBanco);
		}
		return new ModelAndView("redirect:/vacinas");
	}

	@GetMapping("/vacinas/remover/{id}")
	public ModelAndView removerVacina(@PathVariable Long id) {
		Optional<Vacina> op = repV.findById(id);
		if (op.isPresent()) {
			repV.deleteById(id);
		}
		return new ModelAndView("redirect:/vacinas");
	}

}