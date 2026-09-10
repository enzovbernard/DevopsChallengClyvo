package br.com.fiap.clyvo_java.control;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.clyvo_java.model.individuos.Veterinario;
import br.com.fiap.clyvo_java.model.pet.Animal;
import br.com.fiap.clyvo_java.model.pet.consultas.Consulta;
import br.com.fiap.clyvo_java.repository.individuos.VeterinarioRepository;
import br.com.fiap.clyvo_java.repository.pet.AnimalRepository;
import br.com.fiap.clyvo_java.repository.pet.consultas.ConsultaRepository;
import jakarta.validation.Valid;

@Controller
public class ConsultaController {

	@Autowired
	private ConsultaRepository repC;

	@Autowired
	private AnimalRepository repA;

	@Autowired
	private VeterinarioRepository repV;

	@GetMapping("/consultas")
	public ModelAndView listarConsultas() {
		ModelAndView mv = new ModelAndView("/consulta/lista");
		mv.addObject("consultas", repC.findAll());
		return mv;
	}

	@GetMapping("/consultas/agendar")
	public ModelAndView popularFormConsulta() {
		ModelAndView mv = new ModelAndView("/consulta/agendar");
		mv.addObject("consulta", new Consulta());
		mv.addObject("lista_animais", repA.findAll());
		mv.addObject("lista_veterinarios", repV.findAll());
		return mv;
	}

	@PostMapping("/consultas/agendar")
	public ModelAndView agendarConsulta(@Valid Consulta consulta, BindingResult bd,
			@RequestParam(name = "id_animal") Long id_animal,
			@RequestParam(name = "id_veterinario") Long id_veterinario) {

		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/consulta/agendar");
			mv.addObject("consulta", consulta);
			mv.addObject("lista_animais", repA.findAll());
			mv.addObject("lista_veterinarios", repV.findAll());
			return mv;
		}

		boolean veterinarioOcupado = repC.existeConsultaMesmoVeterinarioMesmoDia(id_veterinario, consulta.getDt_consulta());

		if (veterinarioOcupado) {
			ModelAndView mv = new ModelAndView("/consulta/agendar");
			mv.addObject("consulta", consulta);
			mv.addObject("lista_animais", repA.findAll());
			mv.addObject("lista_veterinarios", repV.findAll());
			mv.addObject("erro_agendamento", "Este veterinário já possui uma consulta marcada nesta data. Escolha outra data ou outro veterinário.");
			return mv;
		}

		Optional<Animal> opAnimal = repA.findById(id_animal);
		Optional<Veterinario> opVeterinario = repV.findById(id_veterinario);

		consulta.setDt_agendamento(LocalDate.now());
		opAnimal.ifPresent(consulta::setAnimal);
		opVeterinario.ifPresent(consulta::setVeterinario);

		repC.save(consulta);

		return new ModelAndView("redirect:/consultas");
	}

	@GetMapping("/consultas/detalhes/{id}")
	public ModelAndView exibirDetalhesConsulta(@PathVariable Long id) {
		Optional<Consulta> op = repC.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("/consulta/detalhes");
			mv.addObject("consulta", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/consultas");
	}

	@GetMapping("/consultas/cancelar/{id}")
	public ModelAndView cancelarConsulta(@PathVariable Long id) {
		Optional<Consulta> op = repC.findById(id);
		if (op.isPresent()) {
			repC.deleteById(id);
		}
		return new ModelAndView("redirect:/consultas");
	}

}