package com.example.studentregistrationsystem.Enrollment;

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


public class EnrollmentController implements Initializable {
    private final DBConnection database = new DBConnection();
    private boolean isNewButtonClicked;
    private  boolean isEditButtonClicked;
    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
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
    private TextField fieldCheckStatus;
    @FXML
    private Label lblAdmissionsID;
    @FXML
    private Label lblAdmissionsName;

    //Save button functionality
    public void save() throws SQLException {
        if(isEditButtonClicked){
            updateStudent();
        }
    }


    //Update Student Information

    public void updateStudent() throws SQLException{
        if (checkFieldsEmpty()){
            errorAlert.setContentText("All fields are required");
            errorAlert.show();
        }
        else{
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
                clearAll();
                setAllDisable();
                stdTableView.setItems(getStudents(null));
                isEditButtonClicked = true;
                isNewButtonClicked = false;
            }
            else {
                errorAlert.setContentText("Error has occurred");
                errorAlert.show();
            }
        }

    }

    //Editing Student Information
    public void editStudent() throws SQLException{
        StudentsTable stdTable = stdTableView.getSelectionModel().getSelectedItem();
        if(stdTable == null){
            informationAlert.setContentText("Kindly select a record to Update!");
            informationAlert.show();
        }
        else{

            setAllEnable();

            sqlQuery = "Select * from students where Std_ID='"+stdTable.getId()+"';";

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
        }
    }

    //Searching For Student Information
    public void searchStudent() throws SQLException{
        if(fieldSearch.getText().isEmpty()){
            informationAlert.setContentText("Kindly Enter Student ID!");
            informationAlert.show();
        }
        sqlQuery = "Select * from students where Std_ID LIKE'%"+fieldSearch.getText()+"%';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if(result.next()){
            stdTableView.setItems(getStudents(sqlQuery));
        }
        else{
            informationAlert.setContentText("No Student Found!");
            informationAlert.show();
        }
    }
    //Get Dropped Out Students
    public void droppedOut(ActionEvent event) throws SQLException{
        sqlQuery = "Select * from students where Status='dropped-out';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if(result.next()){
            stdTableView.setItems(getStudents(sqlQuery));
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
        String sqlQuery = "Select * from courses;";
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
    public void clearAll(){
        fieldStdID.setText("");
        fieldStdName.setText("");
        fieldStdEmail.setText("");
        fieldStdPassword.setText("");
        fieldStdCourse.setValue("Courses");
        fieldYearOfStudy.setText("");
        fieldStatus.setValue("Status");
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

    public ObservableList<String>  getStatus() throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sqlQuery = "select Distinct(Status) from students;";
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
            lblAdmissionsID.setText(getEnrollmentDetails().get(0));
            lblAdmissionsName.setText(getEnrollmentDetails().get(1));
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

        sqlQuery = "Select * from students;";
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

    public ArrayList<String> getEnrollmentDetails() throws SQLException{
        String id = "ENR-001";
        sqlQuery = "Select ID, Name from enrollment where ID='"+id+"';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        ArrayList<String> admDetails = new ArrayList<>();
        while (result.next()){
            admDetails.add(result.getString("ID"));
            admDetails.add(result.getString("Name"));
        }
        return admDetails;
    }

    public void checkStatus(ActionEvent event) throws SQLException {
        if(fieldCheckStatus.getText().isEmpty()){
            informationAlert.setContentText("Kindly Enter Student ID!");
            informationAlert.show();
        }
        else{
            String sqlQueryStudent = "Select Std_ID, Status from students where Std_ID='"+fieldCheckStatus.getText()+"';";
            result = database.getConnection().createStatement().executeQuery(sqlQueryStudent);
            if(!result.next()){
                errorAlert.setContentText("No Student Found With That ID!");
                errorAlert.show();
            }
            else{
                String isActive = result.getString("Status");
                if(isActive.equals("inactive") || isActive.equals("dropped-out")){
                    errorAlert.setContentText("Student is Inactive or Is Dropped Out!");
                    errorAlert.setTitle("Student Eligibility");
                    errorAlert.setHeaderText("Inactive Or Dropped Out");
                    errorAlert.show();
                }
                else{
                    String sqlQueryNumberOfUnitResults = "Select count(GPA) as TotalUnits from unitresults " +
                            "where Std_ID='"+fieldCheckStatus.getText()+"';";
                    ResultSet resultNumberOfUnitsResults = database.getConnection().createStatement()
                            .executeQuery(sqlQueryNumberOfUnitResults);
                    if(resultNumberOfUnitsResults.next()){
                        int TotalUnits = resultNumberOfUnitsResults.getInt("TotalUnits");
                        if(TotalUnits == 0){
                            informationAlert.setContentText("The Student Is Not Eligible For Graduation!");
                            informationAlert.setHeaderText("No Marks Found!");
                            informationAlert.show();

                        }
                        else{
                            String sqlQueryNumberOfUnits = "Select count(unitCode) as TotalCourseUnits from courseunits " +
                                    "where courseID=(Select courseID from courses where courseID=(Select Course " +
                                    "from students where Std_ID='"+fieldCheckStatus.getText()+"'));";
                            ResultSet resultSetNumberOfUnits = database.getConnection().createStatement()
                                    .executeQuery(sqlQueryNumberOfUnits);
                            if(resultSetNumberOfUnits.next()){
                                int TotalCourseUnits = resultSetNumberOfUnits.getInt("TotalCourseUnits");
                                if (!(TotalUnits == TotalCourseUnits)){
                                    informationAlert.setContentText("The Student Is Not Eligible For Graduation!");
                                    informationAlert.setHeaderText("No All Marks Entered For Student!");
                                    informationAlert.show();
                                }
                                else{
                                    ResultSet feeresult = database.getConnection().createStatement()
                                            .executeQuery("Select feeAmount from billstatement where Std_ID=" +
                                                    "'"+fieldStdID.getText()+"'");
                                    if(!feeresult.next()){
                                        informationAlert.setContentText("The Student Is Not Eligible For Graduation!");
                                        informationAlert.setHeaderText("No Fee Amount Found!");
                                        informationAlert.setResizable(false);
                                        informationAlert.show();
                                    }
                                    else{
                                        double feeAmount = feeresult.getDouble("feeAmount");
                                        if(feeAmount == 0.0){
                                            confirmationAlert.setTitle("Student Eligiblity");
                                            confirmationAlert.setContentText("The Student Is Eligible For Graduation!");
                                            confirmationAlert.setHeaderText("Student Can Graduate!");
                                            confirmationAlert.setResizable(false);
                                            confirmationAlert.show();
                                        }
                                        else{
                                            informationAlert.setContentText("The Student Is Not Eligible For Graduation!");
                                            informationAlert.setHeaderText("All Fee Amount Must Be Cleared!");
                                            informationAlert.setResizable(false);
                                            informationAlert.show();
                                            database.getConnection().createStatement().
                                                    executeUpdate("Update graduationEligible set isEligible='Yes' " +
                                                            "where Std_ID='"+fieldCheckStatus.getText()+"'");
                                        }
                                    }
                                }
                            }
                            System.out.println();
                            //            sqlQuery = "Select * from students where Std_ID LIKE'%"+fieldSearch.getText()+"%';";
                            //            result = database.getConnection().createStatement().executeQuery(sqlQuery);

                        }
                    }
                }
            }
        }
    }

    public void getListOfGraduants(ActionEvent event) throws SQLException {
        sqlQuery = "Select Std_ID, Std_Name, Std_Email, Course,Password, YOS,Status from students where Std_ID=" +
                "(Select Std_ID from graduationEligible where isEligible='Yes');";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if(!result.next()){
            informationAlert.setContentText("No List Of Graduants To Be Displayed!");
            informationAlert.show();
        }
        else{
            stdTableView.setItems(getStudents(sqlQuery));

        }
    }


}
