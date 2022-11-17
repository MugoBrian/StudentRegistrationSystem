package com.example.studentregistrationsystem.Admission;

import com.example.studentregistrationsystem.database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


public class AdmissionController implements Initializable {
    private final DBConnection database = new DBConnection();
    private boolean isNewButtonClicked;
    private  boolean isEditButtonClicked;
//    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private ResultSet result;
    private String sqlQuery;
    @FXML
    private TextField fieldStdID;
    @FXML
    private  TextField fieldStdName;
    @FXML
    private TextField fieldStdEmail;
    @FXML
    private PasswordField fieldStdPassword;
    @FXML
    private ComboBox<String> fieldStdCourse;
    @FXML
    private TextField fieldYearOfStudy;
    @FXML
    private ComboBox<String> fieldStatus;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    TableView<StudentsTable> stdTableView;
    @FXML
    TableColumn<StudentsTable, String> colStdID;
    @FXML
    TableColumn<StudentsTable, String> colStdName;
    @FXML
    TableColumn<StudentsTable, String> colStdEmail;
    @FXML
    TableColumn<StudentsTable, String> colCourse;
    @FXML
    TableColumn<StudentsTable, String> colStdPassword;
    @FXML
    TableColumn<StudentsTable, Integer> colYear;
    @FXML
    TableColumn<StudentsTable, String> colStatus;

    @FXML
    private TextField fieldSearch;
    @FXML
    private Label lblAdmissionsID;
    @FXML
    private Label lblAdmissionsName;

    public void setAdd(){
        setAllEnable();
        isNewButtonClicked = true;
    }

    //Save button functionality
    public void save() throws SQLException {
        if(isNewButtonClicked){
            registerStudent();
        }
        else if(isEditButtonClicked){
            updateStudent();
        }
    }
    //register Student
    public void registerStudent() throws SQLException {
        if(!checkFieldsEmpty()){
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            sqlQuery = "Insert into students values('"+fieldStdID.getText()+"','"+fieldStdName.getText()+"'," +
                "'"+fieldStdEmail.getText()+"','"+fieldStdPassword.getText()+"'," +
                "'"+fieldStdCourse.getSelectionModel().getSelectedItem()+"','"+ parseInt(fieldYearOfStudy.getText())+"',"
                + "'"+fieldStatus.getSelectionModel().getSelectedItem()+"');";
            String sqlQueryInsert = "Insert into `graduationEligible` values('"+fieldStdID.getText()+"','No')";
            int resultInsert = statement.executeUpdate(sqlQueryInsert);
            int result = statement.executeUpdate(sqlQuery);
            if(result == 1 && resultInsert == 1){
                informationAlert.setContentText("Student Added Successfully!");
                informationAlert.show();
            }
            else{
                errorAlert.setHeaderText("Error encountered!");
                errorAlert.show();
            }
        }
        else{
            errorAlert.setContentText("All fields are required!");
            errorAlert.show();
        }
        isNewButtonClicked = true;
        isEditButtonClicked = false;
    }

    //Update Student Information

    public void updateStudent() throws SQLException{
        sqlQuery ="Update students set Std_ID='"+fieldStdID.getText()+"',Std_Name='"+fieldStdName.getText()+"'," +
            "Std_Email='"+fieldStdEmail.getText()+"',Password='"+fieldStdPassword.getText()+"'," +
            "Course='"+fieldStdCourse.getSelectionModel().getSelectedItem()+"'," +
            "YOS='"+ parseInt(fieldYearOfStudy.getText())+"',"
            + "Status='"+fieldStatus.getSelectionModel().getSelectedItem()+"' " +
                "where Std_ID='"+fieldStdID.getText()+"';";

        int result =  database.getConnection().createStatement().executeUpdate(sqlQuery);
        if(result == 1){
            informationAlert.setContentText("Student Updated Successfully");
            informationAlert.setHeaderText("Success");
            informationAlert.show();
            isEditButtonClicked = true;
            isNewButtonClicked = false;
        }
        else {
            errorAlert.setContentText("Error has occurred");
            errorAlert.show();
        }
        clearAll();
    }

    //Deleting Student Information
    public void deleteStudent() throws SQLException {
        StudentsTable stdTable = stdTableView.getSelectionModel().getSelectedItem();

        if(stdTable == null){
            informationAlert.setContentText("Kindly select a record to delete!");
            informationAlert.show();
        }
        else{
            sqlQuery = "Delete from Student where Std_ID= '"+stdTable.getId()+"';";

            database.getConnection().createStatement().executeUpdate(sqlQuery);
            stdTableView.setItems(getStudents(null));
        }
        assert stdTable != null;

    }

    //Editing Student Information
    public void editStudent() throws SQLException{
        isNewButtonClicked = false;
        isEditButtonClicked = true;
        StudentsTable stdTable = stdTableView.getSelectionModel().getSelectedItem();
        if(stdTable == null){
            informationAlert.setContentText("Kindly select a record to Edit!");
            informationAlert.show();
        }
        else{
            setAllEnable();
            sqlQuery = "Select * from Student where Std_ID='"+stdTable.getId()+"';";

            result = database.getConnection().createStatement().executeQuery(sqlQuery);
            while (result.next()){

            fieldStdID.setText(result.getString("Std_ID"));
            fieldStdName.setText(result.getString("Std_Name"));
            fieldStdEmail.setText(result.getString("Std_Email"));
            fieldStdPassword.setText(result.getString("Password"));
            fieldStdCourse.setValue(result.getString("Course"));
            fieldYearOfStudy.setText(result.getString("YOS"));
            fieldStatus.setValue(result.getString("Status"));
            }
            isEditButtonClicked = true;
            clearAll();
        }
    }

