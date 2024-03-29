package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;

import entities.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sql.DbManager;

public class DoctorController implements Initializable{

    private ErrorPopup ErrorPopup = new ErrorPopup();
    private SuccessPopup SuccessPopup = new SuccessPopup();

    private User myself;
    private Patient p;

    private List<Patient> pList=null;
    private List<MedicalHistory> mhList=null;
    private MedicalHistory mHist=null;
    private Integer ptId=null;
    private Integer plId=null;

    public void setSelf(User u){
        myself=u;
    }
    public User getMyself() {
        return myself;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* Patient table */
        columnPatientID.setCellValueFactory(new PropertyValueFactory<>("medicalCardNumber"));
        columnPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        /* Medical History table */
        columnMedTestId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnMedTestDate.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
        columnMedTestPheno.setCellValueFactory(new PropertyValueFactory<>("phenotype"));
        columnMedTestSever.setCellValueFactory(new PropertyValueFactory<>("stringSever"));
        setTFieldCreateReport();
        setTFReportView();
        cbBMI.getItems().addAll(strBMI);
        cbBmiCreate.getItems().addAll(strBMI);
        hideAll();
        resetAll();
    }

    /* Generic */

    @FXML
    private Button buttonCheckLight;
    @FXML
    private Button buttonCheckDark;

    @FXML
    private void logOut(){
        try{DbManager.close_db();}catch(Exception e){/* And you'll be happy */}
        System.exit(0);
    }

    private void drkChckBtn(){
        buttonCheckLight.setDisable(true);
        buttonCheckLight.setVisible(false);
        buttonCheckDark.setDisable(false);
        buttonCheckDark.setVisible(true);
    }
    private void lightChckBtn(){
        buttonCheckLight.setDisable(false);
        buttonCheckLight.setVisible(true);
        buttonCheckDark.setDisable(true);
        buttonCheckDark.setVisible(false);
    }
    private void hideAll(){

        lightChckBtn();

        buttonSaveChange.setDisable(true);
        groupEditSeverity.setVisible(false);
        groupEditSeverity.setDisable(true);
        panePatientList.setVisible(false);
        panePatientList.setDisable(true);
        paneShowPatient.setVisible(false);
        paneShowPatient.setDisable(true);
        paneCreateReport.setVisible(false);
        paneCreateReport.setDisable(true);
        paneDetailedReport.setVisible(false);
        paneDetailedReport.setDisable(true);

    }
    private void resetAll(){
        trtmntModified=false;dateModified=false;durationModified=false;
        pList=null;mhList=null;mHist=null;p=null;plId=null;ptId=null;
        textPatientName.setText(null);;textBDate.setText(null);;;
        cbBoxSelectReport.getSelectionModel().clearSelection();
        cbBoxSelectPatient.getSelectionModel().clearSelection();
    }
    

    /* Patient List view */
    @FXML
    private Pane panePatientList;

        @FXML
        private ComboBox<String> cbBoxSelectPatient;
        @FXML
        private TableColumn<Patient,Integer> columnPatientID;
        @FXML
        private TableColumn<Patient,String> columnPatientName;
        @FXML
        private TableView<Patient> tableViewPatients;

        @FXML
        private void showPatients() throws Exception{
            hideAll();
            resetAll();
            drkChckBtn();

            loadPatientTable();

            panePatientList.setVisible(true);
            panePatientList.setDisable(false);
        }
        private void loadPatientTable() throws Exception{
            pList=DbManager.getAllPatients();
            if(pList.isEmpty()){ErrorPopup.errorPopup("No patients found on the DB.");hideAll();}
            tableViewPatients.getItems().clear();
            tableViewPatients.getItems().addAll(pList);
            cbBoxSelectPatient.getSelectionModel().clearSelection();
                String[] sList = new String[pList.size()];
                int i=0;
                for (Patient p : pList) {
                    sList[i]="Id: "+p.getMedicalCardNumber()+" - ("+p.getName()+")";
                    i++;
                }
            cbBoxSelectPatient.getItems().setAll(sList);
        }
        @FXML
        private void showPatientInfo() throws Exception{
            if(cbBoxSelectPatient.getSelectionModel().isEmpty()){
                ErrorPopup.errorPopup("Please select a patient.");
                return;
            }

            plId=cbBoxSelectPatient.getSelectionModel().getSelectedIndex();
            ptId=pList.get(plId).getId();
            mhList=DbManager.getMedicalHistory(ptId);
            p=pList.get(cbBoxSelectPatient.getSelectionModel().getSelectedIndex());
            textPatientName.setText(p.getName());
            textBDate.setText(p.getBirthDate().toString());
            loadMHistTable();

            hideAll();
            paneShowPatient.setVisible(true);
            paneShowPatient.setDisable(false);
        }
        


