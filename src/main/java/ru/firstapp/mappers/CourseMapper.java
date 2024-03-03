package ru.firstapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.firstapp.dto.CourseDTO;
import ru.firstapp.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "teacher.id", target = "teacherId")
    CourseDTO toDTO(Course course);

    @Mapping(source = "teacherId", target = "teacher.id")
    Course fromDTO(CourseDTO courseDTO);
}
