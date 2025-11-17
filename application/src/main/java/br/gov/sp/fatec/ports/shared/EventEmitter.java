package br.gov.sp.fatec.ports.shared;

import br.gov.sp.fatec.events.DomainEvent;

public interface EventEmitter {
    void emit(DomainEvent event);
}
