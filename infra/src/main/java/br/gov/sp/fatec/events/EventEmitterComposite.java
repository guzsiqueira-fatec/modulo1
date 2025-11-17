package br.gov.sp.fatec.events;

import br.gov.sp.fatec.ports.shared.EventEmitter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Primary
public class EventEmitterComposite implements EventEmitter {
    private final List<EventEmitter> emitters;

    public EventEmitterComposite(List<EventEmitter> emitters) {
        this.emitters = emitters
                .stream().filter(
                        emitter -> !(emitter instanceof EventEmitterComposite)
                )
                .toList();
    }

    @Override
    public void emit(DomainEvent event) {
        for (EventEmitter emitter : emitters) {
            emitter.emit(event);
        }
    }
}
