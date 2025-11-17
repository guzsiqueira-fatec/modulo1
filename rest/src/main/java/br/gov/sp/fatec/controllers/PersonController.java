package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.dtos.CreatePersonDto;
import br.gov.sp.fatec.dtos.PersonDto;
import br.gov.sp.fatec.usecases.person.create.CreatePersonUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
    public final CreatePersonUseCase createPersonUseCase;

    public PersonController(CreatePersonUseCase createPersonUseCase) {
        this.createPersonUseCase = createPersonUseCase;
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
}
