package br.com.fiap.clyvo_java.repository.individuos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.clyvo_java.model.individuos.Responsavel;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long>  {

	@Query(nativeQuery = true, value = "SELECT * FROM T_CLYVO_RESPONSAVEL WHERE UPPER(nm_responsavel) LIKE UPPER('%' || :substring || '%') ORDER BY nm_responsavel")
    List<Responsavel> retornarResponsaveisPorNome(String substring);
}
