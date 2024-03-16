package ru.firstapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* честное слово, так ведь приятно. просто забить аннотации типа Data, AllArgsConstructor, Column
* нежели прописывать бойлерплейты */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person",
        uniqueConstraints = @UniqueConstraint(columnNames = {"passportSerial", "passportNumber"}))
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private Integer passportSerial;
    private Integer passportNumber;
    private String firstName;
    private String middleName;
    private String lastName;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonManagedReference
    private Student student;
}
