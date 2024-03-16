package ru.firstapp.controller.pages;

import org.apache.tapestry5.annotations.Property;
import ru.firstapp.model.TestProject;
import ru.firstapp.runner.MyRunner;

public class TestProjectEditPage {
    @Property
    private TestProject project;

    void onActivate(TestProject project) {
        this.project = project;
    }

    Object onSuccess() {
        MyRunner.updateTestProject(project);
        return TestProjectPage.class;
    }

    Object onCancel() {
        return TestProjectPage.class;
    }
}