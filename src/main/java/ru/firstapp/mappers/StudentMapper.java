package ru.firstapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.firstapp.dto.StudentDTO;
import ru.firstapp.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "")
    StudentDTO toDTO(Student student);
}

