package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        columnMedTestSever.setCellValueFactory(new PropertyValueFactory<>("severityLevel"));

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

        panePatientList.setVisible(false);
        panePatientList.setDisable(true);
        paneShowPatient.setVisible(false);
        paneShowPatient.setDisable(true);
    }
    private void resetAll(){

        pList=null;mhList=null;mHist=null;p=null;plId=null;ptId=null;
        textPatientName.setText(null);;textBDate.setText(null);;;
        cbBoxSelectReport.getSelectionModel().clearSelection();
        cbBoxSelectPatient.getSelectionModel().clearSelection();
        //TODO duh
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
                ErrorPopup.errorPopup(2);
                return;
            }

            plId=cbBoxSelectPatient.getSelectionModel().getSelectedIndex();
            ptId=pList.get(plId).getMedicalCardNumber();
            mhList=DbManager.getMedicalHistory(ptId);//TODO finish u fucker
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
            //TODO fuck my life :)
        }
        @FXML
        private void checkReport() throws Exception{
            if(cbBoxSelectReport.getSelectionModel().isEmpty()){
                ErrorPopup.errorPopup(2);
                return;
            }

            mHist=mhList.get(cbBoxSelectReport.getSelectionModel().getSelectedIndex());

            //Somehow check if data needs to be added -> Display the different possible views
            
            mHist.getSeverityLevel(); //From CAT/Medical thingys
            mHist.getPhenotype(); //From ?
            mHist.getSuggestedTreatment(); //Should get filled by DSS
            mHist.getBeginDate();//Set when the CAT is made, 
            mHist.getDuration();//Set by DSS ?
            
            
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

        //Any HUGE TODO ?

    /* Create Report */

    @FXML
    private Pane paneCreateReport;

        //Yeah TODO slaker...
}
