package br.gov.sp.fatec.events;

import br.gov.sp.fatec.ports.shared.EventEmitter;
import org.springframework.stereotype.Component;

@Component
public class EventEmitterLog implements EventEmitter {
    @Override
    public void emit(DomainEvent event) {
        System.out.println("Event emitted: " + event);
    }
}
