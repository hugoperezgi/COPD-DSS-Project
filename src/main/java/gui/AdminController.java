package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class AdminController implements Initializable{
    
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
}