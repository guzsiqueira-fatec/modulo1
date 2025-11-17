package br.gov.sp.fatec.usecases.person.create;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.events.PersonCreatedEvent;
import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.ports.shared.EventEmitter;
import br.gov.sp.fatec.usecases.person.shared.PersonOutput;
import br.gov.sp.fatec.usecases.shared.UseCase;
import br.gov.sp.fatec.vos.BirthDate;
import br.gov.sp.fatec.vos.Name;

import java.util.UUID;

public class CreatePersonUseCase implements UseCase<CreatePersonUseCaseInput, PersonOutput> {
    private final PersonPersistencePort personPersistencePort;
    private final EventEmitter eventEmitter;

    public CreatePersonUseCase(PersonPersistencePort personPersistencePort, EventEmitter eventEmitter) {
        this.personPersistencePort = personPersistencePort;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public PersonOutput execute(CreatePersonUseCaseInput input) {
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

        eventEmitter.emit(event);

        return new PersonOutput(
                savedPerson.id(),
                savedPerson.name().name(),
                savedPerson.birthDate().toLocalDate()
        );
    }
}
