package ru.firstapp.repository;

import org.springframework.stereotype.Repository;
import ru.firstapp.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryStudentDAO {
    private final List<Student> STUDENTS = new ArrayList<>();
    public List<Student> findAllStudent() {
        return new ArrayList<>(STUDENTS);
    }
    public Student saveStudent(Student student) {
        STUDENTS.add(student);
        return student;
    }
    public Student findByEmail(String email) {
        return STUDENTS.stream()
                // фильтр по почте, через потоки
                .filter(e -> e.getEmail().equals(email))
                // получаем первого совпадающего студента
                .findFirst()
                .orElse(null);
    }

    /**
     * Обновить данные студента
     * {@code studentIndex } - в этой преременной хранится индекс студента, у которого электронная почта совпадает с почтой
     * студента, который был получен при вызове метода. Eсли {@code studentIndex } больше чем -1, то совпадение было
     * обнаружено.
     * @return Student or null
     */
    public Student updateStudent(Student student) {
        var studentIndex = IntStream.range(0, STUDENTS.size())
                .filter(index -> STUDENTS.get(index).getEmail().equals(student.getEmail()))
                .findFirst()
                .orElse(-1);
        if (studentIndex > -1) {
            STUDENTS.set(studentIndex, student);
            return student;
        }
        return null;
    }

    public void deleteStudent(String email) {
        // ищем студента по почте
        var student = findByEmail(email);
        if (student != null) {
            STUDENTS.remove(student);
        }

    }
}
