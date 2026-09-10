package br.com.fiap.clyvo_java.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.individuos.Responsavel;
import br.com.fiap.clyvo_java.repository.individuos.ResponsavelRepository;
import jakarta.validation.Valid;

@Controller
public class ResponsavelController {

	@Autowired
	private ResponsavelRepository repR;

	@GetMapping("/responsaveis")
	public ModelAndView listarResponsaveis() {
		ModelAndView mv = new ModelAndView("/responsavel/lista");
		mv.addObject("responsaveis", repR.findAll());
		return mv;
	}

	@GetMapping("/responsaveis/novo")
	public ModelAndView popularFormResponsavel() {
		ModelAndView mv = new ModelAndView("/responsavel/novo");
		mv.addObject("responsavel", new Responsavel());
		return mv;
	}

	@PostMapping("/responsaveis/cadastrar")
	public ModelAndView cadastrarResponsavel(@Valid Responsavel responsavel, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/responsavel/novo");
			mv.addObject("responsavel", responsavel);
			return mv;
		}
		repR.save(responsavel);
		return new ModelAndView("redirect:/responsaveis");
	}

	@GetMapping("/responsaveis/detalhes/{id}")
	public ModelAndView exibirDetalhesResponsavel(@PathVariable Long id) {
		Optional<Responsavel> op = repR.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/responsavel/detalhes");
			mv.addObject("responsavel", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/responsaveis");
	}

	@GetMapping("/responsaveis/editar/{id}")
	public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
		Optional<Responsavel> op = repR.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/responsavel/edicao");
			mv.addObject("responsavel", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/responsaveis");
	}

	@PostMapping("/responsaveis/atualizar/{id}")
	public ModelAndView atualizarResponsavel(@PathVariable Long id, @Valid Responsavel responsavel, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/responsavel/edicao");
			mv.addObject("responsavel", responsavel);
			return mv;
		}
		Optional<Responsavel> op = repR.findById(id);
		if (op.isPresent()) {
			Responsavel responsavelBanco = op.get();
			responsavelBanco.transferirResponsavel(responsavel);
			repR.save(responsavelBanco);
		}
		return new ModelAndView("redirect:/responsaveis");
	}

	@GetMapping("/responsaveis/remover/{id}")
	public ModelAndView removerResponsavel(@PathVariable Long id) {
		Optional<Responsavel> op = repR.findById(id);
		if (op.isPresent()) {
			repR.deleteById(id);
		}
		return new ModelAndView("redirect:/responsaveis");
	}

}