package gui;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;

import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sql.DbManager;

public class PatientController implements Initializable{

    public Patient myself;
    public int medicalHistoryID;
    public ErrorPopup err = new ErrorPopup();
    public SuccessPopup succ = new SuccessPopup();

    @FXML
    public Pane paneShowMedicalHist;
    @FXML
    public ComboBox<Integer> comboBoxMedicalHistoryIDs;
    @FXML
    public TableView<MedicalHistory> tableMedHistory;
    @FXML
    public TableColumn<MedicalHistory,Integer> columnMedHistId;
    @FXML
    public TableColumn<MedicalHistory, Date> columnMedHistDate;
    @FXML
    public TableColumn<MedicalHistory,Character> columnMedHistPhenotype;
    @FXML
    public TableColumn<MedicalHistory,Integer> columnMedHistSeverity;
    @FXML
    public TableColumn<MedicalHistory,String> columnMedHistTreatment;

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
    @FXML
    public Text textProvisionaCATScore;


    public void setSelf(User u) throws Exception {
        myself = new Patient(DbManager.getPatientFromUserID(u.getUserID()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnMedHistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnMedHistDate.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
        columnMedHistPhenotype.setCellValueFactory(new PropertyValueFactory<>("phenotype"));
        columnMedHistSeverity.setCellValueFactory(new PropertyValueFactory<>("stringSever"));
        columnMedHistTreatment.setCellValueFactory(new PropertyValueFactory<>("suggestedTreatment"));
    }

    @FXML
    private void logOut(){
        System.exit(0);
    }
    private List<MedicalHistory> myMedHistory;
    @FXML
    public void chooseID() throws Exception {
        hideAll();
        //show pane de la tabla con todos los medical history
        myMedHistory = new ArrayList<>(DbManager.getMedicalHistory(myself.getMedicalCardNumber()));
        List<Integer> medhistoryIds = new ArrayList<>();
        tableMedHistory.getItems().setAll(myMedHistory);
        int medhistoryCount = myMedHistory.size();
        for (int i = 0; i<medhistoryCount; i++){
            medhistoryIds.add(myMedHistory.get(i).getId());
        }
        comboBoxMedicalHistoryIDs.getItems().setAll(medhistoryIds);
        resetAndShowPaneMedicalHist();
    }

    @FXML
    public void newCATTest(){
        hideAll();
        medicalHistoryID = comboBoxMedicalHistoryIDs.getSelectionModel().getSelectedItem();
        //show pane del CAT test como tal
        resetAndShowPaneCatTest();
    }

    @FXML
    public void calculateCATTestScore() throws Exception { 
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setCOPDScore(severityFromCAT(sliderCoughScore.getValue(), sliderMucusScore.getValue(), sliderTightScore.getValue(), sliderBreathlessScore.getValue(), sliderActivityLimitationScore.getValue(), sliderConfidentScore.getValue(), sliderSleepScore.getValue(), sliderEnergyScore.getValue()));
        MedicalHistory m = null;
        for (MedicalHistory medicalHistory : myMedHistory) {
            if(medicalHistory.getId()==medicalHistoryID){m=medicalHistory;}
        }
        m.setSignsAndSymptoms(ss);
        try {
            MedicalHistory_Unit mu = new MedicalHistory_Unit();
            RuleUnitInstance<MedicalHistory_Unit> drl = RuleUnitProvider.get().createRuleUnitInstance(mu);
            mu.getMHist().add(m);
            drl.fire();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DbManager.updateSeverity(medicalHistoryID,m.getSeverityLevel());
        DbManager.updateTreatmentAndDates(medicalHistoryID, m.getSuggestedTreatment(),m.getBeginDate(),m.getDuration());
        succ.successPopup(0);
        hideAll();
    }

    @FXML
    public void provisionalCATScore(){
        int prov_score = (int) (sliderCoughScore.getValue()+sliderMucusScore.getValue()+sliderTightScore.getValue()+sliderBreathlessScore.getValue()+sliderActivityLimitationScore.getValue()+sliderConfidentScore.getValue()+sliderSleepScore.getValue()+sliderEnergyScore.getValue());
        textProvisionaCATScore.setText(String.valueOf(prov_score));
    }

    private int severityFromCAT(double cough, double mucus, double tight, double breathless, double activityLimitation, double confident, double sleep, double energy) {
        return (int) (cough + mucus + tight + breathless + activityLimitation + confident + sleep + energy);
    }

    private void hideAll(){
        //panel medical history
        paneShowMedicalHist.setVisible(false);
        paneShowMedicalHist.setDisable(true);

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
        hideDetailedPheno();
        hideDetailedTrtmnt();
    }

    private void resetAndShowPaneMedicalHist(){
        paneShowMedicalHist.setVisible(true);
        paneShowMedicalHist.setDisable(false);

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

    @FXML
    private Group grpDetailedPheno;
        @FXML
        private void hideDetailedPheno(){
            grpDetailedPheno.setDisable(true);
            grpDetailedPheno.setVisible(false);
        }
        @FXML
        private void toggleDetailedPheno(){
            hideDetailedTrtmnt();
            if(grpDetailedPheno.isVisible()){hideDetailedPheno();}else{
                grpDetailedPheno.setVisible(true);grpDetailedPheno.setDisable(false);
            }
        }

    @FXML
    private Group grpDetailedTrtmnt;
        @FXML
        private void hideDetailedTrtmnt(){
            grpDetailedTrtmnt.setDisable(true);
            grpDetailedTrtmnt.setVisible(false);
        }
        @FXML
        private void toggleDetailedTrtmnt(){
            hideDetailedPheno();
            if(grpDetailedTrtmnt.isVisible()){hideDetailedTrtmnt();}else{
                grpDetailedTrtmnt.setVisible(true);grpDetailedTrtmnt.setDisable(false);
            }
        }
    }
