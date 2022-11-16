package com.example.studentregistrationsystem.database;

import java.sql.*;

public class DBConnection {
    public  Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Student_Registration_System";
        String user = "root";
        String pass = "mugomuchiri@2001";
        return DriverManager.getConnection(url, user, pass);
    }
}
