package ru.firstapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    private Long id;
    private String title; // Название курса
    private String description; // Описание курса

    @ManyToOne // связь курса с преподавателем
    @JoinColumn(name="teacher_id") // внешний ключ для преподавателя
    private Teacher teacher;
}
