package br.gov.sp.fatec.config;

import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.ports.shared.EventEmitterPort;
import br.gov.sp.fatec.usecases.person.create.CreatePersonUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonUseCaseConfig {
    @Bean
    public CreatePersonUseCase createPersonUseCase(PersonPersistencePort personPersistencePort, EventEmitterPort eventEmitterPort) {
        return new CreatePersonUseCase(personPersistencePort, eventEmitterPort);
    }
}
