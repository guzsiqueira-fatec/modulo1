package br.gov.sp.fatec.usecases.person.create;

import java.time.LocalDate;

public record CreatePersonUseCaseInput(
    String name,
    LocalDate birthDate
) {

}
