package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.dtos.CreatePersonDto;
import br.gov.sp.fatec.dtos.PersonDto;
import br.gov.sp.fatec.dtos.UpdatePersonDto;
import br.gov.sp.fatec.pagination.Page;
import br.gov.sp.fatec.usecases.person.create.CreatePersonUseCase;
import br.gov.sp.fatec.usecases.person.delete.DeletePersonUseCase;
import br.gov.sp.fatec.usecases.person.delete.DeletePersonUseCaseInput;
import br.gov.sp.fatec.usecases.person.findAllPaged.FindAllPagedUseCase;
import br.gov.sp.fatec.usecases.person.findAllPaged.FindAllPagedUseCaseInput;
import br.gov.sp.fatec.usecases.person.findById.FindPersonByIdUseCase;
import br.gov.sp.fatec.usecases.person.findById.FindPersonByIdUseCaseInput;
import br.gov.sp.fatec.usecases.person.update.UpdatePersonUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
    public final CreatePersonUseCase createPersonUseCase;
    public final FindPersonByIdUseCase findPersonByIdUseCase;
    public final DeletePersonUseCase deletePersonUseCase;
    public final UpdatePersonUseCase updatePersonUseCase;
    public final FindAllPagedUseCase findAllPagedUseCase;

    public PersonController(CreatePersonUseCase createPersonUseCase, FindPersonByIdUseCase findPersonByIdUseCase, DeletePersonUseCase deletePersonUseCase , UpdatePersonUseCase updatePersonUseCase, FindAllPagedUseCase findAllPagedUseCase) {
        this.createPersonUseCase = createPersonUseCase;
        this.findPersonByIdUseCase = findPersonByIdUseCase;
        this.deletePersonUseCase = deletePersonUseCase;
        this.updatePersonUseCase = updatePersonUseCase;
        this.findAllPagedUseCase = findAllPagedUseCase;
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody @Valid CreatePersonDto createPersonDto) {
        var result = createPersonUseCase.execute(createPersonDto.toInput());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new PersonDto(
                    result.id(),
                    result.name(),
                    result.birthDate()
                )
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable UUID id) {
        var result = findPersonByIdUseCase.execute(new FindPersonByIdUseCaseInput(id));
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new PersonDto(
                    result.id(),
                    result.name(),
                    result.birthDate()
                )
            );
    }

    @GetMapping
    public ResponseEntity<Page<PersonDto>> getAllPersons(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        var result = findAllPagedUseCase.execute(new FindAllPagedUseCaseInput(page, size));
        var personDtos = result.items().stream()
            .map(person -> new PersonDto(
                person.id(),
                person.name(),
                person.birthDate()
            ))
            .toList();
        var responsePage = new Page<PersonDto>(
            personDtos,
            result.currentPage(),
            result.totalPages(),
            result.totalItems()
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responsePage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
        deletePersonUseCase.execute(new DeletePersonUseCaseInput(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable UUID id, @RequestBody @Valid UpdatePersonDto updatePersonDto) {
        var result = updatePersonUseCase.execute(updatePersonDto.toInput(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new PersonDto(
                                result.id(),
                                result.name(),
                                result.birthDate()
                        )
                );
    }
}
