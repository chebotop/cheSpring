package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.model.Teacher;
import ru.firstapp.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService service;

    @GetMapping
    public List<Teacher> findAllTeacher() {
        return service.findAllTeacher();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_teacher")// сделать метод POST
    public String saveTeacher(@RequestBody Teacher teacher) {
        service.saveTeacher(teacher);
        return "Teacher successfully saved";
    }

    @GetMapping("/{email}")    // используем URL адрес для получения почты
    // используем аннотацию для извлечения переменной из URL-запроса и передачи в метод контроллера как параметра
    public Teacher findByEmail(@PathVariable String email) { // имя получаемой переменной и название в фигурных скобках совпадают
        return service.findByEmail(email);
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
