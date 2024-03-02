package ru.firstapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

// через ломбок добавить геттеры

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    private Long id;
    private String title; // Название курса
    private String description; // Описание курса

    @ManyToOne
    @JoinColumn(name = "teacher_id") // Внешний ключ для связи с преподавателем
    private Teacher teacher;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students; // Используем Set для предотвращения дубликатов
}
