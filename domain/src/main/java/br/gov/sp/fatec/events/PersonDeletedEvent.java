package br.gov.sp.fatec.events;

import java.util.UUID;

public class PersonDeletedEvent extends DomainEvent {
    private final UUID id;

    public PersonDeletedEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

