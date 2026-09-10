package br.com.fiap.clyvo_java.control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.pet.Animal;
import br.com.fiap.clyvo_java.model.pet.saude.Vacina;
import br.com.fiap.clyvo_java.model.pet.saude.VacinaAnimal;
import br.com.fiap.clyvo_java.repository.pet.AnimalRepository;
import br.com.fiap.clyvo_java.repository.pet.saude.VacinaAnimalRepository;
import br.com.fiap.clyvo_java.repository.pet.saude.VacinaRepository;
import jakarta.validation.Valid;

@Controller
public class VacinaAnimalController {

	@Autowired
	private VacinaAnimalRepository repVA;

	@Autowired
	private AnimalRepository repA;

	@Autowired
	private VacinaRepository repV;

	@GetMapping("/vacinaAnimal")
	public ModelAndView listarAplicacoes() {
		ModelAndView mv = new ModelAndView("/vacinaAnimal/lista");
		mv.addObject("aplicacoes", repVA.findAll());
		return mv;
	}

	@GetMapping("/vacinaAnimal/aplicar")
	public ModelAndView popularFormAplicacao() {
		ModelAndView mv = new ModelAndView("/vacinaAnimal/aplicar");
		mv.addObject("vacinaAnimal", new VacinaAnimal());
		mv.addObject("lista_animais", repA.findAll());
		mv.addObject("lista_vacinas", repV.findAll());
		return mv;
	}

	@PostMapping("/vacinaAnimal/aplicar")
	public ModelAndView aplicarVacina(@Valid VacinaAnimal vacinaAnimal, BindingResult bd,
			@RequestParam(name = "id_animal") Long id_animal,
			@RequestParam(name = "id_vacina") Long id_vacina) {

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/vacinaAnimal/aplicar");
			mv.addObject("vacinaAnimal", vacinaAnimal);
			mv.addObject("lista_animais", repA.findAll());
			mv.addObject("lista_vacinas", repV.findAll());
			return mv;
		}

		List<VacinaAnimal> anteriores = repVA.buscarAplicacoesAnteriores(id_animal, id_vacina);

		if (!anteriores.isEmpty()) {
			VacinaAnimal ultima = anteriores.get(0);
			long diasDesdeUltima = ChronoUnit.DAYS.between(ultima.getDt_receita(), LocalDate.now());

			if (diasDesdeUltima < ultima.getFrequencia_aplicacao()) {
				LocalDate proximaData = ultima.getDt_receita().plusDays(ultima.getFrequencia_aplicacao());
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				ModelAndView mv = new ModelAndView("/vacinaAnimal/aplicar");
				mv.addObject("vacinaAnimal", vacinaAnimal);
				mv.addObject("lista_animais", repA.findAll());
				mv.addObject("lista_vacinas", repV.findAll());
				mv.addObject("erro_aplicacao", "Este animal já recebeu esta vacina recentemente. "
						+ "Próxima aplicação permitida a partir de " + proximaData.format(formato) + ".");
				return mv;
			}
		}

		Optional<Animal> opAnimal = repA.findById(id_animal);
		Optional<Vacina> opVacina = repV.findById(id_vacina);

		vacinaAnimal.setDt_receita(LocalDate.now());
		opAnimal.ifPresent(vacinaAnimal::setAnimal);
		opVacina.ifPresent(vacinaAnimal::setVacina);

		repVA.save(vacinaAnimal);

		return new ModelAndView("redirect:/vacinaAnimal");
	}

	@GetMapping("/vacinaAnimal/detalhes/{id}")
	public ModelAndView exibirDetalhesAplicacao(@PathVariable Long id) {
		Optional<VacinaAnimal> op = repVA.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/vacinaAnimal/detalhes");
			mv.addObject("vacinaAnimal", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/vacinaAnimal");
	}

	@GetMapping("/vacinaAnimal/remover/{id}")
	public ModelAndView removerAplicacao(@PathVariable Long id) {
		Optional<VacinaAnimal> op = repVA.findById(id);
		if (op.isPresent()) {
			repVA.deleteById(id);
		}
		return new ModelAndView("redirect:/vacinaAnimal");
	}

}