package br.gov.sp.fatec.config;

import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.ports.shared.EventEmitterPort;
import br.gov.sp.fatec.usecases.person.create.CreatePersonUseCase;
import br.gov.sp.fatec.usecases.person.delete.DeletePersonUseCase;
import br.gov.sp.fatec.usecases.person.findAllPaged.FindAllPagedUseCase;
import br.gov.sp.fatec.usecases.person.findById.FindPersonByIdUseCase;
import br.gov.sp.fatec.usecases.person.update.UpdatePersonUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonUseCaseConfig {
    @Bean
    public CreatePersonUseCase createPersonUseCase(PersonPersistencePort personPersistencePort, EventEmitterPort eventEmitterPort) {
        return new CreatePersonUseCase(personPersistencePort, eventEmitterPort);
    }

    @Bean
    public DeletePersonUseCase deletePersonUseCase(PersonPersistencePort personPersistencePort, EventEmitterPort eventEmitterPort) {
        return new DeletePersonUseCase(personPersistencePort, eventEmitterPort);
    }

    @Bean
    public FindAllPagedUseCase findAllPagedUseCase(PersonPersistencePort personPersistencePort) {
        return new FindAllPagedUseCase(personPersistencePort);
    }

    @Bean
    public UpdatePersonUseCase updatePersonUseCase(PersonPersistencePort personPersistencePort, EventEmitterPort eventEmitterPort) {
        return new UpdatePersonUseCase(personPersistencePort, eventEmitterPort);
    }

    @Bean
    public FindPersonByIdUseCase findPersonByIdUseCase(PersonPersistencePort personPersistencePort) {
        return new FindPersonByIdUseCase(personPersistencePort);
    }
}
