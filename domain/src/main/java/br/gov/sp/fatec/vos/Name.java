package br.gov.sp.fatec.vos;

public record Name(
        String firstName,
        String lastName
) {
    public Name {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
    }

    String fullName() {
        return firstName + " " + lastName;
    }
}
