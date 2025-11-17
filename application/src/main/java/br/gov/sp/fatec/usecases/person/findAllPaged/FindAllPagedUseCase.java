package br.gov.sp.fatec.usecases.person.findAllPaged;

import br.gov.sp.fatec.entities.Person;
import br.gov.sp.fatec.pagination.Page;
import br.gov.sp.fatec.pagination.PageRequest;
import br.gov.sp.fatec.ports.person.PersonPersistencePort;
import br.gov.sp.fatec.usecases.person.shared.PersonOutput;
import br.gov.sp.fatec.usecases.shared.UseCase;

import java.util.ArrayList;
import java.util.List;

public class FindAllPagedUseCase implements UseCase<FindAllPagedUseCaseInput, Page<PersonOutput>> {
    private final PersonPersistencePort personPersistencePort;

    public FindAllPagedUseCase(PersonPersistencePort personPersistencePort) {
        this.personPersistencePort = personPersistencePort;
    }

    @Override
    public Page<PersonOutput> execute(FindAllPagedUseCaseInput input) {
        PageRequest pageRequest = new PageRequest(input.page(), input.size());

        Page<Person> personPage = personPersistencePort.findAllPaged(pageRequest);

        return new Page<>(
            personPage.items().stream()
                .map(PersonOutput::fromDomain)
                .toList(),
            personPage.currentPage(),
            personPage.totalPages(),
            personPage.totalItems()
        );
    }
}

