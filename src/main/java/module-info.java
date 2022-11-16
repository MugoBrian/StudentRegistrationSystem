module com.example.studentregistrationsystem {

    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires barcodes;
    requires kernel;
    requires layout;
    requires io;

    //MainLogin
    opens com.example.studentregistrationsystem.MainLogin to javafx.fxml;
    exports com.example.studentregistrationsystem.MainLogin;
    //Admission
    exports com.example.studentregistrationsystem.Admission;
    opens com.example.studentregistrationsystem.Admission to javafx.fxml;
    //Bursar
    exports com.example.studentregistrationsystem.Bursar;
    opens com.example.studentregistrationsystem.Bursar to javafx.fxml;
    //Enrollment
    exports com.example.studentregistrationsystem.Enrollment;
    opens com.example.studentregistrationsystem.Enrollment to javafx.fxml;


}