    //Searching For Student Information
    public void searchStudent() throws SQLException{
        if(fieldSearch.getText().isEmpty()){
            informationAlert.setContentText("Kindly Enter Student ID!");
            informationAlert.show();
        }
        sqlQuery = "Select * from Student where Std_ID LIKE'%"+fieldSearch.getText()+"%';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if(result.next()){
            stdTableView.setItems(getStudents(sqlQuery));
        }
        else{
            informationAlert.setContentText("No Student Found!");
            informationAlert.show();
        }
    }
    public void cancel(){
        setAllDisable();
    }

    //Refreshing student table

    public void refresh() throws SQLException {
        stdTableView.setItems(getStudents(null));
    }


    public ObservableList<String> getCourses() throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sqlQuery = "Select * from Courses;";
        ResultSet result = statement.executeQuery(sqlQuery);
        ObservableList<String> courses = FXCollections.observableArrayList();
        while (result.next()){
            courses.add(result.getString("courseID"));
        }
        return courses;
    }
    private void setAllEnable() {
        fieldStdID.setDisable(false);
        fieldStdName.setDisable(false);
        fieldStdEmail.setDisable(false);
        fieldStdPassword.setDisable(false);
        fieldStdCourse.setDisable(false);
        fieldYearOfStudy.setDisable(false);
        fieldStatus.setDisable(false);
        saveBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    private void setAllDisable() {
        fieldStdID.setDisable(true);
        fieldStdName.setDisable(true);
        fieldStdEmail.setDisable(true);
        fieldStdPassword.setDisable(true);
        fieldStdCourse.setDisable(true);
        fieldYearOfStudy.setDisable(true);
        fieldStatus.setDisable(true);
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);

    }
    public void clearAll(){
        fieldStdID.setText("");
        fieldStdName.setText("");
        fieldStdEmail.setText("");
        fieldStdPassword.setText("");
        fieldStdCourse.setValue("Courses");
        fieldYearOfStudy.setText("");
        fieldStatus.setValue("Status");
    }

    public ObservableList<String>  getStatus() throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sqlQuery = "select Distinct(Status) from Student;";
        ResultSet result = statement.executeQuery(sqlQuery);
        ObservableList<String> courses = FXCollections.observableArrayList();
        while (result.next()){
            courses.add(result.getString("Status"));
        }
        return courses;
    }

    public boolean checkFieldsEmpty(){
        return fieldStdID.getText().isEmpty() || fieldStdName.getText().isEmpty() || fieldStdEmail.getText().isEmpty() ||
                fieldStdPassword.getText().isEmpty() || fieldStdCourse.getSelectionModel().getSelectedItem() == null ||
                fieldStdCourse.getSelectionModel().getSelectedItem().isEmpty() ||
                fieldYearOfStudy.getText().isEmpty() || fieldStatus.getSelectionModel().getSelectedItem() == null ||
                fieldStatus.getSelectionModel().isEmpty();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fieldStdCourse.setItems(getCourses());
            fieldStatus.setItems(getStatus());
            lblAdmissionsID.setText(getAdmissionDetails().get(0));
            lblAdmissionsName.setText(getAdmissionDetails().get(1));
            setAllDisable();
            getStudents(null);
            StdTable();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<StudentsTable> getStudents(String sqlParamsQuery) throws SQLException{
        Optional<String> sqlParamQuery = Optional.ofNullable(sqlParamsQuery);
        if(sqlParamQuery.isPresent()){
            sqlQuery = sqlParamsQuery;
        }
        else{

        sqlQuery = "Select * from Student;";
        }
        ObservableList<StudentsTable> studentTableData = FXCollections.observableArrayList();

        ResultSet result = database.getConnection().createStatement().executeQuery(sqlQuery);
        while (result.next()){
            studentTableData.add(new StudentsTable(
                    result.getString("Std_ID"),
                    result.getString("Std_Name"),
                    result.getString("Std_Email"),
                    result.getString("Password"),
                    result.getString("Course"),
                    result.getInt("YOS"),
                    result.getString("Status")

            ));
        }
        return  studentTableData;
    }

    public void StdTable() throws SQLException {
        colStdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStdEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStdPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        stdTableView.setItems(getStudents(null));
    }
    public void logout(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                "/com/example/studentregistrationsystem/mainlogin/mainlogin.fxml")));
        Stage admissionStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        admissionStage.close();

    }

    public ArrayList<String> getAdmissionDetails() throws SQLException{
        String id = "ADM-001";
        sqlQuery = "Select ID, Name from admissions where ID='"+id+"';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        ArrayList<String> admDetails = new ArrayList<>();
        while (result.next()){
            admDetails.add(result.getString("ID"));
            admDetails.add(result.getString("Name"));
        }
        return admDetails;
    }

}
