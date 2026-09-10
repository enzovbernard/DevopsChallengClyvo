package br.com.fiap.clyvo_java.repository.pet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.clyvo_java.model.pet.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
	
    @Query(nativeQuery = true, value = "SELECT * FROM T_CLYVO_ANIMAL WHERE UPPER(nm_animal) LIKE UPPER('%' || :substring || '%') ORDER BY nm_animal")
    List<Animal> retornarAnimalPorNome(String substring);
}
