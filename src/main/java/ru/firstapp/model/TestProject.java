package ru.firstapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class TestProject {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String title;
    private String description;
    private Date createDate;


}






