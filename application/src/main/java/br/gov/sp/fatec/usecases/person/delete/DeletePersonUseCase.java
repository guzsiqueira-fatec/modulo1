package br.gov.sp.fatec.usecases.person.delete;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.events.PersonDeletedEvent;
import br.gov.sp.fatec.exceptions.PersonNotFoundException;
import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.ports.shared.EventEmitter;
import br.gov.sp.fatec.usecases.shared.UseCase;

public class DeletePersonUseCase implements UseCase<DeletePersonUseCaseInput, Void> {
    private final PersonPersistencePort personPersistencePort;
    private final EventEmitter eventEmitter;

    public DeletePersonUseCase(PersonPersistencePort personPersistencePort, EventEmitter eventEmitter) {
        this.personPersistencePort = personPersistencePort;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public Void execute(DeletePersonUseCaseInput input) {
        Person person = personPersistencePort.findById(input.id()).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + input.id()));

        personPersistencePort.deleteById(input.id());

        PersonDeletedEvent event = new PersonDeletedEvent(input.id());
        eventEmitter.emit(event);

        return null;
    }
}

