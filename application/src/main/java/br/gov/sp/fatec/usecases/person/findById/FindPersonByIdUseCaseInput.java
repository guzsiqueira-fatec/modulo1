package br.gov.sp.fatec.usecases.person.findById;

import java.util.UUID;

public record FindPersonByIdUseCaseInput(
    UUID id
) {
}
