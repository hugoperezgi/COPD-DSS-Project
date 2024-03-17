package gui;

import java.net.URL;
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
    public ComboBox<Integer> comboBoxMedicalHistoryIDs;
    @FXML
    public TableView<MedicalHistory> tableMedHistory;
    @FXML
    public Button buttonStartTest;


    @FXML
    public Pane paneCATTest;
    @FXML
    public Slider sliderCoughScore;
    @FXML
    public Slider sliderMucusScore;
    @FXML
    public Slider sliderTightScore;
    @FXML
    public Slider sliderBreathlessScore;
    @FXML
    public Slider sliderActivityLimitationScore;
    @FXML
    public Slider sliderConfidentScore;
    @FXML
    public Slider sliderSleepScore;
    @FXML
    public Slider sliderEnergyScore;
    @FXML
    public Button buttonSendTest;


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
        hideAll();
        //show pane de la tabla con todos los medical history
        List<MedicalHistory> myMedHistory = new ArrayList<>(DbManager.getMedicalHistory(myself.getId()));
        //nose añadirlos a la tabla @Hugo para ti guapi y tb falta añadir los ids al combo box para que se pueda elegir :)
        resetAndShowPaneMedicalHist();
    }

    @FXML
    public void newCATTest(){
        hideAll();
        medicalHistoryID = comboBoxMedicalHistoryIDs.getValue();
        //show pane del CAT test como tal
        resetAndShowPaneCatTest();
    }

    @FXML
    public void calculateCATTestScore() throws Exception {
        int patient_severity = severityFromCAT(sliderCoughScore.getValue(), sliderMucusScore.getValue(), sliderTightScore.getValue(), sliderBreathlessScore.getValue(), sliderActivityLimitationScore.getValue(), sliderConfidentScore.getValue(), sliderSleepScore.getValue(), sliderEnergyScore.getValue());
        DbManager.updateSeverity(medicalHistoryID,patient_severity);
        //popup de sucess que nose lanzar y vuelta al inicio @Hugo
        chooseID();
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

    private void hideAll(){
        //panel medical history
        paneShowMedicalHist.setVisible(false);
        paneShowMedicalHist.setDisable(true);
        tableMedHistory.setVisible(false);
        tableMedHistory.setDisable(true);
        comboBoxMedicalHistoryIDs.setVisible(false);
        comboBoxMedicalHistoryIDs.setDisable(true);
        buttonStartTest.setVisible(false);
        buttonStartTest.setDisable(true);

        //panel CAT TEST
        paneCATTest.setVisible(false);
        paneCATTest.setDisable(true);
        sliderConfidentScore.setVisible(false);
        sliderConfidentScore.setDisable(true);
        sliderEnergyScore.setVisible(false);
        sliderEnergyScore.setDisable(true);
        sliderBreathlessScore.setVisible(false);
        sliderBreathlessScore.setDisable(true);
        sliderCoughScore.setVisible(false);
        sliderCoughScore.setDisable(true);
        sliderMucusScore.setVisible(false);
        sliderMucusScore.setDisable(true);
        sliderActivityLimitationScore.setVisible(false);
        sliderActivityLimitationScore.setDisable(true);
        sliderSleepScore.setVisible(false);
        sliderSleepScore.setDisable(true);
        sliderTightScore.setVisible(false);
        sliderTightScore.setDisable(true);
        buttonSendTest.setVisible(false);
        buttonSendTest.setDisable(true);
    }

    private void resetAndShowPaneMedicalHist(){
        paneShowMedicalHist.setVisible(true);
        paneShowMedicalHist.setDisable(false);
        tableMedHistory.setVisible(true);
        tableMedHistory.setDisable(false);
        comboBoxMedicalHistoryIDs.setVisible(true);
        comboBoxMedicalHistoryIDs.setDisable(false);
        buttonStartTest.setVisible(true);
        buttonStartTest.setDisable(false);

        comboBoxMedicalHistoryIDs.getSelectionModel().clearSelection();
    }

    private void resetAndShowPaneCatTest(){
        paneCATTest.setVisible(true);
        paneCATTest.setDisable(false);
        sliderConfidentScore.setVisible(true);
        sliderConfidentScore.setDisable(false);
        sliderEnergyScore.setVisible(true);
        sliderEnergyScore.setDisable(false);
        sliderBreathlessScore.setVisible(true);
        sliderBreathlessScore.setDisable(false);
        sliderCoughScore.setVisible(true);
        sliderCoughScore.setDisable(false);
        sliderMucusScore.setVisible(true);
        sliderMucusScore.setDisable(false);
        sliderActivityLimitationScore.setVisible(true);
        sliderActivityLimitationScore.setDisable(false);
        sliderSleepScore.setVisible(true);
        sliderSleepScore.setDisable(false);
        sliderTightScore.setVisible(true);
        sliderTightScore.setDisable(false);
        buttonSendTest.setVisible(true);
        buttonSendTest.setDisable(false);

    }

    
}
