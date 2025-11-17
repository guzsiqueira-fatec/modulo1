package br.gov.sp.fatec.entities;

import br.gov.sp.fatec.vos.BirthDate;
import br.gov.sp.fatec.vos.Name;

import java.util.UUID;

public record Person(
    UUID id,
    Name name,
    BirthDate birthDate,
    boolean active
) {
}
