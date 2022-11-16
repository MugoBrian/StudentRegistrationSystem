package com.example.studentregistrationsystem.Enrollment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentsTable {
    public final SimpleStringProperty id;
    public final SimpleStringProperty name;
    public final SimpleStringProperty email;
    public final SimpleStringProperty password;
    public final SimpleStringProperty course;
    public final SimpleIntegerProperty year;
    public final SimpleStringProperty status;


    public StudentsTable(String  stdTableStdId, String stdTableStdName, String  stdTableStdEmail,
                         String  stdTableStdPassword, String  stdTableStdCourse, int stdTableStdYear,
                         String  stdTableStdStatus) {
        this.id = new SimpleStringProperty(stdTableStdId);
        this.name = new SimpleStringProperty(stdTableStdName);
        this.email = new SimpleStringProperty(stdTableStdEmail);
        this.password = new SimpleStringProperty(stdTableStdPassword);
        this.course = new SimpleStringProperty(stdTableStdCourse);
        this.year = new SimpleIntegerProperty(stdTableStdYear);
        this.status= new SimpleStringProperty(stdTableStdStatus);
    }

    public String getId(){
        return id.get();
    }
    public String getName(){ return name.get(); }
    public String getEmail(){ return email.get();}
    public String getPassword(){return password.get();}
    public String getCourse(){ return  course.get();}
    public int getYear(){ return year.get(); }
    public String getStatus(){ return status.get(); }
}
