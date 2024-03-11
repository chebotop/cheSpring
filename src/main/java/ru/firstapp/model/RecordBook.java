package ru.firstapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RecordBook {
    @Id
    private Long id;
    @Column(unique = true)
    private Long code;

    @OneToMany(mappedBy = "recordBook")
    @JsonBackReference
    private List<Student> students;
}
