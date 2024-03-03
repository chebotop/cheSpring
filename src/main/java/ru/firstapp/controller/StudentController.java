package ru.firstapp.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.firstapp.dto.StudentDTO;
import ru.firstapp.dto.StudentUpdateDTO;
import ru.firstapp.model.Student;
import ru.firstapp.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<Student> findAllStudent() {
        return studentService.findAllStudent();
    }

    // чтобы string могла реализовать указанный обьект, добавим @RequestBody
    @PostMapping("save_student")// сделать метод POST
    public String saveStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return "Student successfully saved";
    }


    @GetMapping("/{email}")    // используем URL адрес для получения почты
    // используем аннотацию для извлечения переменной из URL-запроса и передачи в метод контроллера как параметра
    public Student findByEmail(@PathVariable String email) { // имя получаемой переменной и название в фигурных скобках совпадают
        return studentService.findByEmail(email);
    }

    //todo реализуем обновление данных студента
    @PutMapping("/update_student/{email}")
    public ResponseEntity<?> updateStudent(@PathVariable String email, @RequestBody StudentUpdateDTO studentUpdateDTO) {
        Student student = studentService.findByEmail(email);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        student.setFirstName(studentUpdateDTO.getFirstName());
        student.setLastName(studentUpdateDTO.getLastName());
        student.setEmail(studentUpdateDTO.getEmail());
    }
        @DeleteMapping("delete_student/{email}")
        public void deleteStudent (@PathVariable String email){
            studentService.deleteStudent(email);
        }
    }
}

