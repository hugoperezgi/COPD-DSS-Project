package testSeverityRules;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.*;

public class testSeverityRules {

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
    public void testSeverityRulesTrue(){
        Assert.assertEquals(true,true);
    }

    @Test
    public void testSeverity1(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setBodex(2);

        mHistUnit.getMHist().add(m);
        
        MedicalHistory m2 = new MedicalHistory();
        m2.getSignsAndSymptoms().setBodex(1);
        
        mHistUnit.getMHist().add(m2);
        
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),1);
        Assert.assertEquals(m2.getSeverityLevel(),1);
    }
    @Test
    public void testSeverity2(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setBodex(3);

        mHistUnit.getMHist().add(m);
        MedicalHistory m2 = new MedicalHistory();
        m2.getSignsAndSymptoms().setBodex(4);
        m2.getSignsAndSymptoms().setmMCR(1);

        mHistUnit.getMHist().add(m2);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),2);
        Assert.assertEquals(m2.getSeverityLevel(),2);
    }
    @Test
    public void testSeverity3(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setBodex(5); 
        mHistUnit.getMHist().add(m);
        
        MedicalHistory m2 = new MedicalHistory();
        m2.getSignsAndSymptoms().setBodex(6);         
        mHistUnit.getMHist().add(m2);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),3);
        Assert.assertEquals(m2.getSeverityLevel(),3);
    }
    @Test

    public void testSeverity4(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setBodex(7);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),4);
    }
    @Test
    public void testCATSeverity1(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setCOPDScore(6);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),1);
    }
    @Test
    public void testCATSeverity2(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setCOPDScore(16);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),2);
    }
    @Test
    public void testCATSeverity3(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setCOPDScore(26);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),3);
    }
    @Test
    public void testCATSeverity4(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setCOPDScore(36);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),4);
    }

}
