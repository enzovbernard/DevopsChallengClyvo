package br.com.fiap.clyvo_java.model.pet.consultas;

import java.time.LocalDate;

import br.com.fiap.clyvo_java.model.pet.Animal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "T_CLYVO_HISTORICO")
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico")
    private Long id_historico;

    @Lob
    @Column(name = "link_historico")
    private String link_historico;

    @PastOrPresent
    @Column(name = "dt_criacao_historico")
    private LocalDate dt_criacao_historico;

    private Double num_consultas_clyvo;

    @ManyToOne
    @JoinColumn(name = "fk_id_consulta")
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "fk_id_animal")
    private Animal animal;

    public Historico() {}

	public Historico(Long id_historico, String link_historico, LocalDate dt_criacao_historico,
			Double num_consultas_clyvo, Consulta consulta, Animal animal) {
		super();
		this.id_historico = id_historico;
		this.link_historico = link_historico;
		this.dt_criacao_historico = dt_criacao_historico;
		this.num_consultas_clyvo = num_consultas_clyvo;
		this.consulta = consulta;
		this.animal = animal;
	}
	
	public void transferirHistorico(Historico historico) {
		this.link_historico = historico.getLink_historico();
		this.dt_criacao_historico = historico.getDt_criacao_historico();
		this.num_consultas_clyvo = historico.getNum_consultas_clyvo();
		this.consulta = historico.getConsulta();
		this.animal = historico.getAnimal();
	}

	public Long getId_historico() {
		return id_historico;
	}

	public void setId_historico(Long id_historico) {
		this.id_historico = id_historico;
	}

	public String getLink_historico() {
		return link_historico;
	}

	public void setLink_historico(String link_historico) {
		this.link_historico = link_historico;
	}

	public LocalDate getDt_criacao_historico() {
		return dt_criacao_historico;
	}

	public void setDt_criacao_historico(LocalDate dt_criacao_historico) {
		this.dt_criacao_historico = dt_criacao_historico;
	}

	public Double getNum_consultas_clyvo() {
		return num_consultas_clyvo;
	}

	public void setNum_consultas_clyvo(Double num_consultas_clyvo) {
		this.num_consultas_clyvo = num_consultas_clyvo;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

    
   
}