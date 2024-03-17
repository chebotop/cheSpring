package ru.firstapp.runner;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.firstapp.dao.PersonDao;
import ru.firstapp.dao.RecordBookDao;
import ru.firstapp.dao.StudentDao;
import ru.firstapp.dao.impl.PersonDaoImpl;
import ru.firstapp.dao.impl.StudentDaoImpl;
import ru.firstapp.model.Person;
import ru.firstapp.model.RecordBook;
import ru.firstapp.model.Student;
import ru.firstapp.model.TestProject;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Component
public class MyRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);
    private final StudentDao studentDao;
    private final PersonDao personDao;
    private final RecordBookDao recordBookDao;
    private static final Faker faker = new Faker();
    @Getter
    private static TestProject testProject;
    @PersistenceContext
    private EntityManager entityManager;


    // Tapestry task
    public static void updateTestProject(TestProject updatedProject) {
        testProject.setCode(updatedProject.getCode());
        testProject.setTitle(updatedProject.getTitle());
        testProject.setDescription(updatedProject.getDescription());
        testProject.setCreateDate(updatedProject.getCreateDate());
    }
    // end tapestry task initialize updates

    @Autowired
    public MyRunner(StudentDao studentDao, PersonDao personDao, RecordBookDao recordBookDao) {
        this.studentDao = studentDao;
        this.personDao = personDao;
        this.recordBookDao = recordBookDao;
    }

    /**
     * Создает список рандомных персон.
     *
     * @param n число персон
     * @return List<Person>
     */
    public List<Person> createPersons(int n) {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Person person = new Person();
            person.setFirstName(faker.name().firstName());
            person.setMiddleName(faker.name().firstName());
            person.setLastName(faker.name().lastName());

            Person.Passport passport = new Person.Passport();
            passport.setPassportSeria(4521);
            passport.setPassportNumber(Integer.parseInt(faker.number().digits(6)));

            person.setPassport(passport);
            list.add(person);
        }
        return list;
    }

    public List<Student> createStudents(List<Person> persons) {
        List<Student> list = new ArrayList<>();
        for (Person person : persons) {

            Student student = new Student();
            Person managedPerson = personDao.findById(person.getId());
            student.setPerson(managedPerson);
            studentDao.save(student);
            list.add(student);
        }
        return list;
    }

    public List<RecordBook> createRecordBooks(int n) {
        List<RecordBook> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            RecordBook recordBook = new RecordBook();
            recordBook.setCode(Long.parseLong(faker.number().digits(7)));
            list.add(recordBook);
            recordBookDao.save(recordBook);
        }
        return list;
    }

    @Override
    public void run(String... args) {
        // Логика от тестового Tapestry
        testProject = new TestProject();
        testProject.setId(1L);
        testProject.setCode("TP-001");
        testProject.setTitle("Test Project");
        testProject.setDescription("This is a test project");
        testProject.setCreateDate(new Date());
        // end tapestry test app constructor

        // создать и сохранить персоны
        List<Person> savedPersons = createPersons(10);
        for (Person person : savedPersons) {
            personDao.save(person);
        }

        // создать студентов из всех персон
        List<Student> savedStudents = createStudents(savedPersons);
        for (int i = 0; i < savedStudents.size(); i++) {
            logger.info("{}", savedStudents.get(i).getPerson().getFirstName());
        }
        List<Student> students = studentDao.findAll();
        for (Student student : students) {
            logger.info("{}", student.getPerson().getFirstName());
        }

        // создать n книг,
        int recordBookCount = 7;
        List<RecordBook> savedRecordBooks = createRecordBooks(recordBookCount);

        // назначить свободные книжки студентам
        List<Student> studentsWithoutRecordBook = studentDao.findAll().stream()
                .filter(student -> student.getRecordBook() == null)
                .collect(Collectors.toList());

//
        List<RecordBook> freeRecordBooks = savedRecordBooks.stream()
                .filter(recordBook -> recordBook.getStudent() == null)
                .collect(Collectors.toList());
//
        Iterator<RecordBook> bookIterator = freeRecordBooks.iterator();
        for (Student student : studentsWithoutRecordBook) {
            if (bookIterator.hasNext()) {
                RecordBook book = bookIterator.next();
                student.setRecordBook(book);
                studentDao.save(student);
                recordBookDao.save(book);
                book.setStudent(student);
                bookIterator.remove(); // Безопасно удаляет элемент из списка
            }
        }


        // hql запросы
        String hql = "from Student s where s.recordBook is null";
        List<Student> studentsStillWithoutRecordBook = entityManager.createQuery(hql, Student.class).getResultList();
        String hql1 = "from Student s where s.person.firstName like '%a%' or s.person.middleName like '%a%'" ;
        List<Student> studentsWithA = entityManager.createQuery(hql1, Student.class).getResultList();
        for (Student student : studentsStillWithoutRecordBook) {
            logger.info("Студент без зачетной книжки: {}", student.getPerson().getFirstName());
        }
        for (Student student : studentsWithA) {
            logger.info("Студент с 'a' в имени или отчестве: {} {}", student.getPerson().getFirstName(), student.getPerson().getMiddleName());
        }

        //         удаление созданных студентов, а за ними их персон и книжек
        for (Student student : students) {
            studentDao.deleteById(student.getId());
        }
    }
}


