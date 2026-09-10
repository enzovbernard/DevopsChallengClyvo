package br.com.fiap.clyvo_java.repository.pet.saude;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.clyvo_java.model.pet.saude.VacinaAnimal;

public interface VacinaAnimalRepository extends JpaRepository<VacinaAnimal, Long> {

	@Query("SELECT va FROM VacinaAnimal va WHERE va.animal.id_animal = :idAnimal AND va.vacina.id_vacina = :idVacina ORDER BY va.dt_receita DESC")
	List<VacinaAnimal> buscarAplicacoesAnteriores(@Param("idAnimal") Long idAnimal, @Param("idVacina") Long idVacina);

}