package ru.firstapp.controller.pages;

import org.apache.tapestry5.annotations.Property;
import ru.firstapp.model.TestProject;
import ru.firstapp.runner.MyRunner;

public class TestProjectPage {
    @Property
    private TestProject project;

    void onActivate() {
        project = MyRunner.getTestProject();
    }

    Object onActionFromEdit() {
        return TestProjectEditPage.class;
    }
}