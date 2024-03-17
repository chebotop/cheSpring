//package ru.firstapp.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import ru.firstapp.model.Student;
//
//import java.util.List;
//
//
//public interface StudentRepository extends JpaRepository<Student, Long> {
//
//    void deleteByEmail(String email);
//    Student findStudentByEmail(String email);
//
//    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
//    List<Student> findAllStudentsByCourseId(@Param("courseId") Long courseId);
//
//}
