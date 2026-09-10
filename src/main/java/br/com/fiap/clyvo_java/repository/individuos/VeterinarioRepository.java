package br.com.fiap.clyvo_java.repository.individuos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.clyvo_java.model.individuos.Veterinario;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM T_CLYVO_VETERINARIO WHERE UPPER(nm_veterinario) LIKE UPPER('%' || :substring || '%') ORDER BY nm_veterinario")
    List<Veterinario> retornarVeterinarioPorNome(String substring);
}
