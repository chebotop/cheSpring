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
    public ResponseEntity<List<Student>> findAllStudent() {
        List<Student> students = studentService.findAllStudent();
        return ResponseEntity.ok(students);
    }

    @PostMapping // POST подразумевает создание, поэтому "save_student" в URL не нужен
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Student> findByEmail(@PathVariable String email) {
        Student student = studentService.findByEmail(email);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<Student> updateStudent(@PathVariable String email, @RequestBody StudentUpdateDTO studentUpdateDTO) {
        Student student = studentService.findByEmail(email);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        student.setEmail(studentUpdateDTO.getEmail());
        Student updatedStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteStudent(@PathVariable String email) {
        Student student = studentService.findByEmail(email);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        studentService.deleteStudent(email);
        return ResponseEntity.ok().build();
    }
}
