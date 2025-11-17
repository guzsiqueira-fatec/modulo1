package br.gov.sp.fatec.ports.person;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.pagination.Page;
import br.gov.sp.fatec.pagination.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonPersistencePort {
    Optional<Person> findById(UUID id);
    List<Person> findAll();
    Page<Person> findAllPaged(PageRequest pageRequest);
    Person save(Person entity);
    void deleteById(UUID id);
}
