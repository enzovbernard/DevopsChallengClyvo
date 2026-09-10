package br.com.fiap.clyvo_java.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.pet.saude.Doenca;
import br.com.fiap.clyvo_java.repository.pet.saude.DoencaRepository;
import jakarta.validation.Valid;

@Controller
public class DoencaController {

	@Autowired
	private DoencaRepository repD;

	@GetMapping("/doencas")
	public ModelAndView listarDoencas() {
		ModelAndView mv = new ModelAndView("/doenca/lista");
		mv.addObject("doencas", repD.findAll());
		return mv;
	}

	@GetMapping("/doencas/nova")
	public ModelAndView popularFormDoenca() {
		ModelAndView mv = new ModelAndView("/doenca/nova");
		mv.addObject("doenca", new Doenca());
		return mv;
	}

	@PostMapping("/doencas/cadastrar")
	public ModelAndView cadastrarDoenca(@Valid Doenca doenca, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/doenca/nova");
			mv.addObject("doenca", doenca);
			return mv;
		}
		repD.save(doenca);
		return new ModelAndView("redirect:/doencas");
	}

	@GetMapping("/doencas/detalhes/{id}")
	public ModelAndView exibirDetalhesDoenca(@PathVariable Long id) {
		Optional<Doenca> op = repD.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/doenca/detalhes");
			mv.addObject("doenca", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/doencas");
	}

	@GetMapping("/doencas/editar/{id}")
	public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
		Optional<Doenca> op = repD.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/doenca/edicao");
			mv.addObject("doenca", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/doencas");
	}

	@PostMapping("/doencas/atualizar/{id}")
	public ModelAndView atualizarDoenca(@PathVariable Long id, @Valid Doenca doenca, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/doenca/edicao");
			mv.addObject("doenca", doenca);
			return mv;
		}
		Optional<Doenca> op = repD.findById(id);
		if (op.isPresent()) {
			Doenca doencaBanco = op.get();
			doencaBanco.transferirDoenca(doenca);
			repD.save(doencaBanco);
		}
		return new ModelAndView("redirect:/doencas");
	}

	@GetMapping("/doencas/remover/{id}")
	public ModelAndView removerDoenca(@PathVariable Long id) {
		Optional<Doenca> op = repD.findById(id);
		if (op.isPresent()) {
			repD.deleteById(id);
		}
		return new ModelAndView("redirect:/doencas");
	}

}