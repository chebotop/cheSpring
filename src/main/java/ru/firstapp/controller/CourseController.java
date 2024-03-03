package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.dto.CourseDTO;
import ru.firstapp.dto.CourseUpdateDTO;
import ru.firstapp.mappers.CourseMapper;
import ru.firstapp.model.Course;
import ru.firstapp.model.Student;
import ru.firstapp.model.Teacher;
import ru.firstapp.service.CourseService;
import ru.firstapp.service.StudentService;
import ru.firstapp.service.TeacherService;

import java.util.HashSet;
import java.util.List;

@RestController // Определяет класс как контроллер, где каждый метод возвращает объект домена вместо представления.
@RequestMapping("/api/v1/courses") // Указывает базовый URL для всех методов в контроллере.
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    private final TeacherService teacherService;
    private final StudentService studentService;


    @GetMapping
    public List<Course> findAllCourse() {
        return courseService.findAllCourse();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_course")
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO) {
        // Использование метода convertToEntity для преобразования DTO в сущность
        Course course = courseMapper.fromDTO(courseDTO);
        Course savedCourse = courseService.saveCourse(course);
        return new ResponseEntity<>(courseMapper.toDTO(savedCourse), HttpStatus.CREATED);
    }

        @GetMapping("/{id}")                                                                                                                                                                    // используем имя курса для получения почты
    // используем аннотацию для извлечения переменной из URL-запроса и передачи в метод контроллера как параметра
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) { // имя получаемой переменной и название в фигурных скобках совпадают
        Course course = courseService.findCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @RequestBody реализует JSON
    @PutMapping("/update_course/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody CourseUpdateDTO courseDTO) {
        Course course = courseService.findCourseById(courseId);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
            }
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());

        if (courseDTO.getTeacherId() != null) {
            Teacher teacher = teacherService.findById(courseDTO.getTeacherId());
            if (teacher != null) {
                course.setTeacher(teacher);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
            }
        }
        if (courseDTO.getStudentIds() != null && !courseDTO.getStudentIds().isEmpty()) {
            List<Student> students = studentService.findAllStudentsByCourseId(courseDTO.getStudentId());
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or more students not found");
            }
            course.setStudents(new HashSet<>(students));
        }

        Course updatedCourse = courseService.saveCourse(course);
        CourseDTO updatedCourseDTO = courseMapper.toDTO(updatedCourse);
        return ResponseEntity.ok(updatedCourseDTO);
    }



    @DeleteMapping("delete_course/{title}")
    public void deleteCourse(@PathVariable String title) {
        courseService.deleteCourse(title);
    }
}
