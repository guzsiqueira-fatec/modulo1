package br.gov.sp.fatec.vos;

public record Name(
        String name
) {
    public Name {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
    }
}
