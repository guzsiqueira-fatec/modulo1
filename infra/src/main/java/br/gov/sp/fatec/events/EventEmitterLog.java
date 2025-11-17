package br.gov.sp.fatec.events;

import br.gov.sp.fatec.ports.shared.EventEmitterPort;
import org.springframework.stereotype.Component;

@Component
public class EventEmitterLog implements EventEmitterPort {
    @Override
    public void emit(DomainEvent event) {
        System.out.println("Event emitted: " + event);
    }
}
