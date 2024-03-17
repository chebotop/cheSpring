package ru.firstapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Passport passport;

    private String firstName;
    private String middleName;
    private String lastName;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Passport implements Serializable {
        @Column(name = "passport_seria")
        private Integer passportSeria;

        @Column(name = "passport_number")
        private Integer passportNumber;
    }

    @OneToOne(mappedBy = "person", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Student student;



}