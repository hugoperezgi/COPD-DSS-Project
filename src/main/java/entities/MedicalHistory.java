package entities;

import java.sql.Date;
import java.time.LocalDate;

public class MedicalHistory {

    private int id;

    private String phenotype=null;
    private int severityLevel=1, duration;

    private String suggestedTreatment;
    private Date beginDate;

    public SignsAndSymptoms signsAndSymptoms;

    public MedicalHistory(int id, String phenotype, int severity, String treatment, Date beginDate, int duration) {
        this.setId(id);
        this.setPhenotype(phenotype);
        this.setSeverityLevel(severity);
        this.setSuggestedTreatment(treatment);
        this.setBeginDate(beginDate);
        this.setDuration(duration);
    }
    public MedicalHistory(int id, char phenotype, int severity, String treatment, Date beginDate, int duration) {
        this.setId(id);
        this.setPhenotype(phenotype);
        this.setSeverityLevel(severity);
        this.setSuggestedTreatment(treatment);
        this.setBeginDate(beginDate);
        this.setDuration(duration);
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
        if(this.severityLevel==-1){return "Pending CAT";}else if(this.severityLevel==0){return "-";}else{return Integer.toString(this.severityLevel);}
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
