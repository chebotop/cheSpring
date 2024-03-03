package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.dto.TeacherDTO;
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

    @GetMapping
    public List<Teacher> findAllTeacher() {
        return service.findAllTeacher();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_teacher")// сделать метод POST
    public ResponseEntity<TeacherDTO> saveTeacher(@RequestBody TeacherDTO teacherDTO) {
        // Шаг 1: Преобразование из TeacherDTO в сущность Teacher
        Teacher teacher = convertToEntity(teacherDTO);

        // Шаг 2: Сохранение сущности Teacher в базу данных
        Teacher savedTeacher = service.saveTeacher(teacher);

        // Шаг 3: Преобразование сохраненного учителя обратно в TeacherDTO
        TeacherDTO savedTeacherDTO = convertToDTO(savedTeacher);

        // Если есть courseIds, установите связь с курсами
        if (teacherDTO.getId() != null && !teacherDTO.getListCourseIds().isEmpty()) {
            // Находим курсы по ID
            List<Course> courses = courseService.findAllByTeacherId(teacherDTO.getId());
            // Устанавливаем курсы для учителя
            teacher.setCourses(courses);
        }
        return new ResponseEntity<>(savedTeacherDTO, HttpStatus.CREATED);
    }

    // Пример метода для преобразования DTO в сущность
    private Teacher convertToEntity(TeacherDTO teacherDTO) {
        // Здесь ваш код для преобразования
        // Можно использовать ModelMapper или вручную преобразовать поля DTO в поля сущности
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setDateOfBirth(teacherDTO.getDateOfBirth());
        // Дополните другие поля по необходимости
        return teacher;
    }

    // Пример метода для преобразования сущности в DTO
    private TeacherDTO convertToDTO(Teacher savedTeacher) {
        // Здесь ваш код для преобразования
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(savedTeacher.getId());
        teacherDTO.setFirstName(savedTeacher.getFirstName());
        teacherDTO.setLastName(savedTeacher.getLastName());
        teacherDTO.setEmail(savedTeacher.getEmail());
        teacherDTO.setDateOfBirth(savedTeacher.getDateOfBirth());
        // Дополните другие поля по необходимости
        return teacherDTO;
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
