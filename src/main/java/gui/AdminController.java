package gui;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sql.DbManager;

public class AdminController implements Initializable{


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
    @FXML
    public Button buttonCreateStaffMember;

    @FXML
    public Pane paneDeleteUser;
    @FXML
    public TableView<User> tableViewUsers;
    @FXML
    public ComboBox<Integer> comboBoxUsers;
    @FXML
    public Button buttonDeleteUser;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // comboBoxRole.getItems().setAll(roles);
        // comboBoxBloodType.getItems().setAll(bloodTypeStrings);

        // columnUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        // columnUserRole.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRoleString()));
        // columnUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));

    }     
        
    
    public void logOut(){
        System.exit(0);
    }

    public void createPatient() throws Exception {
        //TODO: comprobacion de que se ha rellenado lo obligatorio y sino lanzar error popup
        String username = textFieldUsernamePatient.getText();
        String password = pswFieldPatient.getText();
        String role = "Patient";
        User new_user = new User(username,password,role);
        int check = DbManager.check_user(new_user);
        if(check>=0){
            int user_id = DbManager.createUser(new_user.getUsername(),new_user.getEncryptedPassword(),new_user.getRole());
            String patientname = textFieldNamePatient.getText();
            int medicalCardNumber = Integer.getInteger(textFieldMedCardNumPatient.getText());
            Date birthdate = Date.valueOf(datePickerBirthdate.getValue());
            DbManager.createPatient(patientname,medicalCardNumber,birthdate,user_id);
            //lanzar pop up success @hugo
        } else {
          //error combinacion username-psw ya esta en la db  @hugo
        }


    }

    public void createStaffMember() throws Exception {
        //TODO: comprobacion de que se ha rellenado lo obligatorio y sino lanzar error popup
        String username = textFieldUsernameStaffMember.getText();
        String password = pswFieldStaffMember.getText();
        String role = comboBoxRole.getValue();
        User new_user = new User(username,password,role);
        int check = DbManager.check_user(new_user);
        if(check>=0) {
            int user_id = DbManager.createUser(new_user.getUsername(), new_user.getEncryptedPassword(), new_user.getRole());
        } else {
            //error combinacion username-psw ya esta en la db  @hugo
        }

    }

    public void deleteUser() throws Exception {
        int id = comboBoxUsers.getValue();
        DbManager.deleteUser(id);
        //pop up de success @hugo
    }

    public void newPatient(){
        hideAll();
        showAndResetCreatePatientPane();
    }

    public void newStaffMember(){
        hideAll();
        showAndResetCreateStaffMemberPane();
    }

    public void deleteUserMenu(){
        hideAll();
        showAndResetDeleteUserPane();
        //añadir a tabla y combobox lista de usuarios e ids en el combo box (no muestro password por motivos obvios, resto esta) @hugo

    }

    public void hideAll(){
        paneNewPatient.setVisible(false);
        paneNewPatient.setDisable(true);
        buttonCreatePatient.setVisible(false);
        buttonCreatePatient.setDisable(true);
        pswFieldPatient.setVisible(false);
        pswFieldPatient.setDisable(true);
        textFieldMedCardNumPatient.setVisible(false);
        textFieldMedCardNumPatient.setDisable(true);
        textFieldNamePatient.setVisible(false);
        textFieldNamePatient.setDisable(true);
        textFieldUsernamePatient.setVisible(false);
        textFieldUsernamePatient.setDisable(true);
        datePickerBirthdate.setVisible(false);
        datePickerBirthdate.setDisable(true);

        paneNewStaffMember.setVisible(false);
        paneNewStaffMember.setDisable(true);
        pswFieldStaffMember.setVisible(false);
        pswFieldStaffMember.setDisable(true);
        textFieldUsernameStaffMember.setVisible(false);
        textFieldUsernameStaffMember.setDisable(true);
        buttonCreateStaffMember.setVisible(false);
        buttonCreateStaffMember.setDisable(true);
        comboBoxRole.setVisible(false);
        comboBoxRole.setDisable(true);

        paneDeleteUser.setVisible(false);
        paneDeleteUser.setDisable(true);
        buttonDeleteUser.setVisible(false);
        buttonDeleteUser.setDisable(true);
        tableViewUsers.setVisible(false);
        tableViewUsers.setDisable(true);
        comboBoxUsers.setVisible(false);
        comboBoxUsers.setDisable(true);
    }

    public void showAndResetCreatePatientPane(){
        paneNewPatient.setVisible(true);
        paneNewPatient.setDisable(false);
        buttonCreatePatient.setVisible(true);
        buttonCreatePatient.setDisable(false);
        pswFieldPatient.setVisible(true);
        pswFieldPatient.setDisable(false);
        textFieldMedCardNumPatient.setVisible(true);
        textFieldMedCardNumPatient.setDisable(false);
        textFieldNamePatient.setVisible(true);
        textFieldNamePatient.setDisable(false);
        textFieldUsernamePatient.setVisible(true);
        textFieldUsernamePatient.setDisable(false);
        datePickerBirthdate.setVisible(true);
        datePickerBirthdate.setDisable(false);

        pswFieldPatient.clear();
        textFieldUsernamePatient.clear();
        textFieldNamePatient.clear();
        textFieldMedCardNumPatient.clear();
        datePickerBirthdate.setValue(null);
    }

    public void showAndResetCreateStaffMemberPane(){
        paneNewStaffMember.setVisible(true);
        paneNewStaffMember.setDisable(false);
        pswFieldStaffMember.setVisible(true);
        pswFieldStaffMember.setDisable(false);
        textFieldUsernameStaffMember.setVisible(true);
        textFieldUsernameStaffMember.setDisable(false);
        buttonCreateStaffMember.setVisible(true);
        buttonCreateStaffMember.setDisable(false);
        comboBoxRole.setVisible(true);
        comboBoxRole.setDisable(false);

        pswFieldStaffMember.clear();
        textFieldUsernameStaffMember.clear();
        comboBoxRole.getSelectionModel().clearSelection();
    }

    public void showAndResetDeleteUserPane(){
        paneDeleteUser.setVisible(true);
        paneDeleteUser.setDisable(false);
        buttonDeleteUser.setVisible(true);
        buttonDeleteUser.setDisable(false);
        tableViewUsers.setVisible(true);
        tableViewUsers.setDisable(false);
        comboBoxUsers.setVisible(true);
        comboBoxUsers.setDisable(false);

        comboBoxUsers.getSelectionModel().clearSelection();
    }
}