    /* Patient info view */
    @FXML
    private Pane paneShowPatient;

        @FXML
        private TableColumn<MedicalHistory,Integer> columnMedTestId;
        @FXML
        private TableColumn<MedicalHistory,String> columnMedTestDate;
        @FXML
        private TableColumn<MedicalHistory,Character> columnMedTestPheno;
        @FXML
        private TableColumn<MedicalHistory,String> columnMedTestSever;
        @FXML
        private TableView<MedicalHistory> tableViewMedTests;
        @FXML
        private Text textPatientName;
        @FXML
        private Text textBDate;
        @FXML
        private Button buttonCheckReport;
        @FXML
        private ComboBox<String> cbBoxSelectReport;
        
        private void loadMHistTable() throws Exception{
            tableViewMedTests.getItems().clear();
            if(mhList.isEmpty()){buttonCheckReport.setDisable(true);cbBoxSelectReport.setDisable(true);}else{
                buttonCheckReport.setDisable(false);
                cbBoxSelectReport.setDisable(false);
                tableViewMedTests.getItems().addAll(mhList);
                cbBoxSelectReport.getSelectionModel().clearSelection();
                    String[] sList = new String[mhList.size()];
                    int i=0;
                    for (MedicalHistory p : mhList) {
                        sList[i]="Id: "+p.getId();
                        i++;
                    }
                cbBoxSelectReport.getItems().setAll(sList);
            }
        }
        @FXML
        private void createReport(){
            hideAll();
            paneCreateReport.setDisable(false);
            paneCreateReport.setVisible(true);
            checkSeverGrp.setSelected(false);
            sliderDyspneaScale.setValue(0);
            severityGroup.setDisable(true);
            rbChronExN.setSelected(false);rbChronExY.setSelected(false);
            rbCoughY.setSelected(false);rbCoughN.setSelected(false);
            rbAATDN.setSelected(false);rbAATDY.setSelected(false);
            checkMinor0.setSelected(false);checkMinor1.setSelected(false);checkMinor2.setSelected(false);
            checkMayor0.setSelected(false);checkMayor1.setSelected(false);checkMayor2.setSelected(false);
            tFieldExaC.clear();
            tFieldExaT.clear();
            tFieldActivityperDayJEJ.clear();
            tFieldFEV.clear();
            cbBmiCreate.getSelectionModel().clearSelection();
        }
        @FXML
        private void checkReport() throws Exception{
            if(cbBoxSelectReport.getSelectionModel().isEmpty()){
                ErrorPopup.errorPopup("Please select a report.");
                return;
            }

            buttonDu.setDisable(false);buttonDa.setDisable(false);
            buttonDu.setVisible(true);buttonDa.setVisible(true);
            txtSDate.setVisible(true);txtDur.setVisible(true);
            buttonSaveChange.setDisable(true);
            mHist=mhList.get(cbBoxSelectReport.getSelectionModel().getSelectedIndex());
            txtTreatmnt.clear();txtTreatmnt.setPromptText("No treatment available yet.");
            txtDuration.setText("0");dateSdate.setValue(null);
            txtDuration.setVisible(false);txtDuration.setDisable(true);
            dateSdate.setVisible(false);dateSdate.setDisable(true);
            txtPName.setText(p.getName());
            if(mHist.getPhenotype().equalsIgnoreCase("?")){txtPheno.setText("NonConclusive Phenotype");}else{txtPheno.setText(mHist.getPhenotype());}
            txtSever.setText(mHist.getStringSever());
            if(mHist.getSuggestedTreatment()!=null){txtTreatmnt.setText(mHist.getSuggestedTreatment());}
            if(mHist.getBeginDate()==null){txtDur.setText("Test not completed yet - Unknown duration");txtSDate.setText("Test not completed yet - Start date not available");buttonModifySever.setDisable(false);}
                else{txtDur.setText(""+mHist.getDuration()+" Days");txtDuration.setText(""+mHist.getDuration());dateSdate.setValue(mHist.getBeginDate().toLocalDate());txtSDate.setText(mHist.getBeginDate().toString());buttonModifySever.setDisable(true);}                

            hideAll();
            paneDetailedReport.setVisible(true);
            paneDetailedReport.setDisable(false);
        }
        @FXML
        private void backToPatientView() throws Exception{
            textPatientName.setText(p.getName());
            textBDate.setText(p.getBirthDate().toString());
            loadMHistTable();

            hideAll();
            paneShowPatient.setVisible(true);
            paneShowPatient.setDisable(false);
        }

