package br.gov.sp.fatec.events;

import br.gov.sp.fatec.vos.BirthDate;
import br.gov.sp.fatec.vos.Name;

import java.util.UUID;

public class PersonUpdatedEvent extends DomainEvent {
    private final UUID id;
    private final Name name;
    private final BirthDate birthDate;

    public PersonUpdatedEvent(UUID id, Name name, BirthDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public BirthDate getBirthDate() {
        return birthDate;
    }
}


