package br.gov.sp.fatec.events;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TopicResolver {
    private static final Map<Class<? extends DomainEvent>, String> TOPICS = Map.of(
        PersonDeletedEvent.class, "person.deleted",
        PersonCreatedEvent.class, "person.created",
        PersonUpdatedEvent.class, "person.updated"
    );

    public String resolve(DomainEvent event) {
        return TOPICS.get(event.getClass());
    }
}
