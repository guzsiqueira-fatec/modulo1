package br.gov.sp.fatec.vos;

import java.time.LocalDate;
import java.time.Period;

public record BirthDate(
    int day,
    int month,
    int year
) {
    public BirthDate {
        var date = LocalDate.of(year, month, day);
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }
    public static BirthDate of(
        int day,
        int month,
        int year
    ) {
        return new BirthDate(day, month, year);
    }

    public static BirthDate of(LocalDate date) {
        return new BirthDate(
            date.getDayOfMonth(),
            date.getMonthValue(),
            date.getYear()
        );
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }

    public int age() {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = this.toLocalDate();
        var diff = Period.between(birthDate, today);
        return diff.getYears();
    }
}
