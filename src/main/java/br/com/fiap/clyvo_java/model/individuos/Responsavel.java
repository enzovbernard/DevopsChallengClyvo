package br.com.fiap.clyvo_java.model.individuos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fiap.clyvo_java.model.pet.Animal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVO_RESPONSAVEL")
public class Responsavel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_responsavel")
	private Long id_responsavel;
	
	@Column(name = "nm_responsavel")
	@NotEmpty(message = "O nome do responsável é obrigatório.")
	@Size(min = 1, max = 50, message = "O nome deve ter, no máximo, 50 caracteres.")
	private String nm_responsavel;
	
	@CPF(message = "O CPF informado é inválido.")
	@Column(name = "cpf")
	@Size(max = 14, message = "O CPF deve ter, no máximo, 14 caracteres.")
	private String cpf;

	@Column(name = "dt_nascimento")
	@Past(message = "A data de nascimento deve ser uma data passada.")
	private LocalDate dt_nascimento;
	
	@Email(message = "O e-mail informado é inválido.")
	@Column(name = "email")
	@NotEmpty(message = "O e-mail é obrigatório.")
	@Size(min = 1, max = 50, message = "O e-mail deve ter, no máximo, 50 caracteres.")
	private String email;
	
	@Column(name = "num_celular")
	@NotEmpty(message = "O telefone é obrigatório.")
	@Size(min = 1, max = 20, message = "O telefone deve ter, no máximo, 20 caracteres.")
	private String num_celular;
	
	
	public Responsavel() {}
	
	public Responsavel(Long id_responsavel, String nm_responsavel, String cpf, LocalDate dt_nascimento, String email,
			String num_celular) {
		super();
		this.id_responsavel = id_responsavel;
		this.nm_responsavel = nm_responsavel;
		this.cpf = cpf;
		this.dt_nascimento = dt_nascimento;
		this.email = email;
		this.num_celular = num_celular;
	}
	
	public void transferirResponsavel(Responsavel responsavel) {
		this.nm_responsavel = responsavel.getNm_responsavel();
		this.cpf = responsavel.getCpf();
		this.dt_nascimento = responsavel.getDt_nascimento();
		this.email = responsavel.getEmail();
		this.num_celular = responsavel.getNum_celular();
	}
	
	@JsonIgnore
	@OneToMany(
		    mappedBy = "responsavel",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true)
	private List<Animal> animais = new ArrayList<>();
	
	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	public Long getId_responsavel() {
		return id_responsavel;
	}
	public void setId_responsavel(Long id_responsavel) {
		this.id_responsavel = id_responsavel;
	}
	public String getNm_responsavel() {
		return nm_responsavel;
	}
	public void setNm_responsavel(String nm_responsavel) {
		this.nm_responsavel = nm_responsavel;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LocalDate getDt_nascimento() {
		return dt_nascimento;
	}
	public void setDt_nascimento(LocalDate dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNum_celular() {
		return num_celular;
	}
	public void setNum_celular(String num_celular) {
		this.num_celular = num_celular;
	}
}