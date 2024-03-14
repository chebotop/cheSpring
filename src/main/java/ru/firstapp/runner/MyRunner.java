package ru.firstapp.runner;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.firstapp.dao.PersonDao;
import ru.firstapp.dao.impl.PersonDaoImpl;
import ru.firstapp.model.Person;
import ru.firstapp.service.StudentService;

import java.util.ArrayList;
import java.util.List;


@Component
public class MyRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);
    private final StudentService studentService;
    private final PersonDao personService;
    private static final Faker faker = new Faker();


    @Autowired
    public MyRunner(StudentService studentService, PersonDao personService) {
        this.studentService = studentService;
        this.personService = personService;
    }

    public static List<Person> createPersons(int n) {
        List<Person> list  = new ArrayList<>();
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
    @Override
    public void run(String... args) {
        createPersons(10);

        List<Person> allPersons = personService.findAll();
        if (allPersons != null) {
            for (Person foundPerson : allPersons) {
                logger.info("Found person: {} {} {} {} {}", foundPerson.getFirstName(), foundPerson.getMiddleName(),
                        foundPerson.getLastName(), foundPerson.getPassportSerial(), foundPerson.getPassportNumber());
            }
        }
    }


}
