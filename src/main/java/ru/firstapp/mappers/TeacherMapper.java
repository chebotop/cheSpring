package ru.firstapp.mappers;

import org.mapstruct.Mapper;
import ru.firstapp.dto.TeacherDTO;
import ru.firstapp.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teacher toTeacher(TeacherDTO teacherDTO);

    TeacherDTO toTeacherDTO(Teacher teacher);



}
