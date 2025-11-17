package br.gov.sp.fatec.usecases.person.create;


import java.time.LocalDate;
import java.util.UUID;

public record CreatePersonUseCaseOutput(
    UUID id,
    String name,
    LocalDate birthDate
) {
}
