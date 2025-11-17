package br.gov.sp.fatec.usecases.person.update;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.events.PersonUpdatedEvent;
import br.gov.sp.fatec.exceptions.PersonNotFoundException;
import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.ports.shared.EventEmitterPort;
import br.gov.sp.fatec.usecases.person.shared.PersonOutput;
import br.gov.sp.fatec.usecases.shared.UseCase;
import br.gov.sp.fatec.vos.BirthDate;
import br.gov.sp.fatec.vos.Name;

import java.util.Optional;

public class UpdatePersonUseCase implements UseCase<UpdatePersonUseCaseInput, PersonOutput> {
    private final PersonPersistencePort personPersistencePort;
    private final EventEmitterPort eventEmitterPort;

    public UpdatePersonUseCase(PersonPersistencePort personPersistencePort, EventEmitterPort eventEmitterPort) {
        this.personPersistencePort = personPersistencePort;
        this.eventEmitterPort = eventEmitterPort;
    }

    @Override
    public PersonOutput execute(UpdatePersonUseCaseInput input) {
        Person existingPerson = personPersistencePort.findById(input.id())
                .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + input.id()));


        Person updatedPerson = new Person(
                existingPerson.id(),
                new Name(input.name()),
                BirthDate.of(input.birthDate()),
                existingPerson.active()
        );

        Person savedPerson = personPersistencePort.save(updatedPerson);

        PersonUpdatedEvent event = new PersonUpdatedEvent(
                savedPerson.id(),
                savedPerson.name(),
                savedPerson.birthDate()
        );

        eventEmitterPort.emit(event);

        return new PersonOutput(
                savedPerson.id(),
                savedPerson.name().name(),
                savedPerson.birthDate().toLocalDate()
        );
    }
}

