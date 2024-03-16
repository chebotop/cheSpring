package ru.firstapp.runner;

import com.github.javafaker.Faker;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.firstapp.dao.PersonDao;
import ru.firstapp.dao.RecordBookDao;
import ru.firstapp.dao.StudentDao;
import ru.firstapp.model.Person;
import ru.firstapp.model.RecordBook;
import ru.firstapp.model.Student;
import ru.firstapp.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyRunnerTest {
    @Mock
    private PersonDao personDao;
    @Mock
    private RecordBookDao recordBookDao;
    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private MyRunner myRunner;

    @Test
    void testRun() {
        int n = 10;
        int x = 7;
        // создать 10 персон
        List<Person> testPeopleList  = MyRunner.createPersons(n);
        // создать менее 10 (случайное число менее 10) зачетных книжек со случайными кодами
        List<RecordBook> testRecordBookList = MyRunner.createRecordBooks(x);
        List<Student> testStudentList = MyRunner.createStudents(testPeopleList);


        when(personDao.findAll()).thenReturn(testPeopleList);
        when(recordBookDao.findAll()).thenReturn(testRecordBookList);
        when(studentDao.findAll()).thenReturn(testStudentList);

//        when(recordBookDao.findStudent(any(RecordBook.class))).thenReturn(new Student());

        // Act
        myRunner.run();
        // Assert
        verify(personDao, times(n)).save(any(Person.class));
        verify(personDao).findAll();
        verify(recordBookDao, times(x)).save(any(RecordBook.class));
        verify(recordBookDao).findAll();
        verify(studentDao, times(n)).save(any(Student.class));

        assertEquals(n, studentDao.findAll().size());
    }
}
