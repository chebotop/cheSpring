package ru.firstapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.firstapp.dto.StudentDTO;
import ru.firstapp.model.Course;
import ru.firstapp.model.Student;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface StudentMapper {
    @Mapping(target = "courseIds", expression = "java(toCourseIds(student.getCourses()))")
    StudentDTO toDTO(Student student);

    @Mapping(target = "courses", ignore = true)
    Student fromDTO(StudentDTO studentDTO);


    default List<Long> toCourseIds(List<Course> courses) {
        if (courses == null) {
            return null;
        }
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toList());
    }
}

