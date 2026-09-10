package br.com.fiap.clyvo_java.model.pet.consultas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fiap.clyvo_java.model.individuos.Veterinario;
import br.com.fiap.clyvo_java.model.pet.Animal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "T_CLYVO_CONSULTA")
public class Consulta {

    @Id
    @Column(name = "id_consulta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_consulta;

    @PastOrPresent(message = "A data do agendamento deve ser uma data passada ou presente.")
    @Column(name = "dt_agendamento")
    private LocalDate dt_agendamento;

    @NotNull(message = "A data da consulta é obrigatória.")
    @Future(message = "A data da consulta deve ser uma data futura.")
    @Column(name = "dt_consulta")
    private LocalDate dt_consulta;

    @ManyToOne
    @JoinColumn(name = "fk_id_animal")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "fk_id_veterinario")
    private Veterinario veterinario;

    public Consulta() {}

	public Consulta(Long id_consulta, LocalDate dt_agendamento, LocalDate dt_consulta, Animal animal,
			Veterinario veterinario) {
		this.id_consulta = id_consulta;
		this.dt_agendamento = dt_agendamento;
		this.dt_consulta = dt_consulta;
		this.animal = animal;
		this.veterinario = veterinario;
	}
	
	public void transferirConsulta(Consulta consulta) {
		this.dt_agendamento = consulta.getDt_agendamento();
		this.dt_consulta = consulta.getDt_consulta();
		this.animal = consulta.getAnimal();
		this.veterinario = consulta.getVeterinario();
	}
	
	@JsonIgnore
	@OneToMany(
		    mappedBy = "consulta",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	private List<Historico> historicos = new ArrayList<>();
	
	

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

	public Long getId_consulta() {
		return id_consulta;
	}

	public void setId_consulta(Long id_consulta) {
		this.id_consulta = id_consulta;
	}

	public LocalDate getDt_agendamento() {
		return dt_agendamento;
	}

	public void setDt_agendamento(LocalDate dt_agendamento) {
		this.dt_agendamento = dt_agendamento;
	}

	public LocalDate getDt_consulta() {
		return dt_consulta;
	}

	public void setDt_consulta(LocalDate dt_consulta) {
		this.dt_consulta = dt_consulta;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

    
}