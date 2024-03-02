package ru.firstapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true) // Включаем цепочку (chaining) для установки значений через методы-сеттеры
public class CourseDTO {
    private Long id;
    private String title;
    private String description;

    private Long teacherId;
    private List<Long> studentIds;

}

