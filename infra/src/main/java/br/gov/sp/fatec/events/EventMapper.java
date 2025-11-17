package br.gov.sp.fatec.events;

import br.gov.sp.fatec.events.dtos.IntegrationEvent;
import br.gov.sp.fatec.events.dtos.PersonCreatedEventDto;
import br.gov.sp.fatec.events.dtos.PersonDeletedEventDto;
import br.gov.sp.fatec.events.dtos.PersonUpdatedEventDto;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public IntegrationEvent map(DomainEvent event) {
        return switch (event) {
            case PersonCreatedEvent e -> new PersonCreatedEventDto(
                    e.getId().toString(),
                    e.getName().name(),
                    e.getBirthDate().toLocalDate().toString()
            );
            case PersonUpdatedEvent e -> new PersonUpdatedEventDto(
                    e.getId().toString(),
                    e.getName().name(),
                    e.getBirthDate().toLocalDate().toString()
            );
            case PersonDeletedEvent e -> new PersonDeletedEventDto(
                    e.getId().toString()
            );
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };
    }
}
