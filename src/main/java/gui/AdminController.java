package gui;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import sql.DbManager;

public class AdminController implements Initializable{

    public ErrorPopup err = new ErrorPopup();
    public SuccessPopup succ = new SuccessPopup();

    @FXML
    public Pane paneNewPatient;
    @FXML
    public PasswordField pswFieldPatient;
    @FXML
    public TextField textFieldUsernamePatient;
    @FXML
    public TextField textFieldNamePatient;
    @FXML
    public TextField textFieldMedCardNumPatient;
    @FXML
    public DatePicker datePickerBirthdate;
    @FXML
    public Button buttonCreatePatient;

    @FXML
    public Pane paneNewStaffMember;
    @FXML
    public PasswordField pswFieldStaffMember;
    @FXML
    public TextField textFieldUsernameStaffMember;
    @FXML
    public ComboBox<String> comboBoxRole;
    public String[] roles = {"Doctor", "Admin"};
    @FXML
    public Button buttonCreateStaffMember;

    @FXML
    public Pane paneDeleteUser;
    @FXML
    public TableView<User> tableViewUsers;
    @FXML
    public TableColumn<User, Integer> columnUserIds;
    @FXML
    public TableColumn<User, String> columnUserNames;
    @FXML
    public TableColumn<User, String> columnUserRoles;
    @FXML
    public ComboBox<Integer> comboBoxUsers;
    public List<Integer> usersIDs = new ArrayList<>();
    @FXML
    public Button buttonDeleteUser;


    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxRole.getItems().setAll(roles);

        columnUserIds.setCellValueFactory(new PropertyValueFactory<>("userID"));
        columnUserNames.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnUserRoles.setCellValueFactory(new PropertyValueFactory<>("role"));
    }     
        
    
    public void logOut(){
        System.exit(0);
    }

    public void createPatient() throws Exception {
        if(textFieldUsernamePatient.getText() == "" || pswFieldPatient.getText() =="" || textFieldNamePatient.getText()=="" || textFieldMedCardNumPatient.getText()== ""){
            //no debe estar vacio pq esta mierda no salta TODO: fix it
            err.errorPopup("Please, fill in all of the mandatory fields: username, password, name, medical card number");
        } else {
            String username = textFieldUsernamePatient.getText();
            String password = pswFieldPatient.getText();
            String role = "Patient";
            User new_user = new User(username, password, role);
            if (DbManager.check_user(new_user.getUsername(), new_user.getEncryptedPassword()) != null) {
                int user_id = DbManager.createUser(new_user.getUsername(), new_user.getEncryptedPassword(), new_user.getRole());
                String patientname = textFieldNamePatient.getText();
                int medicalCardNumber = Integer.getInteger(textFieldMedCardNumPatient.getText());
                Date birthdate = Date.valueOf(datePickerBirthdate.getValue());
                DbManager.createPatient(patientname, medicalCardNumber, birthdate, user_id);
                succ.successPopup(4);
            } else {
                err.errorPopup("The combination of user and password is already in use. Please, try again.");
                //error combinacion username-psw ya esta en la db
            }
        }


    }

    public void createStaffMember() throws Exception {
        if(textFieldUsernameStaffMember.getText().equalsIgnoreCase("") || pswFieldStaffMember.getText().equalsIgnoreCase("") || comboBoxRole.getSelectionModel().isEmpty()){
            err.errorPopup("Please, fill in all of the mandatory fields: username, password and role");
        } else {
            String username = textFieldUsernameStaffMember.getText();
            String password = pswFieldStaffMember.getText();
            String role = comboBoxRole.getSelectionModel().getSelectedItem();
            User new_user = new User(username, password, role);
            if (DbManager.check_user(new_user.getUsername(), new_user.getEncryptedPassword()) != null) {
                DbManager.createUser(new_user.getUsername(), new_user.getEncryptedPassword(), new_user.getRole());
                succ.successPopup(5);
            } else {
                err.errorPopup("The combination of user and password is already in use. Please, try again.");
                //error combinacion username-psw ya esta en la db  @hugo
            }
        }
    }



    public void deleteUser() throws Exception {
        int id = comboBoxUsers.getSelectionModel().getSelectedItem();
        DbManager.deleteUser(id);
        succ.successPopup(6);
    }

    public void newPatient(){
        hideAll();
        showAndResetCreatePatientPane();
    }

    public void newStaffMember(){
        hideAll();
        showAndResetCreateStaffMemberPane();
    }

    public void deleteUserMenu() throws Exception {
        hideAll();
        showAndResetDeleteUserPane();
        List<User> allusers = new ArrayList<>();
        allusers.addAll(DbManager.getAllUsers());
        int userCount = allusers.size();
        for (int i = 0; i<userCount; i++){
            usersIDs.add(allusers.get(i).getUserID());
        }
        comboBoxUsers.getItems().setAll(usersIDs);
        tableViewUsers.getItems().setAll(allusers);
    }

    public void hideAll(){
        paneNewPatient.setVisible(false);
        paneNewPatient.setDisable(true);

        paneNewStaffMember.setVisible(false);
        paneNewStaffMember.setDisable(true);

        paneDeleteUser.setVisible(false);
        paneDeleteUser.setDisable(true);
    }

    public void showAndResetCreatePatientPane(){
        paneNewPatient.setVisible(true);
        paneNewPatient.setDisable(false);

        pswFieldPatient.clear();
        textFieldUsernamePatient.clear();
        textFieldNamePatient.clear();
        textFieldMedCardNumPatient.clear();
        datePickerBirthdate.setValue(null);
    }

    public void showAndResetCreateStaffMemberPane(){
        paneNewStaffMember.setVisible(true);
        paneNewStaffMember.setDisable(false);

        pswFieldStaffMember.clear();
        textFieldUsernameStaffMember.clear();
        comboBoxRole.getSelectionModel().clearSelection();
    }

    public void showAndResetDeleteUserPane(){
        paneDeleteUser.setVisible(true);
        paneDeleteUser.setDisable(false);

        comboBoxUsers.getSelectionModel().clearSelection();
    }
}