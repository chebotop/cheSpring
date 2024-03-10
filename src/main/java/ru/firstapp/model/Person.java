package ru.firstapp.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

/* честное слово, так ведь приятно. просто забить аннотации типа Data, AllArgsConstructor, Column
* нежели прописывать бойлерплейты */
@Data
@AllArgsConstructor
public class Person {
    private Long id;
    @Column(unique = true)
    private Integer passportSeria;
    @Column(unique = true)
    private Integer passportNumber;
    private String firstName;
    private String middleName;
    private String lastName;


}
