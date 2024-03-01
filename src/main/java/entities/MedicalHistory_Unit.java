package entities;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

public class MedicalHistory_Unit implements RuleUnitData {
    
    private final DataStore<MedicalHistory> mHist;

    public MedicalHistory_Unit(){
        this(DataSource.createStore());
    }
        
    public MedicalHistory_Unit(DataStore<MedicalHistory> mHist) {
        this.mHist = mHist;
    }

    public DataStore<MedicalHistory> getMHist() {
        return mHist;
    }
}
