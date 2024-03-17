package ru.firstapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private Long code;

    @OneToOne(mappedBy = "recordBook", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) // надо жадно загрузить студента
    @JsonIgnore
    private Student student;
}
