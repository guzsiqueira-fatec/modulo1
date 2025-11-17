package br.gov.sp.fatec.events;

import br.gov.sp.fatec.vos.BirthDate;
import br.gov.sp.fatec.vos.Name;

import java.util.UUID;

public record PersonCreatedEvent(UUID id, Name name, BirthDate birthDate) {
}
