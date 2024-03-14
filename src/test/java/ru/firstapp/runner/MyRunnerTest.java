package ru.firstapp.runner;

import com.github.javafaker.Faker;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.firstapp.dao.PersonDao;
import ru.firstapp.model.Person;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyRunnerTest {



    @Mock
    private PersonDao personDao;

    @InjectMocks
    private MyRunner myRunner;
//    private static final Faker faker = new Faker();

    @Test
    void testRun() {

        List<Person> testPeopleList  = MyRunner.createPersons(10);

        when(personDao.findAll()).thenReturn(testPeopleList);

        // Act
        myRunner.run();

        // Assert
        verify(personDao, times(10)).save(any(Person.class));
        verify(personDao).findAll();
    }
}
