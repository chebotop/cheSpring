package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.dto.CourseDTO;
import ru.firstapp.dto.CourseUpdateDTO;
import ru.firstapp.model.Course;
import ru.firstapp.model.Student;
import ru.firstapp.model.Teacher;
import ru.firstapp.service.CourseService;
import ru.firstapp.service.StudentService;
import ru.firstapp.service.TeacherService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController // Определяет класс как контроллер, где каждый метод возвращает объект домена вместо представления.
@RequestMapping("/api/v1/courses") // Указывает базовый URL для всех методов в контроллере.
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentService studentService;


    @GetMapping
    public List<Course> findAllCourse() {
        return courseService.findAllCourse();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_course")// сделать метод POST
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        Course savedCourse = courseService.saveCourse(course);
        return new ResponseEntity<>(convertToDTO(savedCourse), HttpStatus.CREATED);
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());

        if (courseDTO.getTeacherId() != null) {
            Teacher teacher = teacherService.findById(courseDTO.getTeacherId());
            course.setTeacher(teacher);
        }
        if (courseDTO.getStudentIds() != null && !courseDTO.getStudentIds().isEmpty()) {
            List<Student> students = studentService.findAllById(courseDTO.getStudentIds());
            course.setStudents(new HashSet<>(students));
        }
        return course;
    }
    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO()
                .setId(course.getId())
                .setTitle(course.getTitle())
                .setDescription(course.getDescription())
                .setTeacherId(course.getTeacher() != null ? course.getTeacher().getId() : null);

        // Добавляем логику для заполнения studentIds
        if (course.getStudents() != null && !course.getStudents().isEmpty()) {
            List<Long> studentIds = course.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toList());
            courseDTO.setStudentIds(studentIds);
        }

        return courseDTO;
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
            if (teacher == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
            } else {
                course.setTeacher(teacher);
            }
        }
        if (courseDTO.getStudentIds() != null && !courseDTO.getStudentIds().isEmpty()) {
            List<Student> students = studentService.findAllById(courseDTO.getStudentIds());
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or more students not found");
            }
            course.setStudents(new HashSet<>(students));
        }

        Course updatedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(updatedCourse);
    }



    @DeleteMapping("delete_course/{title}")
    public void deleteCourse(@PathVariable String title) {
        courseService.deleteCourse(title);
    }
}
