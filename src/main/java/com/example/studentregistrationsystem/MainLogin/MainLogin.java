package com.example.studentregistrationsystem.MainLogin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainLogin extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainlogin.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

    }
    public static void main(String[] args){
        launch();
    }
}