    /* Detailed Report view */
    @FXML
    private Pane paneDetailedReport;

        @FXML
        private Text txtPName;
        @FXML
        private Button buttonModifySever;
        @FXML
        private Button buttonSaveChange;
        @FXML
        private Button buttonDu;
        @FXML
        private Button buttonDa;
        @FXML
        private Text txtPheno;
        @FXML
        private Text txtSever;
        @FXML
        private Text txtDur;
        @FXML
        private Text txtSDate;
        @FXML
        private TextArea txtTreatmnt;
        @FXML
        private TextField txtDuration;
        @FXML
        private DatePicker dateSdate;
        @FXML
        private Group groupEditSeverity; 
        @FXML
        private TextField txtRVActv;
        @FXML
        private ComboBox<String> cbBMI;
        private String[] strBMI = {">21","≤21"};
        @FXML
        private TextField txtRVFev;
        @FXML
        private Slider sliderRVmMCR;

        private void setTFReportView(){
            txtDuration.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        txtDuration.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            txtRVActv.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        txtRVActv.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            txtRVFev.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        txtRVFev.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });

        }

        @FXML
        private void completeSever(){
            sliderRVmMCR.setValue(0);
            groupEditSeverity.setDisable(false);
            groupEditSeverity.setVisible(true);
        }
        @FXML
        private void modifySeverity() throws IOException{
            if(txtRVFev.getText().isEmpty()||txtRVActv.getText().isEmpty()||cbBMI.getSelectionModel().isEmpty()){ErrorPopup.errorPopup("Please fill all the options.");return;}
            mHist.signsAndSymptoms = new SignsAndSymptoms();
            mHist.signsAndSymptoms.setmMCR((int)sliderRVmMCR.getValue());
            
            //Change for Body Mass Index
            mHist.signsAndSymptoms.setBmi(cbBMI.getSelectionModel().isSelected(1));
                //BMI==true -> bmi<=21 AKA index 1
            mHist.signsAndSymptoms.setActivityMinutes(Integer.parseInt(txtRVActv.getText()));
            mHist.signsAndSymptoms.setFEV(Integer.parseInt(txtRVFev.getText()));
            mHist.signsAndSymptoms.calculateBODEx();

            try {
                MedicalHistory_Unit mu = new MedicalHistory_Unit();
                RuleUnitInstance<MedicalHistory_Unit> drl = RuleUnitProvider.get().createRuleUnitInstance(mu);
                mu.getMHist().add(mHist);
                drl.fire();
                txtSever.setText(mHist.getStringSever());
            } catch (Exception e) {
                e.printStackTrace();
                ErrorPopup.errorPopup("Fuck my life 🫠");
                return;
            }
            buttonSaveChange.setDisable(false);
            sliderRVmMCR.setValue(0);cbBMI.getSelectionModel().clearSelection();txtRVActv.setText("");txtRVFev.setText("");
            groupEditSeverity.setDisable(true);
            groupEditSeverity.setVisible(false);
        }
        private boolean durationModified=false;
        @FXML
        private void modifyDuration(){
            txtDur.setVisible(false);
            txtDuration.setVisible(true);
            txtDuration.setDisable(false);
            buttonDu.setVisible(false);
            buttonDu.setDisable(true);
            buttonSaveChange.setDisable(false);
            durationModified=true;
        }
        private boolean dateModified=false;
        @FXML
        private void modifySDate(){
            txtSDate.setVisible(false);
            dateSdate.setVisible(true);
            dateSdate.setDisable(false);
            buttonDa.setVisible(false);
            buttonDa.setDisable(true);
            buttonSaveChange.setDisable(false);
            dateModified=true;
        }
        private boolean trtmntModified=false;
        @FXML
        private void txtModified(){if(!trtmntModified){trtmntModified=true;buttonSaveChange.setDisable(false);}}
        @FXML
        private void saveChanges() throws Exception{
            
            if(durationModified&&(!txtDuration.getText().isEmpty())){
                mHist.setDuration(Integer.parseInt(txtDuration.getText()));
            }
            if(dateModified){
                try {
                    if(dateSdate.getValue()==null){throw new Error("Whatever dude");}
                    mHist.setBeginDate(Date.valueOf(dateSdate.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorPopup.errorPopup("Not a valid date");    
                }
            }
            if(trtmntModified){ 
                mHist.setSuggestedTreatment(txtTreatmnt.getText());
            }
            buttonSaveChange.setDisable(true);
            try{
                DbManager.updateSeverity(mHist.getId(),mHist.getSeverityLevel());
                DbManager.updateTreatmentAndDates(mHist.getId(),mHist.getSuggestedTreatment(),mHist.getBeginDate(),mHist.getDuration());
            }catch(Exception e){e.printStackTrace();ErrorPopup.errorPopup(0);return;}
            hideAll();
            resetAll();
            SuccessPopup.successPopup("Report edited successfully!");
        }


    /* Create Report */
    @FXML
    private Pane paneCreateReport;

        @FXML
        private CheckBox checkSeverGrp;
        @FXML
        private CheckBox checkMinor0;
        @FXML
        private CheckBox checkMinor1;
        @FXML
        private CheckBox checkMinor2;
        @FXML
        private CheckBox checkMayor0;
        @FXML
        private CheckBox checkMayor1;
        @FXML
        private CheckBox checkMayor2;
        @FXML
        private Group severityGroup;
        @FXML
        private RadioButton rbCoughY;
        @FXML
        private RadioButton rbCoughN;
        @FXML
        private RadioButton rbChronExY;
        @FXML
        private RadioButton rbChronExN;
        @FXML
        private Slider sliderDyspneaScale;
        @FXML
        private RadioButton rbAATDY;
        @FXML
        private RadioButton rbAATDN;
        @FXML
        private TextField tFieldActivityperDayJEJ;
        @FXML
        private ComboBox<String> cbBmiCreate;
        @FXML
        private TextField tFieldFEV;
        @FXML
        private TextField tFieldExaC;
        @FXML
        private TextField tFieldExaT;
        @FXML
        private Text textLine1;
        @FXML
        private Text textLine2;

        private void setTFieldCreateReport(){
            tFieldActivityperDayJEJ.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tFieldActivityperDayJEJ.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            tFieldFEV.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tFieldFEV.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            tFieldExaC.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tFieldExaC.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            tFieldExaT.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tFieldExaT.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }
        @FXML
        private void toggleCoughY(){if(rbCoughN.isSelected()){rbCoughN.setSelected(false);}}
        @FXML
        private void toggleCoughN(){if(rbCoughY.isSelected()){rbCoughY.setSelected(false);}}
        @FXML
        private void toggleChroExpY(){if(rbChronExN.isSelected()){rbChronExN.setSelected(false);}}
        @FXML
        private void toggleChroExpN(){if(rbChronExY.isSelected()){rbChronExY.setSelected(false);}}
        @FXML
        private void toggleAATDY(){if(rbAATDN.isSelected()){rbAATDN.setSelected(false);}}
        @FXML
        private void toggleAATDN(){if(rbAATDY.isSelected()){rbAATDY.setSelected(false);}}
        @FXML
        private void execCreateReport() throws IOException{

            if(tFieldExaC.getText().isEmpty()||tFieldExaT.getText().isEmpty()){ErrorPopup.errorPopup("Please, fill all the options.");return;}
            if(!(rbCoughY.isSelected()||rbCoughN.isSelected())){ErrorPopup.errorPopup("Please, fill all the options.");return;}
            if(!(rbChronExY.isSelected()||rbChronExN.isSelected())){ErrorPopup.errorPopup("Please, fill all the options.");return;}
            if(!(rbAATDY.isSelected()||rbAATDN.isSelected())){ErrorPopup.errorPopup("Please, fill all the options.");return;}
            if(checkSeverGrp.isSelected()&&(tFieldFEV.getText().isEmpty()||tFieldActivityperDayJEJ.getText().isEmpty()||cbBmiCreate.getSelectionModel().isEmpty())){ErrorPopup.errorPopup("Please, fill all the options.");return;}

            SignsAndSymptoms ss = new SignsAndSymptoms();
            ss.setCough(rbCoughY.isSelected());
            ss.setChronicExpectoration(rbChronExY.isSelected());
            ss.setExacerbationCount(Integer.parseInt(tFieldExaC.getText()));
            ss.setTimeBetweenExacerbations(Integer.parseInt(tFieldExaT.getText()));
            ss.setMixed_asthma(checkMayor2.isSelected(),checkMayor0.isSelected(),checkMayor1.isSelected(),checkMinor0.isSelected(),checkMinor2.isSelected(),checkMinor1.isSelected());
            ss.setAatd(rbAATDY.isSelected());
            if(checkSeverGrp.isSelected()){
                ss.setmMCR((int)sliderDyspneaScale.getValue());
                ss.setBmi(cbBmiCreate.getSelectionModel().isSelected(1));
                ss.setActivityMinutes(Integer.parseInt(tFieldActivityperDayJEJ.getText()));
                ss.setFEV(Integer.parseInt(tFieldFEV.getText()));
                ss.calculateBODEx();
            }
            MedicalHistory m = new MedicalHistory();
            m.setSignsAndSymptoms(ss);
            
            if(!checkSeverGrp.isSelected()){m.setSeverityLevel(-1);}

            try {
                MedicalHistory_Unit mu = new MedicalHistory_Unit();
                RuleUnitInstance<MedicalHistory_Unit> drl = RuleUnitProvider.get().createRuleUnitInstance(mu);
                mu.getMHist().add(m);
                drl.fire();
                DbManager.createMedicalRecord((int)this.ptId,m.getPhenotype(),m.getSeverityLevel(),m.getSuggestedTreatment(),m.getBeginDate(),m.getDuration());
            } catch (Exception e) {
                e.printStackTrace();
                ErrorPopup.errorPopup("Fuck my life 🫠");
                return;
            }
            resetAll();
            hideAll();
            SuccessPopup.successPopup("Report created successfully!");
        }
        @FXML
        private void disableSeverity(){
            if (severityGroup.isDisabled()){
                severityGroup.setDisable(false);
                textLine1.setOpacity(1);textLine2.setOpacity(1);
            }else{
                severityGroup.setDisable(true);
                textLine1.setOpacity(.5);textLine2.setOpacity(.5);
                sliderDyspneaScale.setValue(0);
                tFieldActivityperDayJEJ.clear();
                tFieldFEV.clear();
                cbBmiCreate.getSelectionModel().clearSelection();
            }
        }
}
