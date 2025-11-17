package br.gov.sp.fatec.usecases.person.update;

import java.time.LocalDate;
import java.util.UUID;

public record UpdatePersonUseCaseInput(
    UUID id,
    String name,
    LocalDate birthDate
) {
}


