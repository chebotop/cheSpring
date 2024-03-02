package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.model.Course;
import ru.firstapp.service.CourseService;

import java.util.List;

@RestController // Определяет класс как контроллер, где каждый метод возвращает объект домена вместо представления.
@RequestMapping("/api/v1/courses") // Указывает базовый URL для всех методов в контроллере.
@AllArgsConstructor
public class CourseController {
    private final CourseService service;

    @GetMapping
    public List<Course> findAllCourse() {
        return service.findAllCourse();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_course")// сделать метод POST
    public String saveCourse(@RequestBody Course course) {
        service.saveCourse(course);
        return "Course successfully saved";
    }

    @GetMapping("/{title}")                                                                                                                                                                    // используем имя курса для получения почты
    // используем аннотацию для извлечения переменной из URL-запроса и передачи в метод контроллера как параметра
    public Course findByTitle(@PathVariable String title) { // имя получаемой переменной и название в фигурных скобках совпадают
        return service.findByTitle(title);
    }

    // @RequestBody реализует JSON
    @PutMapping("/update_course/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody CourseUpdateDTO courseDTO) {
        Course updatedCourse = CourseService.updateCourse(courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("delete_course/{title}")
    public void deleteCourse(@PathVariable String title) {
        service.deleteCourse(title);
    }
}
