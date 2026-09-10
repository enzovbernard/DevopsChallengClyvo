package br.com.fiap.clyvo_java.repository.pet.saude;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.clyvo_java.model.pet.saude.DoencaAnimal;

public interface DoencaAnimalRepository extends JpaRepository<DoencaAnimal, Long> {

}
