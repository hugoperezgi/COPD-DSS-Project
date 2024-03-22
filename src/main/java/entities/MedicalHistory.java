package entities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.LocalDate;

public class MedicalHistory {

    private int id;

    private String phenotype;
    private int severityLevel=1, duration;

    private String suggestedTreatment,stringSever;
    private Date beginDate;

    public SignsAndSymptoms signsAndSymptoms;

    public MedicalHistory(int id, String phenotype, int severity, String treatment, Date beginDate, int duration) {
    this.setId(id);
    this.setPhenotype(phenotype);
    this.setSeverityLevel(severity);
    this.setSuggestedTreatment(treatment);
    this.setBeginDate(beginDate);
    this.setDuration(duration);
    if(severity==-1){this.stringSever="Pending CAT";}else{this.stringSever=Integer.toString(severity);}
    }
    public MedicalHistory(int id, char phenotype, int severity, String treatment, Date beginDate, int duration) {
        this.setId(id);
        this.setPhenotype(phenotype);
        this.setSeverityLevel(severity);
        this.setSuggestedTreatment(treatment);
        this.setBeginDate(beginDate);
        this.setDuration(duration);
        if(severity==-1){this.stringSever="Pending CAT";}else{this.stringSever=Integer.toString(severity);}
        }
    

    public void setDuration(int duration) { this.duration = duration;
    }

    public int getDuration(){ return this.duration; }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPhenotype() {
        if(phenotype==null){return "?";}return phenotype;
    }
    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }
    public void setPhenotype(char phenotype) {
        this.phenotype = "" + phenotype;
    }
    public int getSeverityLevel() {
        return severityLevel;
    }
    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }
    public String getSuggestedTreatment() {
        return suggestedTreatment;
    }
    public void setSuggestedTreatment(String suggestedTreatment) {
        this.suggestedTreatment = suggestedTreatment;
    }
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate() {
        this.beginDate = Date.valueOf(LocalDate.now());
    }
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getStringSever() {
        return stringSever;
    }
    public void setStringSever(String stringSever) {
        this.stringSever = stringSever;
    }
    public SignsAndSymptoms getSignsAndSymptoms() {
        return signsAndSymptoms;
    }
    public void setSignsAndSymptoms(SignsAndSymptoms signsAndSymptoms) {
        this.signsAndSymptoms = new SignsAndSymptoms(signsAndSymptoms);
    }
    public void setSignsAndSymptoms(){
        this.signsAndSymptoms = new SignsAndSymptoms();
    }
    public MedicalHistory(){
        this.setSignsAndSymptoms();
    }
}
