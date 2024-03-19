package gui;

import java.io.IOException;
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
import javafx.scene.layout.Pane;
import sql.DbManager;

public class DoctorController implements Initializable{

    private ErrorPopup ErrorPopup = new ErrorPopup();
    private SuccessPopup SuccessPopup = new SuccessPopup();

    private User myself;

    private List<Patient> pList=null;
    private List<MedicalHistory> mhList=null;
    private MedicalHistory mHist=null;
    private String sPatientName=null;
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
        panePatientList.setVisible(false);
        panePatientList.setDisable(true);
        paneShowPatient.setVisible(false);
        paneShowPatient.setDisable(true);
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
        private TableColumn<Patient,Character> columnPatientSurname;
        @FXML
        private TableView<Patient> tableViewPatients;

        @FXML
        private void showPatients(){
            hideAll();
            drkChckBtn();

            panePatientList.setVisible(true);
            panePatientList.setDisable(false);

            

        }
        private void loadPatientTable(){
            tableViewPatients.getItems().clear();
            tableViewPatients.getItems().addAll(pList);
            cbBoxSelectPatient.getSelectionModel().clearSelection();
                String[] sList = new String[pList.size()];
                int i=0;
                for (Patient p : pList) {
                    // sList[i]="Id: "+p.getMedicalCardNumber()+" - ("+p.getName()+" "+p.getSurname()+")";//ffs no tenemos ni apellidos holy shiet
                    i++;
                }
            cbBoxSelectPatient.getItems().setAll(sList);
        }
        @FXML
        private void showPatientInfo() throws IOException{
            if(cbBoxSelectPatient.getSelectionModel().isEmpty()){
                ErrorPopup.errorPopup(2);
                return;
            }

            plId=cbBoxSelectPatient.getSelectionModel().getSelectedIndex();
            ptId=pList.get(plId).getMedicalCardNumber();
            // mhList=idk-idc.getMedicalHist(ptId);//TODO
            if(mhList==null){
                ErrorPopup.errorPopup(12);
                return;
            }
            sPatientName=pList.get(cbBoxSelectPatient.getSelectionModel().getSelectedIndex()).getName();

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
        private TableColumn<MedicalHistory,Character> columnPhenotype;
        @FXML
        private TableColumn<MedicalHistory,String> columnSeverity;
        @FXML
        private TableView<MedicalHistory> tableViewMedTests;


    /* Detailed Report view */

    /* Create Report */

}
