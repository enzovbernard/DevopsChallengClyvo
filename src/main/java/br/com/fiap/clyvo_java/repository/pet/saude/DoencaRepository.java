package br.com.fiap.clyvo_java.repository.pet.saude;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.clyvo_java.model.pet.saude.Doenca;

public interface DoencaRepository extends JpaRepository<Doenca, Long>  {
	
	@Query(nativeQuery = true, value = "SELECT * FROM T_CLYVO_DOENCA WHERE UPPER(nm_doenca) LIKE UPPER('%' || :substring || '%') ORDER BY nm_doenca")
    List<Doenca> retornarDoencaPorNome(String substring);

}
