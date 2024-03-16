package ru.firstapp.runner;

import com.github.javafaker.Faker;
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
import ru.firstapp.model.Person;
import ru.firstapp.model.RecordBook;
import ru.firstapp.model.Student;
import ru.firstapp.model.TestProject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Component
public class MyRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);
    private final StudentDao studentDao;
    private final PersonDao personDao;
    private final RecordBookDao recordBookDao;
    private static final Faker faker = new Faker();
    @Getter
    private static TestProject testProject;


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
    public static List<Person> createPersons(int n) {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Person person = new Person();
            person.setFirstName(faker.name().firstName());
            person.setMiddleName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
            person.setPassportSerial(Integer.parseInt(faker.number().digits(4)));
            person.setPassportNumber(Integer.parseInt(faker.number().digits(6)));
            list.add(person);
        }
        return list;
    }

    public static List<Student> createStudents(List<Person> persons) {
        List<Student> list = new ArrayList<>();
        for (Person person : persons) {
            Student student = new Student();
            student.setPerson(person);
            list.add(student);
        }
        return list;
    }

    public static List<RecordBook> createRecordBooks(int n) {
        List<RecordBook> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            RecordBook recordBook = new RecordBook();
            recordBook.setCode(Long.parseLong(faker.number().digits(7)));
            list.add(recordBook);
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

        // Логика от тестового Hibernate
        List<Person> savedPersons = createPersons(10);
        for (Person person : savedPersons) {
            personDao.save(person);
        }
        int recordBookCount = 7;
        List<RecordBook> savedRecordBooks = createRecordBooks(recordBookCount);
        for (RecordBook recordBook : savedRecordBooks) {
            recordBookDao.save(recordBook);
        }
        List<RecordBook> allRecordBooks = recordBookDao.findAll();

        List<Student> students = createStudents(savedPersons);

        // Назначение свободных книжек студентам
        List<Student> studentsWithoutRecordBook = studentDao.findAll().stream()
                .filter(student -> student.getRecordBook() == null)
                .toList();

        List<RecordBook> freeRecordBooks = allRecordBooks.stream()
                .filter(recordBook -> recordBook.getStudent() == null)
                .collect(Collectors.toList());

        for (Student student : studentsWithoutRecordBook) {
            Optional<RecordBook> freeRecordBook = freeRecordBooks.stream().findFirst();

            if (freeRecordBook.isPresent()) {
                student.setRecordBook(freeRecordBook.get());
                freeRecordBook.get().setStudent(student);
                freeRecordBooks.remove(freeRecordBook.get());
            }
        }
        for (Student student : students) {
            studentDao.save(student);
        }

        // common logger
        List<Person> allPersons = personDao.findAll();
        if (allPersons != null) {
            for (Person foundPerson : allPersons) {
                Student student = studentDao.findAll().stream()
                        .filter(s -> s.getPerson().equals(foundPerson))
                        .findFirst()
                        .orElse(null);

                if (student != null && student.getRecordBook() != null) {
                    logger.info("Found person: {} {} {} {} {} rb: {}", foundPerson.getFirstName(), foundPerson.getMiddleName(),
                            foundPerson.getLastName(), foundPerson.getPassportSerial(), foundPerson.getPassportNumber(), student.getRecordBook().getCode());
                }
            }
        }


    }
}
