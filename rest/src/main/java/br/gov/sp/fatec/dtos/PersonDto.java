package br.gov.sp.fatec.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record PersonDto(
        UUID id,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate birthDate
) {
}
