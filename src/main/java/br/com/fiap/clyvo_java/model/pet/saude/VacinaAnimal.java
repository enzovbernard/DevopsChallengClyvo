package br.com.fiap.clyvo_java.model.pet.saude;

import java.time.LocalDate;

import br.com.fiap.clyvo_java.model.pet.Animal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "T_CLYVO_VACINA_ANIMAL")
public class VacinaAnimal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacina_animal")
    private Long id_vacina_animal;

    @NotNull(message = "O número de doses é obrigatório.")
    @Positive(message = "O número de doses deve ser maior que zero.")
    @Column(name = "num_doses")
    private Integer num_doses;
    
    @NotNull(message = "A frequência de aplicação é obrigatória.")
    @Positive(message = "A frequência de aplicação deve ser maior que zero.")
    @Column(name = "frequencia_aplicacao")
    private Integer frequencia_aplicacao;

    @PastOrPresent
    @Column(name = "dt_receita")
    private LocalDate dt_receita;

    @ManyToOne
    @JoinColumn(name = "fk_id_vacina")
    private Vacina vacina;

    @ManyToOne
    @JoinColumn(name = "fk_id_animal")
    private Animal animal;

    public VacinaAnimal() {}

	public VacinaAnimal(Long id_vacina_animal, Integer num_doses, Integer frequencia_aplicacao, LocalDate dt_receita, Vacina vacina, Animal animal) {
		this.id_vacina_animal = id_vacina_animal;
		this.num_doses = num_doses;
		this.frequencia_aplicacao = frequencia_aplicacao;
		this.dt_receita = dt_receita;
		this.vacina = vacina;
		this.animal = animal;
	}
	
	public void transferirVacinaAnimal(VacinaAnimal vacinaAnimal) {
		this.num_doses = vacinaAnimal.getNum_doses();
		this.frequencia_aplicacao = vacinaAnimal.getFrequencia_aplicacao();
		this.dt_receita = vacinaAnimal.getDt_receita();
		this.vacina = vacinaAnimal.getVacina();
		this.animal = vacinaAnimal.getAnimal();
	}

	public Long getId_vacina_animal() {
		return id_vacina_animal;
	}

	public void setId_vacina_animal(Long id_vacina_animal) {
		this.id_vacina_animal = id_vacina_animal;
	}

	public LocalDate getDt_receita() {
		return dt_receita;
	}

	public void setDt_receita(LocalDate dt_receita) {
		this.dt_receita = dt_receita;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Integer getNum_doses() {
		return num_doses;
	}

	public void setNum_doses(Integer num_doses) {
		this.num_doses = num_doses;
	}

	public Integer getFrequencia_aplicacao() {
		return frequencia_aplicacao;
	}

	public void setFrequencia_aplicacao(Integer frequencia_aplicacao) {
		this.frequencia_aplicacao = frequencia_aplicacao;
	}
	
	

    
}