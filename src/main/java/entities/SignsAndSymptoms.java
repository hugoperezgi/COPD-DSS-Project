package entities;

import java.io.Serializable;

public class SignsAndSymptoms implements Serializable{
    static final long serialVersionUID = 42L;

    private int FEV, activityMinutes, hospitalizationCount;
    private int exacerbationFrequency, exacerbationCount;
    private boolean cough, chronicExpectoration;

    public int getActivityMinutes() {
        return activityMinutes;
    }
    public void setActivityMinutes(int activityMinutes) {
        this.activityMinutes = activityMinutes;
    }
    public int getFEV() {
        return FEV;
    }
    public void setFEV(int fEV) {
        FEV = fEV;
    }
    public int getHospitalizationCount() {
        return hospitalizationCount;
    }
    public void setHospitalizationCount(int hospitalizationCount) {
        this.hospitalizationCount = hospitalizationCount;
    }

    public int getExacerbationCount() {
        return exacerbationCount;
    }
    public void setExacerbationCount(int exacerbationCount) {
        this.exacerbationCount = exacerbationCount;
    }
    public int getExacerbationFrequency() {
        return exacerbationFrequency;
    }
    public void setExacerbationFrequency(int exacerbationFrequency) {
        this.exacerbationFrequency = exacerbationFrequency;
    }
    public boolean getChronicExpectoration() {
        return chronicExpectoration;
    }
    public void setChronicExpectoration(boolean chronicExpectoration) {
        this.chronicExpectoration = chronicExpectoration;
    }
    public boolean getCough() {
        return cough;
    }
    public void setCough(boolean cough) {
        this.cough = cough;
    }

}
