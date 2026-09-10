package br.com.fiap.clyvo_java.model.pet.saude;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "T_CLYVO_DOENCA")
public class Doenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doenca")
    private Long id_doenca;

    @Column(name = "nm_doenca")
    @NotEmpty(message = "O nome da doença é obrigatório.")
    @Size(max = 50, message = "O nome da doença deve ter, no máximo, 50 caracteres.")
    private String nm_doenca;

    @Column(name = "tipo")
    @NotEmpty(message = "O tipo da doença é obrigatório.")
    @Size(max = 50, message = "O tipo deve ter, no máximo, 50 caracteres.")
    private String tipo;

    @Size(max = 1)
    @Column(name = "contagiosidade")
    private String contagiosidade;

    @Lob
    @Column(name = "descricao")
    private String descricao;
    
    public Doenca() {}

	public Doenca(Long id_doenca, String nm_doenca, String tipo,
			String contagiosidade, String descricao) {
		super();
		this.id_doenca = id_doenca;
		this.nm_doenca = nm_doenca;
		this.tipo = tipo;
		this.contagiosidade = contagiosidade;
		this.descricao = descricao;
	}
	
	public void transferirDoenca(Doenca doenca) {
		this.nm_doenca = doenca.getNm_doenca();
		this.tipo = doenca.getTipo();
		this.contagiosidade = doenca.getContagiosidade();
		this.descricao = doenca.getDescricao();
	}
	
	@JsonIgnore
	@OneToMany(
		    mappedBy = "doenca",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	private List<DoencaAnimal> animaisDoentes = new ArrayList<>();
	
	

	public List<DoencaAnimal> getAnimaisDoentes() {
		return animaisDoentes;
	}

	public void setAnimaisDoentes(List<DoencaAnimal> animaisDoentes) {
		this.animaisDoentes = animaisDoentes;
	}

	public Long getId_doenca() {
		return id_doenca;
	}

	public void setId_doenca(Long id_doenca) {
		this.id_doenca = id_doenca;
	}

	public String getNm_doenca() {
		return nm_doenca;
	}

	public void setNm_doenca(String nm_doenca) {
		this.nm_doenca = nm_doenca;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getContagiosidade() {
		return contagiosidade;
	}

	public void setContagiosidade(String contagiosidade) {
		this.contagiosidade = contagiosidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}