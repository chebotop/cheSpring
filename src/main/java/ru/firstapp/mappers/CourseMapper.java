package ru.firstapp.mappers;

import org.mapstruct.*;
import ru.firstapp.dto.CourseDTO;
import ru.firstapp.model.Course;
import ru.firstapp.model.Student;
import ru.firstapp.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface CourseMapper {

    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "studentIds", ignore = true)
    CourseDTO toDTO(Course course);

    @AfterMapping
    default void fillStudentIds(@MappingTarget CourseDTO courseDTO, Course course, @Context StudentService studentService) {
        List<Long> studentIds = studentService.findAllStudentsByCourseId(course.getId())
                .stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        courseDTO.setStudentIds(studentIds);
    }

    @Mapping(target = "teacher", ignore=true)
    @Mapping(target = "students", ignore=true)
    Course fromDTO(CourseDTO courseDTO);
}

