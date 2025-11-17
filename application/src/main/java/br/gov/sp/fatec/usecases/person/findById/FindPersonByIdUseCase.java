package br.gov.sp.fatec.usecases.person.findById;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.exceptions.PersonNotFoundException;
import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.usecases.person.shared.PersonOutput;
import br.gov.sp.fatec.usecases.shared.UseCase;

import java.util.Optional;

public class FindPersonByIdUseCase implements UseCase<FindPersonByIdUseCaseInput, PersonOutput> {
    private final PersonPersistencePort personPersistencePort;

    public FindPersonByIdUseCase(PersonPersistencePort personPersistencePort) {
        this.personPersistencePort = personPersistencePort;
    }

    @Override
    public PersonOutput execute(FindPersonByIdUseCaseInput input) {
        Person person = personPersistencePort.findById(input.id())
                .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + input.id()));


        return new PersonOutput(
                person.id(),
                person.name().name(),
                person.birthDate().toLocalDate()
        );
    }
}
