package com.example.studentregistrationsystem.Bursar;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FeeTable {
    public final SimpleStringProperty id;
    public final SimpleStringProperty name;
    public final SimpleStringProperty email;
    public final SimpleStringProperty course;
    public final SimpleIntegerProperty year;
    private final SimpleDoubleProperty amount;


    public FeeTable(String  feeTableStdId, String feeTableStdName, String  feeTableStdEmail, String  feeTableStdCourse,
                    int feeTableStdYear, Double feeTableFeeAmount) {
        this.id = new SimpleStringProperty(feeTableStdId);
        this.name = new SimpleStringProperty(feeTableStdName);
        this.email = new SimpleStringProperty(feeTableStdEmail);
        this.course = new SimpleStringProperty(feeTableStdCourse);
        this.year = new SimpleIntegerProperty(feeTableStdYear);
        this.amount = new SimpleDoubleProperty(feeTableFeeAmount);
    }

    public String getId(){
        return id.get();
    }
    public String getName(){ return name.get(); }
    public String getEmail(){ return email.get();}
    public String getCourse(){ return  course.get();}
    public int getYear(){ return year.get(); }
    public Double getAmount(){ return amount.get();}
}
