package ru.firstapp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServlet;

@SpringBootApplication
public class SpringApp extends HttpServlet {
    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }

}
