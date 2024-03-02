package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.model.Student;
import ru.firstapp.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService service;

    @GetMapping
    public List<Student> findAllStudent() {
        return service.findAllStudent();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_student")// сделать метод POST
    public String saveStudent(@RequestBody Student student) {
        service.saveStudent(student);
        return "Student successfully saved";
    }

    @GetMapping("/{email}")    // используем URL адрес для получения почты
    // используем аннотацию для извлечения переменной из URL-запроса и передачи в метод контроллера как параметра
    public Student findByEmail(@PathVariable String email) { // имя получаемой переменной и название в фигурных скобках совпадают
        return service.findByEmail(email);
    }

    // @RequestBody реализует JSON
    @PutMapping("update_student")
    public Student updateStudent(@RequestBody Student student) {
        return service.updateStudent(student);
    }

    @DeleteMapping("delete_student/{email}")
    public void deleteStudent(@PathVariable String email) {
        service.deleteStudent(email);
    }
}
