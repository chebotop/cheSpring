//package ru.firstapp.service.impl;
//
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//import ru.firstapp.model.Course;
//import ru.firstapp.model.Teacher;
//import ru.firstapp.repository.CourseRepository;
//import ru.firstapp.repository.TeacherRepository;
//import ru.firstapp.service.CourseService;
//import ru.firstapp.service.TeacherService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//@Primary
//public class TeacherServiceImpl implements TeacherService {
//    private final TeacherRepository repository;
//    private final CourseRepository courseRepository;
//    private final CourseService courseService;
//    private static final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);
//
//
//    @Override
//    public List<Teacher> findAllTeacher() {
//        return repository.findAll();
//    }
//
//    @Override
//    public Teacher saveTeacher(Teacher teacher) {
//        return repository.save(teacher);
//    }
//
//    @Override
//    public Teacher findByEmail(String email) {
//        return repository.findTeacherByEmail(email);
//    }
//
//    @Override
//    public Teacher findById(Long id) {
//        return repository.findTeacherById(id);
//    }
//
//    @Override
//    public Teacher updateTeacher(Teacher teacher) {
//        return repository.save(teacher);
//    }
//
//    @Override
//    @Transactional
//    public void deleteTeacher(Long teacherId) {
//        List<Course> courses = courseRepository.findAllByTeacherId(teacherId);
//        for (Course course : courses) {
//            course.setTeacher(null);
//            courseRepository.save(course);
//        }
//        repository.deleteById(teacherId);
//    }
//    @Override
//    @Transactional
//    public void deleteTeacher(String email) {
//        try {
//            Teacher teacher = repository.findTeacherByEmail(email);
//            if (teacher != null) {
//                deleteTeacher(teacher.getId());
//                log.info("Teacher with email {} deleted successfully", email);
//            } else {
//                log.warn("Teacher with email {} not found", email);
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with email " + email + " not found");
//            }
//        } catch (Exception e) {
//            log.error("Error deleting teacher with email {}: {}", email, e.getMessage());
//            throw e;
//        }
//    }
//}
