package br.gov.sp.fatec.dtos;

import br.gov.sp.fatec.usecases.person.update.UpdatePersonUseCaseInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record UpdatePersonDto(
    @NotBlank
    String name,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull
    @Past
    LocalDate birthDate
) {
    public UpdatePersonUseCaseInput toInput(UUID id) {
        return new UpdatePersonUseCaseInput(
            id,
            this.name,
            this.birthDate
        );
    }
}
