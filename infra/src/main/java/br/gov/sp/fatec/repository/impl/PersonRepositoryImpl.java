package br.gov.sp.fatec.repository.impl;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.entities.PersonJpa;
import br.gov.sp.fatec.pagination.Page;
import br.gov.sp.fatec.pagination.PageRequest;
import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.repository.jpa.PersonRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PersonRepositoryImpl implements PersonPersistencePort {
    private final PersonRepositoryJpa personRepositoryJpa;

    public PersonRepositoryImpl(PersonRepositoryJpa personRepositoryJpa) {
        this.personRepositoryJpa = personRepositoryJpa;
    }

    @Override
    public Optional<Person> findById(UUID id) {
        return personRepositoryJpa.findByIdAndActiveTrue(id)
                .map(PersonJpa::toDomain);
    }

    @Override
    public List<Person> findAll() {
        return personRepositoryJpa.findByActiveTrue()
                .stream()
                .map(PersonJpa::toDomain)
                .toList();
    }

    @Override
    public Page<Person> findAllPaged(PageRequest pageRequest) {
        var pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.page(),
                pageRequest.size()
        );

        var page = personRepositoryJpa.findByActiveTrue(pageable);
        var persons = page.getContent()
                .stream()
                .map(PersonJpa::toDomain)
                .toList();
        return new Page<>(
                persons,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Override
    public Person save(Person entity) {
        var personJpa = PersonJpa.fromDomain(entity);
        var savedPersonJpa = personRepositoryJpa.save(personJpa);
        return savedPersonJpa.toDomain();
    }

    @Override
    public void deleteById(UUID id) {
        personRepositoryJpa.deleteById(id);
    }
}
