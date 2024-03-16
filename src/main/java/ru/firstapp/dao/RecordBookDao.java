package ru.firstapp.dao;

import ru.firstapp.model.RecordBook;
import ru.firstapp.model.Student;

import java.util.List;

public interface RecordBookDao {
    List<RecordBook> findAll();
    Student findStudent(RecordBook recordBook);
    RecordBook save(RecordBook entity);
    void delete(RecordBook entity);

}
