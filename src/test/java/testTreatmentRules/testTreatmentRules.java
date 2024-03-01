package testTreatmentRules;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import entities.*;

public class testTreatmentRules {
    
    
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
    public void testTreatmentRulesTrue(){
        Assert.assertEquals(true,true);
    }


    @Test
    public void testPhenotypeASeverity1(){
        
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('A');
        m.setSeverityLevel(1);
        
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"SABA / SAMA or LAMA / LABA");
    }
    @Test
    public void testPhenotypeASeverity2(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('A');
        m.setSeverityLevel(2);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA / LABA or LAMA + LABA");
    }
    @Test
    public void testPhenotypeASeverity3(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('A');
        m.setSeverityLevel(3);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA");
    }
    @Test
    public void testPhenotypeASeverity4(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('A');
        m.setSeverityLevel(4);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA or LAMA + LABA + Teofiline");
    }
    @Test
    public void testPhenotypeBSeverity1(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('B');
        m.setSeverityLevel(1);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LABA + CI");
    }
    @Test
    public void testPhenotypeBSeverity2(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('B');
        m.setSeverityLevel(2);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LABA + CI");
    }
    @Test
    public void testPhenotypeBSeverity3(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('B');
        m.setSeverityLevel(3);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA + CI");
    }
    @Test
    public void testPhenotypeBSeverity4(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('B');
        m.setSeverityLevel(4);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA + CI, Consider adding Teofiline/IPE4 with expectoration");
    }
    @Test
    public void testPhenotypeCSeverity1(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('C');
        m.setSeverityLevel(1);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA / LABA");
    }
    @Test
    public void testPhenotypeCSeverity2(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('C');
        m.setSeverityLevel(2);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"(LAMA / LABA) + CI or LAMA + LABA or LAMA/LABA");
    }
    @Test
    public void testPhenotypeCSeverity3(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('C');
        m.setSeverityLevel(3);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA + CI");
    }
    @Test
    public void testPhenotypeCSeverity4(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('C');
        m.setSeverityLevel(4);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA + CI, Consider adding Teofiline");
    }
    @Test
    public void testPhenotypeDSeverity1(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('D');
        m.setSeverityLevel(1);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA / LABA");
    }
    @Test
    public void testPhenotypeDSeverity2(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('D');
        m.setSeverityLevel(2);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"(LAMA / LABA) + (CI / IPE4) or LAMA + LABA or  LAMA / LABA");
    }
    @Test
    public void testPhenotypeDSeverity3(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('D');
        m.setSeverityLevel(3);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA + (CI / IPE4) or (LAMA / LABA) + CI + IPE4, and consider adding Carbocisteine");
    }
    @Test
    public void testPhenotypeDSeverity4(){
        MedicalHistory m = new MedicalHistory();
        m.setPhenotype('D');
        m.setSeverityLevel(4);
        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(m.getSuggestedTreatment(),"LAMA + LABA + (CI / IPE4) or LAMA + LABA + CI + IPE4, and consider adding Carbocisteine/Antibiotics");
    }
    
}
