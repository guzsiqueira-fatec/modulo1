package br.gov.sp.fatec.events.dtos;

public record PersonCreatedEventDto(
        String id,
        String name,
        String birthDate
) implements IntegrationEvent {

}
