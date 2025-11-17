package br.gov.sp.fatec.events.dtos;

public record PersonDeletedEventDto(String id) implements IntegrationEvent {
}
