package br.com.fiap.clyvo_java.model.individuos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fiap.clyvo_java.model.pet.consultas.Consulta;
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
@Table(name = "T_CLYVO_VETERINARIO")
public class Veterinario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_veterinario")
	private Long id_veterinario;
	
	@Column(name = "nm_veterinario")
	@NotEmpty(message = "O nome do veterinário é obrigatório.")
	@Size(min = 1, max = 50, message = "O nome deve ter, no máximo, 50 caracteres.")
	private String nm_veterinario;
	
	@CPF(message = "O CPF informado é inválido.")
	@Column(name = "cpf")
	@Size(max = 14, message = "O CPF deve ter, no máximo, 14 caracteres.")
	private String cpf;
	
	@Column(name = "dt_nascimento")
	@Past(message = "A data de nascimento deve ser uma data passada.")
	private LocalDate dt_nascimento;
	
	@Column(name = "email")
	@Email(message = "O e-mail informado é inválido.")
	@Size(min = 1, max = 50, message = "O e-mail deve ter, no máximo, 50 caracteres.")
	private String email;
	
	@Column(name = "num_celular")
	@NotEmpty(message = "O telefone é obrigatório.")
	@Size(min = 1, max = 20, message = "O telefone deve ter, no máximo, 20 caracteres.")
	private String num_celular;
	
	@Column(name = "especialidade")
	@NotEmpty(message = "A especialidade é obrigatória.")
	@Size(min = 1, max = 50, message = "A especialidade deve ter, no máximo, 50 caracteres.")
	private String especialidade;
	
	public Veterinario() {}

	public Veterinario(Long id_veterinario, String nm_veterinario, String cpf, LocalDate dt_nascimento, String email, String num_celular, String especialidade) {
		
		this.id_veterinario = id_veterinario;
		this.nm_veterinario = nm_veterinario;
		this.cpf = cpf;
		this.dt_nascimento = dt_nascimento;
		this.email = email;
		this.num_celular = num_celular;
		this.especialidade = especialidade;
	}
	
	public void transferirVeterinario(Veterinario veterinario) {
		this.nm_veterinario = veterinario.getNm_veterinario();
		this.cpf = veterinario.getCpf();
		this.dt_nascimento = veterinario.getDt_nascimento();
		this.email = veterinario.getEmail();
		this.num_celular = veterinario.getNum_celular();
		this.especialidade = veterinario.getEspecialidade();
	}
	
	@JsonIgnore
	@OneToMany(
		    mappedBy = "veterinario",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true)
	private List<Consulta> consultas = new ArrayList<>();
	
	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public Long getId_veterinario() {
		return id_veterinario;
	}

	public void setId_veterinario(Long id_veterinario) {
		this.id_veterinario = id_veterinario;
	}

	public String getNm_veterinario() {
		return nm_veterinario;
	}

	public void setNm_veterinario(String nm_veterinario) {
		this.nm_veterinario = nm_veterinario;
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

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

}