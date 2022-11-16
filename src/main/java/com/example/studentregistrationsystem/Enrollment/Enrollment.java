package com.example.studentregistrationsystem.Enrollment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Enrollment extends  Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Enrollment.class.getResource(
                "/com/example/studentregistrationsystem/enrollment/enrollment.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args){
        launch();
    }
}
