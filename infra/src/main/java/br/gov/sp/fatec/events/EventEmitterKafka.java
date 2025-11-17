package br.gov.sp.fatec.events;

import br.gov.sp.fatec.events.dtos.IntegrationEvent;
import br.gov.sp.fatec.ports.shared.EventEmitter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventEmitterKafka implements EventEmitter {
    private final EventMapper eventMapper;
    private final TopicResolver topicResolver;
    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

    public EventEmitterKafka(EventMapper eventMapper, TopicResolver topicResolver, KafkaTemplate<String, IntegrationEvent> kafkaTemplate) {
        this.eventMapper = eventMapper;
        this.topicResolver = topicResolver;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void emit(DomainEvent event) {
        var topic = topicResolver.resolve(event);
        var integrationEvent = eventMapper.map(event);
        kafkaTemplate.send(topic, integrationEvent);
    }
}
