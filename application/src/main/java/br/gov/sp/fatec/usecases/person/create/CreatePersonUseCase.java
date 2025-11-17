package br.gov.sp.fatec.usecases.person.create;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.events.PersonCreatedEvent;
import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.ports.shared.EventEmitterPort;
import br.gov.sp.fatec.usecases.shared.UseCase;
import br.gov.sp.fatec.vos.BirthDate;
import br.gov.sp.fatec.vos.Name;

import java.util.UUID;

public class CreatePersonUseCase implements UseCase<CreatePersonUseCaseInput, CreatePersonUseCaseOutput> {
    private final PersonPersistencePort personPersistencePort;
    private final EventEmitterPort eventEmitterPort;

    public CreatePersonUseCase(PersonPersistencePort personPersistencePort, EventEmitterPort eventEmitterPort) {
        this.personPersistencePort = personPersistencePort;
        this.eventEmitterPort = eventEmitterPort;
    }

    @Override
    public CreatePersonUseCaseOutput execute(CreatePersonUseCaseInput input) {
        Person person = new Person(
                UUID.randomUUID(),
                new Name(input.name()),
                BirthDate.of(input.birthDate()),
                true
        );
        Person savedPerson = personPersistencePort.save(person);

        PersonCreatedEvent event = new PersonCreatedEvent(
                savedPerson.id(),
                savedPerson.name(),
                savedPerson.birthDate()
        );

        eventEmitterPort.emit(event);

        return new CreatePersonUseCaseOutput(
                savedPerson.id(),
                savedPerson.name().name(),
                savedPerson.birthDate().toLocalDate()
        );
    }
}
