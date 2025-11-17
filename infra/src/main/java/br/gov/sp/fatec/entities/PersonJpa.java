package br.gov.sp.fatec.entities;

import br.gov.sp.fatec.vos.BirthDate;
import br.gov.sp.fatec.vos.Name;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "PERSONS")
@SQLDelete(sql = "UPDATE PERSONS SET PERS_ACTIVE = false WHERE PERS_ID = ?")
public class PersonJpa {

    @Id
    @Column(name = "PRSN_ID", nullable = false)
    private UUID id;

    @Column(name = "PRSN_NAME", nullable = false)
    private String name;

    @Column(name = "PRSN_BIRTHDATE", nullable = false)
    private LocalDate birthdate;

    @Column(name = "PRSN_ACTIVE", nullable = false)
    private boolean active;

    protected PersonJpa() {
    }

    public PersonJpa(UUID id, String name, LocalDate birthdate, boolean active) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.active = active;
    }

    public static PersonJpa fromDomain(Person person) {
        return new PersonJpa(
                person.id(),
                person.name().name(),
                person.birthDate().toLocalDate(),
                person.active()
        );
    }

    public Person toDomain() {
        return new Person(
                this.id,
                new Name(this.name),
                BirthDate.of(this.birthdate),
                this.active
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public boolean isActive() {
        return active;
    }
}

