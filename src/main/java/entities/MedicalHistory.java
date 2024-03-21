package entities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;

public class MedicalHistory {

    private int id;

    private char phenotype;
    private int severityLevel, duration;

    private String suggestedTreatment;
    private Date beginDate;

    public SignsAndSymptoms signsAndSymptoms;

    public MedicalHistory(int id, char phenotype, int severity, String treatment, Date beginDate, int duration) {
    this.setId(id);
    this.setPhenotype(phenotype);
    this.setSeverityLevel(severity);
    this.setSuggestedTreatment(treatment);
    this.setBeginDate(beginDate);
    this.setDuration(duration);
    }

    private void setDuration(int duration) { this.duration = duration;
    }

    public int getDuration(){ return this.duration; }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public char getPhenotype() {
        return phenotype;
    }
    public void setPhenotype(char phenotype) {
        this.phenotype = phenotype;
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
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
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
    public byte[] getSignsAndSymptomsBLOB() throws IOException{
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(b);
        oos.writeObject(signsAndSymptoms);
        byte[] rtn = b.toByteArray();
        oos.close();
        b.close();
        return rtn;
    }
    public void loadSignsAndSymptomsBLOB(byte[] b) throws ClassNotFoundException, IOException{
        ByteArrayInputStream bi = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bi);
        this.signsAndSymptoms = (SignsAndSymptoms) ois.readObject();
        ois.close();
        bi.close();
    }

    public MedicalHistory(){
        this.setSignsAndSymptoms();
    }
}
