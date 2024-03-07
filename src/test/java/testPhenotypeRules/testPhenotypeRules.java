package testPhenotypeRules;

import entities.MedicalHistory;
import entities.SignsAndSymptoms;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.MedicalHistory_Unit;

public class testPhenotypeRules {
    
    private static MedicalHistory_Unit mHistUnit;
    private static RuleUnitInstance<MedicalHistory_Unit> droolsInstance;

    @Before
    public void setUpDrools(){
        mHistUnit = new MedicalHistory_Unit();
        droolsInstance = RuleUnitProvider.get().createRuleUnitInstance(mHistUnit);
    }
    @After
    public void tearDownDrools(){
        droolsInstance.close();
    }

    @Test
    public void testPhenotypeRulesTrue(){
        Assert.assertEquals(true,true);
    }

    @Test
    public void testPhenotypeA(){

        MedicalHistory m = new MedicalHistory();
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setExacerbationCount(1);
        ss.setMixed_asthma(true,false,false,false,false,false);
        System.out.println(ss.isMixed_asthma());
        m.setSignsAndSymptoms(ss);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals('A',m.getPhenotype());
    }
}
