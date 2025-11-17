package br.gov.sp.fatec.repository.jpa;

import br.gov.sp.fatec.entities.PersonJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepositoryJpa extends JpaRepository<PersonJpa, UUID> {
    Optional<PersonJpa> findByIdAndActiveTrue(UUID id);
    List<PersonJpa> findByActiveTrue();
    Page<PersonJpa> findByActiveTrue(Pageable pageable);
}
