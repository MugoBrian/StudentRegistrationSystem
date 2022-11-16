package com.example.studentregistrationsystem.Bursar;

import com.example.studentregistrationsystem.database.DBConnection;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
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

import static com.itextpdf.io.font.constants.StandardFonts.TIMES_BOLD;
import static java.lang.Double.parseDouble;

public class BursarController implements Initializable {
    private final DBConnection database = new DBConnection();
    private boolean isNewButtonClicked;

    private  boolean isEditButtonClicked;
//    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);


    private ResultSet result;
    private String sqlQuery;
    @FXML
    private TextField fieldStdID;
    @FXML
    private TextField fieldStdFeeAmount;

    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    TableView<FeeTable> feeTableView;
    @FXML
    TableColumn<FeeTable, String> colStdID;
    @FXML
    TableColumn<FeeTable, String> colStdName;
    @FXML
    TableColumn<FeeTable, String> colStdEmail;
    @FXML
    TableColumn<FeeTable, String> colCourse;
    @FXML
    TableColumn<FeeTable, Integer> colYear;
    @FXML
    TableColumn<FeeTable, Double> colFeeAmount;
    @FXML
    private TextField fieldSearch;
    @FXML
    private Label lblAdmissionsID;
    @FXML
    private Label lblAdmissionsName;
    private FeeTable feeTable;


    public void setAdd(){
        setAllEnable();
        isNewButtonClicked = true;
        isEditButtonClicked = false;
    }

    //Save button functionality
    public void save() throws SQLException {
        if(isNewButtonClicked){
            registerNewFeeAmount();
        }
        else if(isEditButtonClicked){
            updateStudent();
        }
    }
    //register Student
    public void registerNewFeeAmount() throws SQLException {
        if(!checkFieldsEmpty()){
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            // Selects the current Fee Amount of the student,
            // gets the resulting amount and substracts from the inputed amount
            ResultSet feeresult = statement.executeQuery("Select feeAmount from billstatement where " +
                "Std_ID='"+fieldStdID.getText()+"'");
            if(!feeresult.next()){
                errorAlert.setContentText("No student with that ID!");
                errorAlert.show();
            }
            else{
                double updatedFee = parseDouble(feeresult.getString("feeAmount"))
                    - parseDouble(fieldStdFeeAmount.getText()) ;
                sqlQuery = "Update billstatement set feeAmount='"+updatedFee+"' where Std_ID='"+fieldStdID.getText()+"';";

                int result = statement.executeUpdate(sqlQuery);
                if(result == 1){
                    informationAlert.setContentText("Student Fee Added Successfully!");
                    informationAlert.show();
                }
                else{
                    errorAlert.setHeaderText("Error encountered!");
                    errorAlert.show();
                }
            }

            setAllDisable();
            clearAll();

        }
        else{
            errorAlert.setContentText("All fields are required!");
            errorAlert.show();
        }
    }

    //Update Student Information

    public void updateStudent() throws SQLException{
        sqlQuery ="Update billstatement set Std_ID='"+fieldStdID.getText()+"'," +
            "feeAmount='"+parseDouble(fieldStdFeeAmount.getText())+"' where Std_ID='"+fieldStdID.getText()+"';";

        int result =  database.getConnection().createStatement().executeUpdate(sqlQuery);
        if(result == 1){
            informationAlert.setContentText("Student Updated Successfully");
            informationAlert.setHeaderText("Success");
            informationAlert.show();
        }
        else {
            errorAlert.setContentText("Error has occurred");
            errorAlert.show();
        }
        setAllDisable();
        clearAll();
    }

    //Deleting Student Information
    public void deleteStudent() throws SQLException {
        feeTable = feeTableView.getSelectionModel().getSelectedItem();

        if(feeTable == null){
            informationAlert.setContentText("Kindly select a record to delete!");
            informationAlert.show();
        }
        else{
            sqlQuery = "Delete from billstatement where Std_ID= '"+feeTable.getId()+"';";

            database.getConnection().createStatement().executeUpdate(sqlQuery);
            feeTableView.setItems(getFeeStatement(null));
        }
        assert feeTable != null;

    }

