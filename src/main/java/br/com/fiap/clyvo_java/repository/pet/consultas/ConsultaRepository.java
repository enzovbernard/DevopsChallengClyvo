package br.com.fiap.clyvo_java.repository.pet.consultas;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.clyvo_java.model.pet.consultas.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	@Query("SELECT COUNT(c) > 0 FROM Consulta c WHERE c.veterinario.id_veterinario = :idVeterinario AND c.dt_consulta = :dtConsulta")
	boolean existeConsultaMesmoVeterinarioMesmoDia(@Param("idVeterinario") Long idVeterinario, @Param("dtConsulta") LocalDate dtConsulta);

}