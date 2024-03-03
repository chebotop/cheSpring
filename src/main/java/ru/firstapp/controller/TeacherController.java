package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.dto.TeacherDTO;
import ru.firstapp.mappers.TeacherMapper;
import ru.firstapp.model.Course;
import ru.firstapp.model.Teacher;
import ru.firstapp.service.CourseService;
import ru.firstapp.service.TeacherService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService service;
    private final CourseService courseService;
    private final TeacherMapper teacherMapper;
    @GetMapping
    public List<Teacher> findAllTeacher() {
        return service.findAllTeacher();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_teacher")// сделать метод POST
    public ResponseEntity<TeacherDTO> saveTeacher(@RequestBody TeacherDTO teacherDTO) {
        // Шаг 1: Преобразование из TeacherDTO в сущность Teacher
//        Teacher teacher = convertToEntity(teacherDTO);
        Teacher teacher = teacherMapper.toTeacher(teacherDTO);
        // Шаг 2: Сохранение сущности Teacher в базу данных
        Teacher savedTeacher = service.saveTeacher(teacher);
        // Шаг 3: Преобразование сохраненного учителя обратно в TeacherDTO
//        TeacherDTO savedTeacherDTO = convertToDTO(savedTeacher);
        TeacherDTO savedTeacherDTO = teacherMapper.toTeacherDTO(savedTeacher);


        // Если есть courseIds, установите связь с курсами
        if (teacherDTO.getId() != null && !teacherDTO.getListCourseIds().isEmpty()) {
            // Находим курсы по ID
            List<Course> courses = courseService.findAllByTeacherId(teacherDTO.getId());
            // Устанавливаем курсы для учителя
            teacher.setCourses(courses);
        }
        return new ResponseEntity<>(savedTeacherDTO, HttpStatus.CREATED);
    }


    @GetMapping("/{email}")    // используем URL адрес для получения почты
    // используем аннотацию для извлечения переменной из URL-запроса и передачи в метод контроллера как параметра
    public Teacher findByEmail(@PathVariable String email) { // имя получаемой переменной и название в фигурных скобках совпадают
        return service.findByEmail(email);
    }
    @GetMapping("/{id}")
    public Teacher findById(@PathVariable Long id) { // имя получаемой переменной и название в фигурных скобках совпадают
        return service.findById(id);
    }
    // @RequestBody реализует JSON
    @PutMapping("update_teacher")
    public Teacher updateTeacher(@RequestBody Teacher teacher) {
        return service.updateTeacher(teacher);
    }
    @DeleteMapping("delete_teacher/{email}")
    public void deleteTeacher(@PathVariable String email) {
        service.deleteTeacher(email);
    }
}
