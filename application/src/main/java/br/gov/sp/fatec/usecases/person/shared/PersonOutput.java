package br.gov.sp.fatec.usecases.person.shared;

import br.gov.sp.fatec.entities.Person;

import java.time.LocalDate;
import java.util.UUID;

public record PersonOutput(
    UUID id,
    String name,
    LocalDate birthDate
) {
    public static PersonOutput fromDomain(Person person) {
        return new PersonOutput(
            person.id(),
            person.name().name(),
            person.birthDate().toLocalDate()
        );
    }
}
