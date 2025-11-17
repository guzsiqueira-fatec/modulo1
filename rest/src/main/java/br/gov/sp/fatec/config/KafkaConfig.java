package br.gov.sp.fatec.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;


@EnableKafka
@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic personCreatedTopic() {
        return TopicBuilder
            .name("person.created")
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic personDeletedTopic() {
        return TopicBuilder
            .name("person.deleted")
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic personUpdatedTopic() {
        return TopicBuilder
            .name("person.updated")
            .partitions(3)
            .replicas(1)
            .build();
    }
}
