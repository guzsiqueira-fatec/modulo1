package br.gov.sp.fatec.events.dtos;

public record PersonUpdatedEventDto (
        String id,
        String name,
        String birthDate
) implements IntegrationEvent {
}
