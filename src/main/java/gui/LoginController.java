package gui;

import java.io.IOException;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

public class LoginController {

    @FXML
    TextField username;
    @FXML
    PasswordField psw;

    private ErrorPopup ErrorPopup = new ErrorPopup();


    public void logIn(ActionEvent a) throws Exception{
        
        User u = new User("STOP","SHOWING","FUCKINGERRORS");//Ty <3
        // User u = null;
        //u= TODO idk-idc.logIn(username.getText(),psw.getText()); 
        if(u==null){ErrorPopup.errorPopup(5);return;}
        switch (u.getRole()) {
            case "admin":
                loadAdminUI(a);
                break;
            case "doctor":
                loadDoctUI(a);
                break;
            case "patient":
                loadPatientUI(a,u);
                break;
            default:
                ErrorPopup.errorPopup(0);
                break;
        }
    }
    

    private void loadPatientUI(ActionEvent aEvent,User u) throws IOException, ClassNotFoundException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientController.fxml"));
		Parent root = loader.load();

        PatientController pc = loader.getController();
        pc.setSelf(u);
        if(pc.myself==null){
            ErrorPopup.errorPopup(11); //TODO
        } else {
            Stage stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    private void loadAdminUI(ActionEvent aEvent) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminController.fxml"));
		Parent root = loader.load();

        Stage stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    private void loadDoctUI(ActionEvent aEvent) throws IOException, ClassNotFoundException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorController.fxml"));
		Parent root = loader.load();

        Stage stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        

    }


}