    //Editing Student Information
    public void editStudent() throws SQLException{
        isNewButtonClicked = false;
        isEditButtonClicked = true;
        feeTable = feeTableView.getSelectionModel().getSelectedItem();
        if(feeTable == null){
            informationAlert.setContentText("Kindly select a record to Edit!");
            informationAlert.show();
        }
        else{
            setAllEnable();
            sqlQuery = "Select * from billstatement where Std_ID='"+feeTable.getId()+"';";

            result = database.getConnection().createStatement().executeQuery(sqlQuery);
            while (result.next()){

            fieldStdID.setText(result.getString("Std_ID"));
            fieldStdFeeAmount.setText(result.getString("feeAmount"));

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
        sqlQuery = "Select students.Std_ID, students.Std_Name, students.Std_Email,students.Course, students.YOS," +
            "students.Status,billstatement.feeAmount from students inner join billstatement on students.Std_ID=billstatement.Std_ID " +
            "where billstatement.Std_ID LIKE '%"+fieldStdID.getText()+"%';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if(result.next()){
            feeTableView.setItems(getFeeStatement(sqlQuery));
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
        feeTableView.setItems(getFeeStatement(null));
    }

    private void setAllEnable() {
        fieldStdID.setDisable(false);
        fieldStdFeeAmount.setDisable(false);
        saveBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    private void setAllDisable() {
        fieldStdID.setDisable(true);
        fieldStdFeeAmount.setDisable(true);
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);

    }
    public void clearAll(){
        fieldStdID.setText("");
        fieldStdFeeAmount.setText("");
    }

    public boolean checkFieldsEmpty(){
        return fieldStdID.getText().isEmpty() || fieldStdFeeAmount.getText().isEmpty();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblAdmissionsID.setText(getBursarDetails().get(0));
            lblAdmissionsName.setText(getBursarDetails().get(1));
            setAllDisable();
            getFeeStatement(null);
            FeeAmountTable();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<FeeTable> getFeeStatement(String sqlParamsQuery) throws SQLException{
        Optional<String> sqlParamQuery = Optional.ofNullable(sqlParamsQuery);
        if(sqlParamQuery.isPresent()){
            sqlQuery = sqlParamsQuery;
        }
        else{

        sqlQuery = "Select students.Std_ID, students.Std_Name, students.Std_Email,students.Course, students.YOS," +
                "students.Status,billstatement.feeAmount from students inner join billstatement on students.Std_ID=billstatement.Std_ID";

        }
        ObservableList<FeeTable> feeTableData = FXCollections.observableArrayList();

        ResultSet result = database.getConnection().createStatement().executeQuery(sqlQuery);
        while (result.next()){
            feeTableData.add(new FeeTable(
                    result.getString("Std_ID"),
                    result.getString("Std_Name"),
                    result.getString("Std_Email"),
                    result.getString("Course"),
                    result.getInt("YOS"),
                    result.getDouble("feeAmount")

            ));
            System.out.println(result.getString("Std_ID"));
        }
        return feeTableData;
    }

    public void FeeAmountTable() throws SQLException {
        colStdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStdEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colFeeAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

       feeTableView.setItems(getFeeStatement(null));
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

    public ArrayList<String> getBursarDetails() throws SQLException{
        String id = "BURS-001";
        sqlQuery = "Select ID, Name from bursar where ID='"+id+"';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        ArrayList<String> admDetails = new ArrayList<>();
        while (result.next()){
            admDetails.add(result.getString("ID"));
            admDetails.add(result.getString("Name"));
        }
        return admDetails;
    }

    public void printFeeStatement(ActionEvent event) throws IOException {
        feeTable = feeTableView.getSelectionModel().getSelectedItem();

        if(feeTable == null){
            informationAlert.setContentText("Kindly select a record to print fee Statement!");
            informationAlert.show();
        }
        else{
            String path="/home/mugo/Downloads/"+String.valueOf(feeTable.getId()).replace("/","")+" Fee Statement.pdf";
            PdfWriter feeWriter = new PdfWriter(path);
            PdfDocument feeDocument = new PdfDocument(feeWriter);
            feeDocument.setDefaultPageSize(PageSize.A4);
            feeDocument.addNewPage();
            Style pStyles = new Style();
            pStyles.setFont(PdfFontFactory.createFont(TIMES_BOLD))
                    .setFontSize(12);


            Document feeDoc = new Document(feeDocument);

            String feeStatement = "\t\t Fee Statement \n"+
                    "============================================================================\n" +
                    "Student Registration Number:\t\t " + feeTable.getId() + "\n\n" +
                    "Name:\t\t " + feeTable.getName() + "\n\n" +
                    "Course Name:\t\t " + feeTable.getCourse() + "\n\n" +
                    "Year Of Study:\t\t " + feeTable.getYear() + "\n\n" +
                    "============================================================================\n" +
                    "Fees Amount:\t\t " + feeTable.getAmount() + "\n\n";
            Paragraph feeParagraph = new Paragraph(feeStatement).addStyle(pStyles);
            feeDoc.add(feeParagraph);
            feeDoc.close();

            confirmationAlert.setTitle("Print");
            confirmationAlert.setHeaderText("Downloaded");
            confirmationAlert.setContentText("Fee Statement Has Been Downloaded. Ready To Be Printed!");
            confirmationAlert.setResizable(false);
            Scene scene = ((Node) event.getSource()).getScene();
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if(result.isPresent()){
                if(result.get() == ButtonType.OK){
                    PrinterJob job = PrinterJob.createPrinterJob();
                    if(job == null){
                        errorAlert.setContentText("No printers Found!");
                        errorAlert.show();
                    }
                    else{
                        boolean proceed = job.showPrintDialog(scene.getWindow());
                        if(proceed){
                            if(job.printPage(feeTableView)){
                                job.endJob();
                            }
                        }
                    }

                }
            }

        }
    }

}
