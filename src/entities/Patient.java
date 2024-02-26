package entities;

public class Patient {
    
    private char phenotype;
    private short severityLevel;

    private String suggestedTreatment;

    public char getPhenotype() {
        return phenotype;
    }
    public short getSeverityLevel() {
        return severityLevel;
    }
    public void setPhenotype(char phenotype) {
        this.phenotype = phenotype;
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

}
