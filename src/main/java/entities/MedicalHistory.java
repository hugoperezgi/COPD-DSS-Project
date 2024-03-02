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
    private short severityLevel;

    private String suggestedTreatment;
    private Date beginDate, endDate;

    private SignsAndSymptoms signsAndSymptoms;

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
    public short getSeverityLevel() {
        return severityLevel;
    }
    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = (short)severityLevel;
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

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public SignsAndSymptoms getSignsAndSymptoms() {
        return signsAndSymptoms;
    }
    public void setSignsAndSymptoms(SignsAndSymptoms signsAndSymptoms) {
        this.signsAndSymptoms = signsAndSymptoms;
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
}
