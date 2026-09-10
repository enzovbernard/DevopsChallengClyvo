package br.com.fiap.clyvo_java.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.individuos.Responsavel;
import br.com.fiap.clyvo_java.model.pet.Animal;
import br.com.fiap.clyvo_java.repository.individuos.ResponsavelRepository;
import br.com.fiap.clyvo_java.repository.pet.AnimalRepository;
import jakarta.validation.Valid;

@Controller
public class AnimalController {

	@Autowired
	private AnimalRepository repA;

	@Autowired
	private ResponsavelRepository repR;

	@GetMapping("/animais")
	public ModelAndView listarAnimais() {
		ModelAndView mv = new ModelAndView("/animal/lista");
		mv.addObject("animais", repA.findAll());
		return mv;
	}

	@GetMapping("/animais/novo")
	public ModelAndView popularFormAnimal() {
		ModelAndView mv = new ModelAndView("/animal/novo");
		mv.addObject("animal", new Animal());
		mv.addObject("lista_responsaveis", repR.findAll());
		return mv;
	}

	@PostMapping("/animais/cadastrar")
	public ModelAndView cadastrarAnimal(@Valid Animal animal, BindingResult bd,
			@RequestParam(name = "id_responsavel", required = false) Long id_responsavel) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/animal/novo");
			mv.addObject("animal", animal);
			mv.addObject("lista_responsaveis", repR.findAll());
			return mv;
		}
		if (id_responsavel != null) {
			Optional<Responsavel> op = repR.findById(id_responsavel);
			op.ifPresent(animal::setResponsavel);
		}
		repA.save(animal);
		return new ModelAndView("redirect:/animais");
	}

	@GetMapping("/animais/detalhes/{id}")
	public ModelAndView exibirDetalhesAnimal(@PathVariable Long id) {
		Optional<Animal> op = repA.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/animal/detalhes");
			mv.addObject("animal", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/animais");
	}

	@GetMapping("/animais/editar/{id}")
	public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
		Optional<Animal> op = repA.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/animal/edicao");
			mv.addObject("animal", op.get());
			mv.addObject("lista_responsaveis", repR.findAll());
			return mv;
		}
		return new ModelAndView("redirect:/animais");
	}

	@PostMapping("/animais/atualizar/{id}")
	public ModelAndView atualizarAnimal(@PathVariable Long id, @Valid Animal animal, BindingResult bd,
			@RequestParam(name = "id_responsavel", required = false) Long id_responsavel) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/animal/edicao");
			mv.addObject("animal", animal);
			mv.addObject("lista_responsaveis", repR.findAll());
			return mv;
		}
		Optional<Animal> op = repA.findById(id);
		if (op.isPresent()) {
			Animal animalBanco = op.get();
			animalBanco.transferirAnimal(animal);
			if (id_responsavel != null) {
				Optional<Responsavel> opR = repR.findById(id_responsavel);
				opR.ifPresent(animalBanco::setResponsavel);
			}
			repA.save(animalBanco);
		}
		return new ModelAndView("redirect:/animais");
	}

	@GetMapping("/animais/remover/{id}")
	public ModelAndView removerAnimal(@PathVariable Long id) {
		Optional<Animal> op = repA.findById(id);
		if (op.isPresent()) {
			repA.deleteById(id);
		}
		return new ModelAndView("redirect:/animais");
	}

}