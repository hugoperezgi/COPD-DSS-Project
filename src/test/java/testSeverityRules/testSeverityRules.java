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
        m.getSignsAndSymptoms().setFEV(51);
        m.getSignsAndSymptoms().setHospitalizationCount(0);
        m.getSignsAndSymptoms().setmMCR(0);
        m.getSignsAndSymptoms().setActivityMinutes(121);

        mHistUnit.getMHist().add(m);
        
        MedicalHistory m2 = new MedicalHistory();
        m2.getSignsAndSymptoms().setFEV(51);
        m2.getSignsAndSymptoms().setHospitalizationCount(0);
        m2.getSignsAndSymptoms().setmMCR(1);
        m2.getSignsAndSymptoms().setActivityMinutes(121);
        
        mHistUnit.getMHist().add(m2);
        
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),1);
        Assert.assertEquals(m2.getSeverityLevel(),1);
    }
    @Test
    public void testSeverity2_0(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(51);
        m.getSignsAndSymptoms().setHospitalizationCount(0);
        m.getSignsAndSymptoms().setmMCR(1);
        m.getSignsAndSymptoms().setActivityMinutes(60);

        mHistUnit.getMHist().add(m);
        MedicalHistory m2 = new MedicalHistory();
        m2.getSignsAndSymptoms().setFEV(51);
        m2.getSignsAndSymptoms().setHospitalizationCount(1);
        m2.getSignsAndSymptoms().setmMCR(1);
        m2.getSignsAndSymptoms().setActivityMinutes(60);

        mHistUnit.getMHist().add(m2);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),2);
        Assert.assertEquals(m2.getSeverityLevel(),2);
    }
    @Test
    public void testSeverity2_1(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(51);
        m.getSignsAndSymptoms().setHospitalizationCount(0); //0,1
        m.getSignsAndSymptoms().setmMCR(2);
        m.getSignsAndSymptoms().setActivityMinutes(60);

        mHistUnit.getMHist().add(m);
        
        MedicalHistory m2 = new MedicalHistory();
        m2.getSignsAndSymptoms().setFEV(51);
        m2.getSignsAndSymptoms().setHospitalizationCount(1); //0,1
        m2.getSignsAndSymptoms().setmMCR(2);
        m2.getSignsAndSymptoms().setActivityMinutes(60);
        
        mHistUnit.getMHist().add(m2);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),2);
        Assert.assertEquals(m2.getSeverityLevel(),2);
    }
    @Test
    public void testSeverity3_0(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(49);
        m.getSignsAndSymptoms().setHospitalizationCount(1);
        m.getSignsAndSymptoms().setmMCR(2);
        m.getSignsAndSymptoms().setActivityMinutes(21);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),3);
    }
    @Test
    public void testSeverity3_1(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(49);
        m.getSignsAndSymptoms().setHospitalizationCount(2);
        m.getSignsAndSymptoms().setmMCR(2);
        m.getSignsAndSymptoms().setActivityMinutes(21);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),3);
    }
    @Test
    public void testSeverity3_2(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(49);
        m.getSignsAndSymptoms().setHospitalizationCount(1);
        m.getSignsAndSymptoms().setmMCR(3);
        m.getSignsAndSymptoms().setActivityMinutes(21);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),3);
    }
    @Test
    public void testSeverity3_3(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(49);
        m.getSignsAndSymptoms().setHospitalizationCount(2);
        m.getSignsAndSymptoms().setmMCR(3);
        m.getSignsAndSymptoms().setActivityMinutes(21);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),3);
    }
    @Test
    public void testSeverity4_0(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(29);
        m.getSignsAndSymptoms().setHospitalizationCount(3);
        m.getSignsAndSymptoms().setmMCR(3);
        m.getSignsAndSymptoms().setActivityMinutes(29);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();
        Assert.assertEquals(m.getSeverityLevel(),4);
    }
    @Test
    public void testSeverity4_1(){
        MedicalHistory m = new MedicalHistory();
        m.getSignsAndSymptoms().setFEV(29);
        m.getSignsAndSymptoms().setHospitalizationCount(3);
        m.getSignsAndSymptoms().setmMCR(4);
        m.getSignsAndSymptoms().setActivityMinutes(29);

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
