package br.gov.sp.fatec.dtos;

import br.gov.sp.fatec.usecases.person.create.CreatePersonUseCaseInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreatePersonDto(
    @NotBlank
    String name,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull
    @Past
    LocalDate birthDate
) {
    public CreatePersonUseCaseInput toInput() {
        return new CreatePersonUseCaseInput(name, birthDate);
    }
}
