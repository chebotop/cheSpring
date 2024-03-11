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
public class Person {
    @Id
    private Long id;
    @Column(unique = true)
    private Integer passportSerial;
    @Column(unique = true)
    private Integer passportNumber;
    private String firstName;
    private String middleName;
    private String lastName;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonManagedReference
    private Student student;


}
