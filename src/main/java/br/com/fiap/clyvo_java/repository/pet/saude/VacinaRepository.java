package br.com.fiap.clyvo_java.repository.pet.saude;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.clyvo_java.model.pet.saude.Vacina;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM T_CLYVO_VACINA WHERE UPPER(nm_vacina) LIKE UPPER('%' || :substring || '%') ORDER BY nm_vacina")
    List<Vacina> retornarVacinaPorNome(String substring);
	
}
