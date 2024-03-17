package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import sql.DbManager;

public class PatientController implements Initializable{

    public Patient myself;
    public int medicalHistoryID;

    @FXML
    public Pane paneShowMedicalHist;
    @FXML
    public ComboBox<Integer> medicalHistoryIDs;
    @FXML
    public TableView<MedicalHistory> tableMedHistory;


    @FXML
    public Pane paneCATTest;
    @FXML
    public Slider coughScore;
    @FXML
    public Slider mucusScore;
    @FXML
    public Slider tightScore;
    @FXML
    public Slider breathlessScore;
    @FXML
    public Slider activityLimitationScore;
    @FXML
    public Slider confidentScore;
    @FXML
    public Slider sleepScore;
    @FXML
    public Slider energyScore;


    public void setSelf(User u) throws Exception {
        myself = new Patient(DbManager.getPatientFromUserID(u.getUserID()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void logOut(){
        System.exit(0);
    }

    @FXML
    public void chooseID() throws Exception {
        //show pane de la tabla con todos los medical history
        List<MedicalHistory> myMedHistory = new ArrayList<>(DbManager.getMedicalHistory(myself.getId()));
        //nose añadirlos a la tabla @Hugo para ti guapi y tb falta añadir los ids al combo box para que se pueda elegir :)
        paneShowMedicalHist.setVisible(true);
        paneCATTest.setVisible(false);
    }

    @FXML
    public void newCATTest(){
        medicalHistoryID = medicalHistoryIDs.getValue();
        //show pane del CAT test como tal
        paneShowMedicalHist.setVisible(false);
        paneCATTest.setVisible(true);
    }

    @FXML
    public void calculateCATTestScore() throws Exception {
        int patient_severity = severityFromCAT(coughScore.getValue(), mucusScore.getValue(), tightScore.getValue(), breathlessScore.getValue(), activityLimitationScore.getValue(), confidentScore.getValue(), sleepScore.getValue(), energyScore.getValue());
        DbManager.updateSeverity(medicalHistoryID,patient_severity);
        //popup de sucess que nose lanzar y vuelta al inicio @Hugo
        paneCATTest.setVisible(false);
        paneShowMedicalHist.setVisible(false);
    }

    private int severityFromCAT(double cough, double mucus, double tight, double breathless, double activityLimitation, double confident, double sleep, double energy) {
        int overall_score = (int) (cough + mucus + tight + breathless + activityLimitation + confident + sleep + energy);
        if (overall_score < 10) {
            return 1;
        } else if (overall_score < 20) {
            return 2;
        } else if (overall_score < 30) {
            return 3;
        } else {
            return 4;
        }
    }

    
}
