package entities;
import java.util.*;

public class SignsAndSymptoms{

    private int FEV, activityMinutes, mMCR, bodex=-1;
    private int timeBetweenExacerbations;
    private int exacerbationCount;

    private int COPDScore;
    private boolean cough, chronicExpectoration, mixed_asthma,aatd,bmi;

    public SignsAndSymptoms(SignsAndSymptoms ss) {
        this.FEV = ss.getFEV();
        this.activityMinutes = ss.getActivityMinutes();
        this.exacerbationCount = ss.getExacerbationCount();
        this.timeBetweenExacerbations = ss.getTimeBetweenExacerbations();
        this.mixed_asthma = ss.isMixed_asthma();
        this.bmi = ss.isBMI();
        this.cough = ss.getCough();
        this.chronicExpectoration = ss.getChronicExpectoration();
        this.mMCR=ss.mMCR;
        this.COPDScore=ss.COPDScore;
        this.aatd=ss.aatd;
        this.bodex = ss.getBodex();
    }

    public SignsAndSymptoms() {
        this.COPDScore=-1;
    }

    public int getmMCR() {
        return mMCR;
    }
    public void setmMCR(int mMCR) {
        this.mMCR = mMCR;
    }

    public boolean isMixed_asthma() {
        return mixed_asthma;
    }
    public boolean isAatd() {
        return aatd;
    }
    public void setAatd(boolean aatd) {
        this.aatd = aatd;
    }
    public void setMixed_asthma(boolean critMajor1,boolean critMajor2,boolean critMajor3, boolean critMinor1, boolean critMinor2, boolean critMinor3) {
        List<Boolean> critMajor = new ArrayList<Boolean>();
        critMajor.add(critMajor1);
        critMajor.add(critMajor2);
        critMajor.add(critMajor3);
        List<Boolean> critMinor = new ArrayList<Boolean>();
        critMinor.add(critMinor1);
        critMinor.add(critMinor2);
        critMinor.add(critMinor3);
        int i = 0;
        int j = 0;
        for (Boolean c : critMajor) {
            if (c) {
                i++;
            }
        }
        for (Boolean c : critMinor) {
            if (c) {
                j++;
            }
        }

        if (i >= 2 || (i == 1 && j >= 2)) {
            this.mixed_asthma = true;
        } else {
            this.mixed_asthma = false;
        }

    }

    public int getCOPDScore() {
        return COPDScore;
    }

    public void setCOPDScore(int COPDScore) {
        this.COPDScore = COPDScore;
    }

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
    public int getBodex() {
        return bodex;
    }
    public void setBodex(int bodex) {
        this.bodex = bodex;
    }
    public boolean isBMI() {
        return bmi;
    }
    public void setBmi(boolean bmi) {
        this.bmi = bmi;
    }
    public int getExacerbationCount() {
        return exacerbationCount;
    }
    public void setExacerbationCount(int exacerbationCount) {
        this.exacerbationCount = exacerbationCount;
    }
    public int getTimeBetweenExacerbations() {
        return timeBetweenExacerbations;
    }
    public void setTimeBetweenExacerbations(int timeBetweenExacerbations) {
        this.timeBetweenExacerbations = timeBetweenExacerbations;
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
    public void calculateBODEx(){
        int tmp=0;
        if(bmi){tmp++;}
        if(activityMinutes<350 && activityMinutes>249){tmp++;}else if(activityMinutes<250 && activityMinutes>149){tmp+=2;}else if(activityMinutes<150){tmp+=3;}
        if(FEV<65 && FEV>49){tmp++;}else if(FEV<50 && FEV>35){tmp+=2;}else if(FEV<35){tmp+=3;}
        switch (mMCR) {
            case 2:tmp++;break;
            case 3:tmp+=2;break;
            case 4:tmp+=3;break;
            default:break;
        }
        bodex=tmp;
    }


}
